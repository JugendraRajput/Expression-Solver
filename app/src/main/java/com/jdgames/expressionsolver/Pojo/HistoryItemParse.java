package com.jdgames.expressionsolver.Pojo;

import java.util.List;

public class HistoryItemParse {

    String date;
    List<Expression> items;

    public HistoryItemParse(String date, List<Expression> items) {
        this.date = date;
        this.items = items;
    }

    public String getDate() {
        return date;
    }

    public List<Expression> getItems() {
        return items;
    }
}
