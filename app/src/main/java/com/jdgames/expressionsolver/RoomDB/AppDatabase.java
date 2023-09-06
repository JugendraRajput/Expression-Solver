package com.jdgames.expressionsolver.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jdgames.expressionsolver.Interface.ExpressionDao;
import com.jdgames.expressionsolver.Pojo.Expression;

@Database(entities = {Expression.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExpressionDao expressionDao();
}

