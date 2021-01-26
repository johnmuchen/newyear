package com.example.newyear;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class QuestionDB implements Serializable {
    @PrimaryKey(autoGenerate = false)
    private int question_id;

    @ColumnInfo(name = "question_player")
    private String question_player;

    @ColumnInfo(name = "question_content")
    private String question_content;

    @ColumnInfo(name = "question_image_addr")
    private String question_image_addr;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_player() {
        return question_player;
    }

    public void setQuestion_player(String question_player) {
        this.question_player = question_player;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getQuestion_image_addr() {
        return question_image_addr;
    }

    public void setQuestion_image_addr(String question_image_addr) {
        this.question_image_addr = question_image_addr;
    }
}
