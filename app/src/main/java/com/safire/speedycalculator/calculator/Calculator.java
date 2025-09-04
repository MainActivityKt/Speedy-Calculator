package com.safire.speedycalculator.calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class Calculator {


    static final String EXIT = "/exit";
    static final String HELP = "/help";
    static final Pattern LEFT_PARENTHESIS = Pattern.compile("\\(");
    static final Pattern RIGHT_PARENTHESIS = Pattern.compile("\\)");

    static Pattern SINGLE_NUMBER = Pattern.compile("[+-]*\\d+(.\\d+)?");
    static final Pattern OPERATOR = Pattern.compile("[+\\-*÷%]|-+");

    static Map<String, Operator> operatorsPrecedence = new HashMap<>();
    static Map<String, BigInteger> variables = new HashMap<>();

    static boolean isRunning = true;

    public static void main(String[] args) {

        operatorsPrecedence.put("+", new Operator(1, OperatorName.ADD));
        operatorsPrecedence.put("-", new Operator(1, OperatorName.SUBTRACT));
        operatorsPrecedence.put("*", new Operator(2, OperatorName.Multiply));
        operatorsPrecedence.put("/", new Operator(2, OperatorName.Divide));
        operatorsPrecedence.put("^", new Operator(3, OperatorName.Power));



        Scanner scanner = new Scanner(System.in);
        String line;

        do {
            line = scanner.nextLine();

            if (line.startsWith("/")) {
                parseCommand(line);
            } else if (SINGLE_NUMBER.matcher(line).matches()) {
                System.out.println(line);
            } else if(!line.isEmpty()) { //TODO
                parseExpression(line);
            }
        } while (isRunning);
        System.out.println("Bye!");
        scanner.close();
    }

    static void parseCommand(String line) {

        if (line.equals(HELP)) {
            System.out.println("The program performs calculation operations.");
        } else if (line.equals(EXIT)) {
            isRunning = false;
        }

    }


    static void parseExpression(String line) {
        Deque<String> postfix = getPostfix(line);


        if (postfix.isEmpty()) {
            System.out.println("Invalid expression");
        } else {
            Deque<String> result = new ArrayDeque<>();

            while (!postfix.isEmpty()) {
                String value = postfix.pop();

                if (SINGLE_NUMBER.matcher(value).matches()) {
                    int negativeSignsCount = value.length() - value.replaceAll("-", "").length();
                    result.push(negativeSignsCount %2 == 0 ? value : "-".concat(value.replaceAll("[+-]+", "")));
                } else if (OPERATOR.matcher(value).matches()) {
                    BigInteger b = new Scanner(result.pop()).nextBigInteger();
                    BigInteger a = new Scanner(result.pop()).nextBigInteger();

                    OperatorName operator = operatorsPrecedence.get(value).operatorName;

                    switch (operator) {
                        case ADD:
                            result.push(String.valueOf(a.add(b)));
                            break;

                        case SUBTRACT:
                            result.push(String.valueOf(a.subtract(b)));
                            break;

                        case Multiply:
                            result.push(String.valueOf(a.multiply(b)));
                            break;

                        case Divide:
                            result.push(String.valueOf(a.divide(b)));
                            break;

                    }
                }
            }
            System.out.println(result.pop());
        }

    }

    static Deque<String> getPostfix(String infix) {

        Deque<String> result = new ArrayDeque<>();
        Deque<String> stack = new ArrayDeque<>(); //rename to operatorsStack

        int leftParenthesisCount = infix.length() - infix.replaceAll(LEFT_PARENTHESIS.toString(), "").length();
        int rightParenthesisCount = infix.length() - infix.replaceAll(RIGHT_PARENTHESIS.toString(), "").length();

        if (leftParenthesisCount != rightParenthesisCount || infix.matches(".*\\*{2,}.*|.*/{2,}.*")) {
            return new ArrayDeque<>();
        }

        infix = infix.replaceAll(String.valueOf(LEFT_PARENTHESIS), " ( ");
        infix = infix.replaceAll(String.valueOf(RIGHT_PARENTHESIS), " ) ");
        infix = infix.replaceAll("\\++", " + ");
        infix = infix.replaceAll("\\*+", " * ");
        infix = infix.replaceAll("\\^", " ^ ");

        infix = infix.replaceAll(String.valueOf(RIGHT_PARENTHESIS), " ) ");
        infix = infix.replaceAll(String.valueOf(RIGHT_PARENTHESIS), " ) ");
        Scanner sc = new Scanner(infix);

        while (sc.hasNext()) {
            String value = sc.next();

            if (SINGLE_NUMBER.matcher(value).matches()) {
                result.offer(value);
            } else if (LEFT_PARENTHESIS.matcher(value).matches()) {
                stack.push(value);
            } else if (OPERATOR.matcher(value).matches()) {
                if (value.matches("[+-]{2,}")) {
                    int negativesCount = value.length() - value.replaceAll("-", "").length();
                    value = negativesCount % 2 == 0 ? "+" : "-";
                }

                if (stack.isEmpty() || stack.peek().equals("(")) {
                    stack.push(value);
                } else {
                    String stackTop = stack.peek();
                    int topItemPrecedence = operatorsPrecedence.get(stackTop).precedence;
                    int incomingItemPrecedence = operatorsPrecedence.get(value).precedence;

                    if (incomingItemPrecedence > topItemPrecedence) {
                        stack.push(value);
                    } else {
                        while (stackTop != null && !stackTop.equals("(")) {
                            String item = stack.pop();
                            result.offer(item);
                            stackTop = stack.peek();
                        }
                        stack.push(value);
                    }
                }
            } else if (RIGHT_PARENTHESIS.matcher(value).matches()) {

                String stackTop = stack.pop();

                do {
                    result.offer(stackTop);
                    stackTop = stack.pop();
                } while (!stackTop.equals("("));
            }
        }

        while (!stack.isEmpty()) {
            result.offer(stack.pop());
        }
        sc.close();
        return result;
    }
}

class Operator {
    int precedence;
    OperatorName operatorName;

    public Operator(int precedence, OperatorName operatorName) {
        this.precedence = precedence;
        this.operatorName = operatorName;

    }
}

enum OperatorName {
    ADD, SUBTRACT, Multiply, Divide, Power
}
