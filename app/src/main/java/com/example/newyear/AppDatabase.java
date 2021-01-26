package com.example.newyear;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {QuestionDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDBDao questionDBDao();
}
