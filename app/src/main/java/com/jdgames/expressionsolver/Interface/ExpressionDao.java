package com.jdgames.expressionsolver.Interface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.jdgames.expressionsolver.Pojo.Expression;

import java.util.List;

@Dao
public interface ExpressionDao {
    @Insert
    void insert(Expression expression);

    @Query("SELECT * FROM tb_expressions ORDER BY date DESC")
    List<Expression> getAllExpressions();
}
