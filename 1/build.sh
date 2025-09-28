#!/usr/bin/env bash

rm -rf build

mkdir -p build/classes
mkdir -p build/docs

# Компиляция исходного кода
javac -d build/classes src/main/java/ru/nsu/bukhanov/*.java

# Создание документации
javadoc -d build/docs -sourcepath src/main/java -subpackages ru.nsu.bukhanov

# Создание JAR-файла
jar --create --file=build/heapsort.jar \
  --main-class=ru.nsu.bukhanov.MAIN \
  -C build/classes .

# Запуск приложения
java -jar build/heapsort.jar