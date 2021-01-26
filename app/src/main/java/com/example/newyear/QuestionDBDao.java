package com.example.newyear;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionDBDao {
    @Query("SELECT * FROM questiondb")
    List<QuestionDB> getAll();

    @Query("SELECT * FROM questiondb WHERE question_id = :id")
    QuestionDB findByQuestionId(String id);

    @Query("DELETE FROM questiondb")
    void deleteAll();

    @Insert
    void insert(QuestionDB questiondb);

    @Delete
    void delete(QuestionDB questiondb);

    @Update
    void update(QuestionDB questiondb);
}
