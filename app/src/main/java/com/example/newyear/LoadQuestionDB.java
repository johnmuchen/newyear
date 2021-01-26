package com.example.newyear;

import android.content.Context;
import android.os.AsyncTask;

public class LoadQuestionDB {
    SetQuestion setQuestion = null;

    public LoadQuestionDB(SetQuestion setQuestion) {
        this.setQuestion = setQuestion;
    }
    public void loadQuestion(Context context, int question_id) {

        class LoadQuestion extends AsyncTask<String, Void, QuestionDB> {

            @Override
            protected QuestionDB doInBackground(String... strings) {
                String qid = strings[0];
                QuestionDB questionDB = DatabaseClient
                        .getInstance(context)
                        .getAppDatabase()
                        .questionDBDao()
                        .findByQuestionId(qid);
                return questionDB;
            }

            @Override
            protected void onPostExecute(QuestionDB questionDB) {
                super.onPostExecute(questionDB);
                setQuestion.setQuestionNumber(questionDB.getQuestion_id());
//                setQuestion.setQuestionNumber(question_id);
                setQuestion.setQuestionContent(questionDB.getQuestion_content());
                setQuestion.setQuestionPlayer(questionDB.getQuestion_player());
                setQuestion.setQuestionImage(questionDB.getQuestion_image_addr());
//                tvQuestionNumber.setText(String.valueOf(questionDB.getQuestion_id()));
//                tvQuestion.setText(questionDB.getQuestion_content());
//                tvPlayer.setText(questionDB.getQuestion_player());
//                SetupActivity.LoadImage loadImage = new SetupActivity.LoadImage(ivOldImage);
//                loadImage.execute(questionDB.getQuestion_image_addr());

//                Intent intent = new Intent(getApplicationContext(), SetupActivity.class);
//                intent.putExtra("questiondb", questionDB);
//                getApplicationContext().startActivity(intent);
            }

        }
        LoadQuestion loadQuestion = new LoadQuestion();
        loadQuestion.execute(String.valueOf(question_id));
    }

}
