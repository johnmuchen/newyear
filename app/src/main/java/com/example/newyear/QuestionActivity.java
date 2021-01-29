package com.example.newyear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class QuestionActivity extends AppCompatActivity {

    TextView tvQuestionNumber;
    TextView tvQuestion;
    TextView tvPlayer;
    ImageView ivImage;
    Button btnNextQuestion;
    Button btnPrevQuestion;
    LoadQuestionDB loadQuestionDB;
    SetQuestion setQuestion;
    int question_id=1;
    int playerCnt=0;
    SharedPreferences sharedPreferences;
    static String HAO_ORDER = "HaoOrder";
    static String RUI_ORDER = "RuiOrder";
    static String YUAN_ORDER = "YuanOrder";
    static String Q_NUM_PLAYER_VISIBILITY = "QVisible";
    int playerOrder;
    int HaoOrder;
    int RuiOrder;
    int YuanOrder;
    int QVisibility;
    int [] playOrderArray = {1,2,3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        tvQuestionNumber = (TextView) findViewById(R.id.tvQuestionNumber);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvPlayer = (TextView) findViewById(R.id.tvPlayer);
        btnNextQuestion = (Button) findViewById(R.id.btnNextQuestion);
        btnPrevQuestion = (Button) findViewById(R.id.btnPrevQuestion);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        HaoOrder = sharedPreferences.getInt(HAO_ORDER,1)-1;
        RuiOrder = sharedPreferences.getInt(RUI_ORDER,2)-1;
        YuanOrder = sharedPreferences.getInt(YUAN_ORDER,3)-1;
        QVisibility = sharedPreferences.getInt(Q_NUM_PLAYER_VISIBILITY,1);

//        QVisibility=1;
        if (QVisibility==1) {
            tvQuestionNumber.setVisibility(View.VISIBLE);
            tvPlayer.setVisibility(View.VISIBLE);
        } else {
            tvQuestionNumber.setVisibility(View.INVISIBLE);
            tvPlayer.setVisibility(View.INVISIBLE);
        }

        setQuestion = new SetQuestion() {
            @Override
            public void setQuestionContent(String question_content) {
                tvQuestion.setText(question_content);
            }

            @Override
            public void setQuestionNumber(Integer question_number) {
                tvQuestionNumber.setText(String.valueOf(question_number));
            }

            @Override
            public void setQuestionPlayer(String question_player) {
                tvPlayer.setText(question_player);
            }

            @Override
            public void setQuestionImage(String question_image_url) {
////                LoadImageURL loadImageURL = new LoadImageURL(ivImage);
////                loadImageURL.execute(question_image_url);
//                SetImage setImage = new SetImage() {
//                    @Override
//                    public void SetImageView(Bitmap bitmap) {
//                        ivImage.setImageBitmap(bitmap);
//                    }
//                };
//                LoadImageURL loadImageURL = new LoadImageURL(setImage);
//                loadImageURL.execute(question_image_url);

                String image_addr = getApplicationContext().getExternalFilesDir(null).toString()+
                        "/Images/question_image_"+
                        String.valueOf(tvQuestionNumber.getText().toString())+
                        ".jpg";
                File imgFile = new File(image_addr);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) findViewById(R.id.ivImage);
                    myImage.setImageBitmap(myBitmap);
                }
            }
        };
        loadQuestionDB = new LoadQuestionDB(setQuestion);

        question_id = 1;
        playerCnt = 1;

        playerOrder = getPlayerOrder(playerCnt,question_id);
        loadQuestionDB.loadQuestion(getApplicationContext(),playerOrder);
//        playerCnt = getPlayerCnt(playerCnt);

        btnNextQuestion.setText("下一題");
        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_id = question_id + 1;
                if (question_id>15) {
                    question_id = 1;
                }
                playerCnt = playerCnt + 1;
                if (playerCnt>3) {
                    playerCnt = 1;
                }
                playerOrder = getPlayerOrder(playerCnt,question_id);
                loadQuestionDB.loadQuestion(getApplicationContext(),playerOrder);
//                playerCnt = getPlayerCnt(playerCnt);
            }
        });

        btnPrevQuestion.setText("上一題");
        btnPrevQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_id = question_id - 1;
                if (question_id<1) {
                    question_id = 15;
                }
                playerCnt = playerCnt - 1;
                if (playerCnt<1) {
                    playerCnt = 3;
                }
                playerOrder = getPlayerOrder(playerCnt,question_id);
                loadQuestionDB.loadQuestion(getApplicationContext(),playerOrder);
//                playerCnt = getPlayerCnt(playerCnt);
            }
        });
    }

    int getPlayerCnt(int playerCnt) {
        playerCnt = playerCnt + 1;
        if (playerCnt>3) {
            playerCnt = 1;
        }
        return playerCnt;
    }

    int getPlayerOrder(int playerCnt, int question_id) {
        if (question_id%3==0) {
            playOrderArray[HaoOrder] = question_id-2;
            playOrderArray[RuiOrder] = question_id-1;
            playOrderArray[YuanOrder] = question_id;
        } else if (playerCnt==1) {
            playOrderArray[HaoOrder] = question_id;
            playOrderArray[RuiOrder] = question_id+1;
            playOrderArray[YuanOrder] = question_id+2;
        }

        Log.d("Order",  "playerOrder " +String.valueOf(playOrderArray[playerCnt-1])
                + " player_cnt " + String.valueOf(playerCnt)
                + " question_id "  + String.valueOf(question_id)
                + " " + String.valueOf(HaoOrder)
                + " " + String.valueOf(RuiOrder)
                + " " + String.valueOf(YuanOrder));

        return playOrderArray[playerCnt-1];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}