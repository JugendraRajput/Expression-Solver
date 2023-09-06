package com.jdgames.expressionsolver.Pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_expressions")
public class Expression {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "expression")
    public String expression;

    @ColumnInfo(name = "result")
    public String result;

    @ColumnInfo(name = "date")
    public String date;

    public int getId() {
        return id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
