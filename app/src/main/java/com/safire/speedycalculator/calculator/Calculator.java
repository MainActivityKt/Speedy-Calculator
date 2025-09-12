package com.safire.speedycalculator.calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    static final Pattern LEFT_PARENTHESIS = Pattern.compile("\\(");
    static final Pattern RIGHT_PARENTHESIS = Pattern.compile("\\)");

    static Pattern SINGLE_NUMBER = Pattern.compile("-*\\d+(.\\d+)?");
    static final Pattern OPERATOR = Pattern.compile("[+\\-x÷%]|-+");

    Map<String, Operator> operatorsPrecedence = new HashMap<>();

    public Calculator() {
        operatorsPrecedence.put("+", new Operator(1, OperatorName.ADD));
        operatorsPrecedence.put("-", new Operator(1, OperatorName.SUBTRACT));
        operatorsPrecedence.put("x", new Operator(2, OperatorName.Multiply));
        operatorsPrecedence.put("÷", new Operator(2, OperatorName.Divide));
        operatorsPrecedence.put("%", new Operator(3, OperatorName.PERCENTAGE));
    }

    public String evaluateExpression(String line) {
        Deque<String> postfix = getPostfix(line);
        return calculatePostfix(postfix);
    }

    private String calculatePostfix(Deque<String> postfix) {
        if (postfix.isEmpty()) {
            return "Invalid expression";
        } else {
            Deque<String> result = new ArrayDeque<>();

            while (!postfix.isEmpty()) {
                String value = postfix.pop();

                if (SINGLE_NUMBER.matcher(value).matches()) {
                    int negativeSignsCount = value.length() - value.replaceAll("-", "").length();
                    result.push(negativeSignsCount % 2 == 0 ? value : "-".concat(value.replaceAll("[+-]+", "")));
                } else if (OPERATOR.matcher(value).matches()) {
                    Double b = Double.valueOf(result.pop());
                    Double a = Double.valueOf(result.pop());

                    OperatorName operator = operatorsPrecedence.get(value).operatorName;

                    switch (operator) {
                        case ADD:
                            result.push(String.valueOf(a + b));
                            break;

                        case SUBTRACT:
                            result.push(String.valueOf(a - b));
                            break;

                        case Multiply:
                            result.push(String.valueOf(a * b));
                            break;

                        case Divide:
                            if (b.equals(0.0)) {
                                return "Infinity";
                            }
                            result.push(String.valueOf(a / b));
                            break;
                        case PERCENTAGE:
                            result.push(String.valueOf((a * b) / 100));

                    }
                }
            }
            return result.pop();
        }
    }

    private Deque<String> getPostfix(String infix) {

        Deque<String> result = new ArrayDeque<>();
        Deque<String> stack = new ArrayDeque<>(); //rename to operatorsStack

        Pattern pattern = Pattern.compile("(?<![\\d)])-?\\d+(?:\\.\\d+)?|\\d+(?:\\.\\d+)?|[-+x÷%()]");
        Matcher matcher = pattern.matcher(infix);

        while (matcher.find()) {
            String value = matcher.group();

            if (SINGLE_NUMBER.matcher(value).matches()) {
                result.addLast(value);
            } else if (LEFT_PARENTHESIS.matcher(value).matches()) {
                stack.push(value);
            } else if (OPERATOR.matcher(value).matches()) {

                if (stack.isEmpty() || stack.peek().equals("(")) {
                    stack.push(value);
                } else {
                    String stackTop = stack.peek();

                    int topItemPrecedence = operatorsPrecedence.get(stackTop).precedence;
                    int incomingItemPrecedence = operatorsPrecedence.get(value).precedence;

                    if (incomingItemPrecedence > topItemPrecedence) {
                        stack.push(value);
                    } else {
                        while (stackTop != null && (!stackTop.equals("(") && incomingItemPrecedence <= topItemPrecedence) ) {
                            String item = stack.pop();
                            result.offer(item);

                            stackTop = stack.peek();
                            System.out.println("Stack top:" + stackTop);
                            topItemPrecedence = stackTop != null ?operatorsPrecedence.get(stackTop).precedence : -1;
                        }
                        stack.push(value);
                    }
                }
            }  else if(RIGHT_PARENTHESIS.matcher(value).matches()) {

                String stackTop = "";

                do {
                    stackTop = stack.pop();
                    if (!LEFT_PARENTHESIS.matcher(stackTop).matches() && !RIGHT_PARENTHESIS.matcher(stackTop).matches()) {
                        result.offer(stackTop);

                    }
                } while (!stackTop.equals("("));
            }
        }

        while (!stack.isEmpty()) {
            result.offer(stack.pop());
        }
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
    ADD, SUBTRACT, Multiply, Divide, PERCENTAGE
}