package com.jdgames.expressionsolver.Pojo;

public class ResultParse {
    String expression;
    String result;

    public ResultParse(String expression, String result) {
        this.expression = expression;
        this.result = result;
    }

    public String getExpression() {
        return expression;
    }

    public String getResult() {
        return result;
    }
}
