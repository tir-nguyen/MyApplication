package com.example.quiz;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Entity
public class Question {
   @PrimaryKey(autoGenerate = true)
   public int id;

   @ColumnInfo(name = "content")
   public String content;

   @ColumnInfo(name = "options")
   public String options;

   @ColumnInfo(name = "answer")
   public String answer;

    @Dao
    public interface QuestionDao {
        @Insert
        void insert(Question question);
        @Insert
        void insertAll(List<Question> questions);
        @Query("SELECT * FROM question WHERE id = :questionId")
        Question getById(int questionId);

        @Update
        void update(Question question);

        List<Question> getAllQuestions();
    }
}