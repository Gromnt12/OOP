#!/usr/bin/env bash
set -Eeuo pipefail

# --- config ---
MAIN_CLASS="ru.nsu.bukhanov.Main"

# --- helpers ---
usage() {
  cat <<'EOF'
Usage: ./build.sh [command]

Commands:
  build        Clean + build (with tests & JaCoCo report)
  test         Run tests
  run          Run the console app
  jar          Build jar (no run)
  run-jar      Build jar and run the latest one from build/libs
  clean        gradle clean
  coverage     Open path to JaCoCo report (prints location)
  help         Show this message

Tips:
  - First run: ./build.sh build
  - Then run game: ./build.sh run
EOF
}

need_cmd() {
  command -v "$1" >/dev/null 2>&1 || return 1
}

ensure_java() {
  if ! need_cmd java; then
    echo "ERROR: 'java' not found. Install JDK 11+ and ensure it is on PATH (or set JAVA_HOME)." >&2
    exit 1
  fi
  echo "Java found: $(java -version 2>&1 | head -n1)"
}

ensure_wrapper() {
  if [[ -x "./gradlew" ]]; then
    return 0
  fi
  echo "Gradle wrapper not found. Trying to generate with system Gradle..."
  if ! need_cmd gradle; then
    echo "ERROR: No './gradlew' and system 'gradle' is not installed." >&2
    echo "Install Gradle once to run:  gradle wrapper" >&2
    exit 1
  fi
  gradle wrapper
  chmod +x ./gradlew
}

gradlew() {
  ./gradlew --no-daemon "$@"
}

latest_jar() {
  ls -t build/libs/*.jar 2>/dev/null | head -n1 || true
}

# --- commands ---
cmd_build()   { ensure_java; ensure_wrapper; gradlew clean build; }
cmd_test()    { ensure_java; ensure_wrapper; gradlew test; }
cmd_clean()   { ensure_wrapper; gradlew clean; }
cmd_jar()     { ensure_java; ensure_wrapper; gradlew jar; }
cmd_run()     { ensure_java; ensure_wrapper; gradlew run -PmainClass="$MAIN_CLASS"; }
cmd_run_jar() {
  ensure_java; ensure_wrapper; gradlew jar;
  JAR="$(latest_jar)"
  if [[ -z "${JAR}" ]]; then
    echo "ERROR: jar not found in build/libs" >&2
    exit 1
  fi
  echo "Running jar: ${JAR}"
  exec java -jar "${JAR}"
}
cmd_coverage() {
  echo "JaCoCo HTML report (after tests): build/reports/jacoco/test/html/index.html"
}

# --- main ---
CMD="${1:-build}"
case "$CMD" in
  build)     cmd_build ;;
  test)      cmd_test ;;
  run)       cmd_run ;;
  jar)       cmd_jar ;;
  run-jar)   cmd_run_jar ;;
  clean)     cmd_clean ;;
  coverage)  cmd_coverage ;;
  help|-h|--help) usage ;;
  *)
    echo "Unknown command: $CMD" >&2
    usage
    exit 2
    ;;
esac
