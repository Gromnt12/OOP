package ru.nsu.bukhanov;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Expression current = null;

        System.out.println("Консоль математических выражений");
        System.out.println("Команды: expr, print, deriv, simplify, eval, help, end/exit");

        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            if (equalsCmd(line, "end") || equalsCmd(line, "exit") || equalsCmd(line, "quit")) {
                System.out.println("Выход.");
                break;
            }

            if (equalsCmd(line, "help")) {
                printHelp();
                continue;
            }

            // expr ...
            if (startsWithCommand(line, "expr")) {
                String exprStr = argsAfter(line, "expr", /*stripParensOnlyIfNoSpace=*/true);
                if (exprStr.isEmpty()) {
                    System.out.println("Нужно передать выражение: expr (3+(2*x))");
                    continue;
                }
                try {
                    ExpressionParser parser = new ExpressionParser(exprStr);
                    current = parser.parse();
                    System.out.println("Выражение установлено: " + current.print());
                } catch (Exception e) {
                    System.out.println("Ошибка парсинга: " + e.getMessage());
                }
                continue;
            }

            // print
            if (line.equalsIgnoreCase("print") ||
                    (startsWithCommand(line, "print") && argsAfter(line, "print", false).isEmpty())) {
                if (current == null) {
                    System.out.println("Выражение не задано.");
                } else {
                    System.out.println(current.print());
                }
                continue;
            }

            // deriv
            if (startsWithCommand(line, "deriv")) {
                if (current == null) {
                    System.out.println("Сначала задайте выражение.");
                    continue;
                }
                String varName = argsAfter(line, "deriv", true);
                if (varName.isEmpty()) {
                    System.out.println("Укажите переменную: deriv x");
                    continue;
                }
                current = current.derivative(varName);
                System.out.println("Производная: " + current.print());
                continue;
            }

            // simplify
            if (line.equalsIgnoreCase("simplify") ||
                    (startsWithCommand(line, "simplify") && argsAfter(line, "simplify", false).isEmpty())) {
                if (current == null) {
                    System.out.println("Сначала задайте выражение.");
                    continue;
                }
                current = current.simplify();
                System.out.println("Упрощённое выражение: " + current.print());
                continue;
            }

            // eval
            if (startsWithCommand(line, "eval")) {
                if (current == null) {
                    System.out.println("Сначала задайте выражение.");
                    continue;
                }
                String assigns = argsAfter(line, "eval", true);
                try {
                    double result = current.eval(assigns);
                    if (result == Math.rint(result)) {
                        System.out.println("Результат: " + (int) result);
                    } else {
                        System.out.println("Результат: " + result);
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка вычисления: " + e.getMessage());
                }
                continue;
            }

            System.out.println("Неизвестная команда. Напишите 'help'.");
        }
    }

    private static void printHelp() {
        System.out.println("Доступные команды:");
        System.out.println("  expr <выражение>   или  expr(<выражение>)");
        System.out.println("  print              или  print()");
        System.out.println("  deriv <var>        или  deriv(<var>)");
        System.out.println("  simplify           или  simplify()");
        System.out.println("  eval <assignments> или  eval(<assignments>)");
        System.out.println("  end / exit / quit  — выход");
    }

    private static boolean equalsCmd(String line, String cmd) {
        return line.equalsIgnoreCase(cmd) || line.equalsIgnoreCase(cmd + "()");
    }

    private static boolean startsWithCommand(String line, String cmd) {
        if (line.length() < cmd.length()) return false;
        if (!line.regionMatches(true, 0, cmd, 0, cmd.length())) return false;
        if (line.length() == cmd.length()) return true;
        char ch = line.charAt(cmd.length());
        return Character.isWhitespace(ch) || ch == '(';
    }

    private static String argsAfter(String line, String cmd, boolean stripParensOnlyIfNoSpace) {
        int idx = line.indexOf(cmd);
        String rest = line.substring(idx + cmd.length()).trim();

        if (stripParensOnlyIfNoSpace) {
            // смотрим, сразу ли после имени команды шла скобка
            int afterCmd = idx + cmd.length();
            boolean noSpace = afterCmd < line.length() && line.charAt(afterCmd) == '(';
            if (noSpace && rest.startsWith("(") && rest.endsWith(")") && rest.length() >= 2) {
                return rest.substring(1, rest.length() - 1).trim();
            } else {
                return rest;
            }
        } else {
            return rest;
        }
    }
}
