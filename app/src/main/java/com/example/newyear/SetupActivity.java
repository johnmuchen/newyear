package com.example.newyear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.NavigableSet;
import java.util.Set;

public class SetupActivity extends AppCompatActivity{

    String DEFAULT_IMAGE_URL = "https://www.esleschool.com/wp-content/uploads/2019/11/questions.png";
    String DB_NAME = "GameQuestions";

    Button btnLoadImage;
    Button btnDefaultImage;
    EditText etIMageURL;
    ImageView ivNewImage;

    Button btnUpdateQuestion;
    EditText etPlayer;
    EditText etNewQuestion;
    TextView tvQuestionNumber;
    TextView tvQuestion;
    TextView tvPlayer;
    ImageView ivOldImage;
    Button btnResetQuestions;
    //    QuestionDB questionDB;
    Button btnNextQuestion;
    Button btnPlayerVisible;
    OutputStream outputStream;
    SharedPreferences sharedPreferences;
    static String Q_NUM_PLAYER_VISIBILITY = "QVisible";
    int PlayerVisible;

    int question_id;
    LoadQuestionDB loadQuestionDB;
    int current_question_number=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        etIMageURL = (EditText) findViewById(R.id.etImageURL);
        btnLoadImage = (Button) findViewById(R.id.btnLoadImage);
        btnDefaultImage = (Button) findViewById(R.id.btnDefaultImage);
        ivNewImage = (ImageView) findViewById(R.id.ivNewImage);

        btnUpdateQuestion = (Button) findViewById(R.id.btnUpdateQuestion);
        etNewQuestion = (EditText) findViewById(R.id.etNewQuestion);
        etPlayer = (EditText) findViewById(R.id.etPlayer);
        tvQuestionNumber = (TextView) findViewById(R.id.tvQuestionNumber);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        tvPlayer = (TextView) findViewById(R.id.tvPlayer);
        ivOldImage = (ImageView) findViewById(R.id.ivOldImage);
        btnResetQuestions = (Button) findViewById(R.id.btnResetQuestions);
        btnNextQuestion = (Button) findViewById(R.id.btnNextQuestion);
        btnPlayerVisible = (Button) findViewById(R.id.btnPlayerVisible);

        sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        PlayerVisible = 1;
        btnPlayerVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayerVisible==1) {
                    PlayerVisible = 0;
                } else if (PlayerVisible==0) {
                    PlayerVisible = 1;
                }
                Toast.makeText(getApplicationContext(), "PlayerVisible : " + String.valueOf(PlayerVisible), Toast.LENGTH_SHORT).show();
                editor.putInt(Q_NUM_PLAYER_VISIBILITY, PlayerVisible);
                editor.apply();
            }
        });

        SetQuestion setQuestion = new SetQuestion() {
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

//                String image_addr = "/storage/emulated/0/Android/data/com.example.newyear/files/Images/question_image_6.jpg";
                String image_addr = getApplicationContext().getExternalFilesDir(null).toString()+
                        "/Images/question_image_"+
                        String.valueOf(tvQuestionNumber.getText().toString())+
                        ".jpg";
                File imgFile = new File(image_addr);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ImageView myImage = (ImageView) findViewById(R.id.ivOldImage);
                    myImage.setImageBitmap(myBitmap);
                }


//                SetImage setImage = new SetImage() {
//                    @Override
//                    public void SetImageView(Bitmap bitmap) {
//                        ivOldImage.setImageBitmap(bitmap);
//                    }
//                };
//                LoadImageURL loadImageURL = new LoadImageURL(setImage);
//                loadImageURL.execute(question_image_url);
            }
        };

        loadQuestionDB = new LoadQuestionDB(setQuestion);

        btnResetQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuestion();
            }
        });

//        Log.d("Database", getDatabasePath(DB_NAME).getAbsolutePath());
//        Log.d("Database", getDatabasePath(DB_NAME).getName());
//        QuestionDB questionDB;
        question_id = 1;
        if (getDatabasePath(DB_NAME).exists()) {
//            Log.d("Database", "database found");
            loadQuestionDB.loadQuestion(getApplicationContext(),question_id);
        } else {
//            Log.d("Database", "database not found");
            Toast.makeText(getApplicationContext(), "No Questions", Toast.LENGTH_SHORT).show();
        }

        btnLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String urlLink = etIMageURL.getText().toString();
                if (urlLink.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Url!!", Toast.LENGTH_SHORT).show();
                } else {
//                    LoadImageURL loadImageURL = new LoadImageURL(ivNewImage);
//                    loadImageURL.execute(urlLink);
                    SetImage setImage = new SetImage() {
                        @Override
                        public void SetImageView(Bitmap bitmap) {
                            ivNewImage.setImageBitmap(bitmap);
                        }
                    };
                    LoadImageURL loadImageURL = new LoadImageURL(setImage);
                    loadImageURL.execute(urlLink);
                }
            }
        });

        btnDefaultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlLink = DEFAULT_IMAGE_URL;
                etIMageURL.setText(urlLink);
//                LoadImageURL loadImageURL = new LoadImageURL(ivNewImage);
//                loadImageURL.execute(urlLink);
                SetImage setImage = new SetImage() {
                    @Override
                    public void SetImageView(Bitmap bitmap) {
                        ivNewImage.setImageBitmap(bitmap);
                    }
                };
                LoadImageURL loadImageURL = new LoadImageURL(setImage);
                loadImageURL.execute(urlLink);
            }
        });

//        btnSaveImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BitmapDrawable drawable = (BitmapDrawable) ivNewImage.getDrawable();
//                Bitmap bitmap = drawable.getBitmap();
//                File parent = new File(getApplicationContext().getExternalFilesDir(null),"/Images/");
//                parent.mkdirs();
//                String qNum = tvQuestionNumber.getText().toString();
//                File file = new File(parent, "question_image_"+qNum+".jpg");
//                try {
//                    outputStream = new FileOutputStream(file);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                Toast.makeText(getApplicationContext(), file.getAbsolutePath() + " saved", Toast.LENGTH_SHORT).show();
//                try {
//                    outputStream.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        btnUpdateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(question_id);
            }
        });

        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question_id = question_id + 1;
                if (question_id > 15) {
                    question_id = 1;
                }
                loadQuestionDB.loadQuestion(getApplicationContext(),question_id);
            }
        });
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

    private void updateQuestion(int question_id) {
        final String sQuestion = etNewQuestion.getText().toString().trim();
        final String sPlayer = etPlayer.getText().toString().trim();
        final String sImageURL = etIMageURL.getText().toString().trim();

        if (sQuestion.isEmpty()) {
            etNewQuestion.setError("Question Required");
            etNewQuestion.requestFocus();
            return;
        }

        if (sPlayer.isEmpty()) {
            etPlayer.setError("Player Required");
            etPlayer.requestFocus();
            return;
        }

        if (sImageURL.isEmpty()) {
            etIMageURL.setError("Image URL Required");
            etIMageURL.requestFocus();
//            etIMageURL.setText(DEFAULT_IMAGE_URL);
            return;
        }

        class UpdateQuestion extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                QuestionDB questionDB = new QuestionDB();
                questionDB.setQuestion_id(question_id);
                questionDB.setQuestion_content(sQuestion);
                questionDB.setQuestion_player(sPlayer);
                questionDB.setQuestion_image_addr(sImageURL);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .questionDBDao()
                        .update(questionDB);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                finish();
//                startActivity(new Intent(getApplicationContext(), SetupActivity.class));
                etNewQuestion.setText("");
                etPlayer.setText("");
                etIMageURL.setText("");
                BitmapDrawable drawable = (BitmapDrawable) ivNewImage.getDrawable();
                SaveImage saveImage = new SaveImage(getApplicationContext(), drawable);
                saveImage.ToStorage("question_image_"+String.valueOf(tvQuestionNumber)+".jpg");
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }
        UpdateQuestion updateQuestion = new UpdateQuestion();
        updateQuestion.execute();
    }

    private void deleteQuestion() {
        class ClearQuestion extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
//                if (getDatabasePath(DB_NAME).exists()) {
//                    getApplicationContext().deleteDatabase("GameQuestions");
//                }
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .questionDBDao()
                        .deleteAll();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Database Deleted", Toast.LENGTH_LONG).show();
                initQuestion();
            }
        }

        ClearQuestion clearQuestion = new ClearQuestion();
        clearQuestion.execute();

    }

    private void initQuestion() {

        String[] sQuestions = {
                "6+9、15+1，哪個比較大", "Hao", "https://pic.17qq.com/uploads/qhswhqmhkky.jpeg",
                "Pineapple是什麼水果", "Rui", "https://freedesignfile.com/upload/2019/09/Funny-fruit-cartoon-emoticon-vector.jpg",
                "什麼顏色是Red", "Yuan", "https://i.ytimg.com/vi/xW2bjae5LPM/maxresdefault.jpg",
                "阿嬤的名字", "Hao", "https://avatarfiles.alphacoders.com/152/152686.jpg",
                "蝌蚪長大變什麼", "Rui", "https://hendrickscountyparks.org/wp-content/uploads/2020/01/tadpole-2.jpg",
                "什麼動物有4隻腳", "Yuan", "https://assets.stickpng.com/images/580b57fcd9996e24bc43c319.png",
                "關於蘋果的祝福話", "Hao", "https://i2.wp.com/ceklog.kindel.com/wp-content/uploads/2013/02/firefox_2018-07-10_07-50-11.png",
                "Michael的生日是幾月幾號", "Rui", "https://i.ebayimg.com/images/g/p5AAAOSw~gRVwlC5/s-l400.jpg",
                "蝴蝶3的後面是什麼", "Yuan", "https://ku.90sjimg.com/element_origin_min_pic/18/03/29/4d88543d17b9584a4951675a5420b9ce.jpg",
                "5,10,__,20,25", "Hao", "https://www.mymathtables.com/tips-and-tricks/img/5-table-icon.png",
                "下面有幾顆氣球", "Rui", "https://fs1.shop123.com.tw/300095/upload/product/3000955142pic_outside_393412.jpg",
                "Bee吃什麼", "Yuan", "https://freedesignfile.com/upload/2019/09/Illustration-cartoon-cute-bee-vector.jpg",
                "下面圖案是幾點鐘", "Hao", "https://www.ikea.com/mx/en/images/products/tjalla-wall-clock__0633571_PE695905_S5.JPG",
                "2張貼紙要再加上幾張，才會變成3張貼紙", "Rui", "https://png.pngtree.com/png-vector/20190130/ourlarge/pngtree-cartoon-carrot-sticker-sticker-png-image_590062.jpg",
                "什麼數字是Three", "Yuan", "https://img.freepik.com/free-vector/cartoon-kids-with-123-numbers_97632-620.jpg?size=338&ext=jpg",
        };
        class InitQuestion extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                String sQuestion = strings[0];
                String sPlayer = strings[1];
                String sImageURL = strings[2];
                int iQuestion_id = Integer.parseInt(strings[3]);
                QuestionDB questionDB = new QuestionDB();
                questionDB.setQuestion_id(iQuestion_id);
                questionDB.setQuestion_content(sQuestion);
                questionDB.setQuestion_player(sPlayer);
                questionDB.setQuestion_image_addr(sImageURL);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .questionDBDao()
                        .insert(questionDB);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
//                finish();
//                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

//        SQLiteDatabase database;
//        database = SQLiteDatabase.openOrCreateDatabase(getApplicationContext().getDatabasePath(DB_NAME), null);
//        String deleteTable = "DELETE FROM " + DB_NAME;
//        database.execSQL(deleteTable);
//
//        String deleteSqliteSequence = "DELETE FROM sqlite_sequence WHERE name = '" + DB_NAME + "'";
//        database.execSQL(deleteSqliteSequence);

//        ClearQuestion clearQuestion = new ClearQuestion();
//        clearQuestion.execute();

//        ClearQuestionSeq clearQuestionSeq = new ClearQuestionSeq();
//        clearQuestionSeq.execute();

        for (int i = 0; i < sQuestions.length / 3; i++) {
            String[] sQ = {sQuestions[i * 3],
                    sQuestions[i * 3 + 1],
                    sQuestions[i * 3 + 2],
                    String.valueOf(i + 1)
            };
//            Log.d("QArray", sQuestions[i * 3] + " " + String.valueOf(i));
            InitQuestion initQuestion = new InitQuestion();
            initQuestion.execute(sQ);

            SetImage setImage = new SetImage() {
                @Override
                public void SetImageView(Bitmap bitmap) {
                    ivOldImage.setImageBitmap(bitmap);
                    BitmapDrawable drawable = (BitmapDrawable) ivOldImage.getDrawable();
                    SaveImage saveImage = new SaveImage(getApplicationContext(), drawable);
                    saveImage.ToStorage("question_image_"+String.valueOf(current_question_number+1)+".jpg");
                    Log.d("ResetQ2", "qi_"+String.valueOf(current_question_number+1));
                    current_question_number = current_question_number + 1;
                    if (current_question_number==15) {
                        current_question_number=0;
                    }
                }
            };
            LoadImageURL loadImageURL = new LoadImageURL(setImage);
            loadImageURL.execute(sQuestions[i * 3 + 2]);
        }
        Toast.makeText(getApplicationContext(), "Database Init", Toast.LENGTH_LONG).show();
    }

//
//    private static final int GALLERY_PHOTO = 111;
//    private void pickImage(){
//        Intent chooserIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        chooserIntent.setType("image/*");
//        startActivityForResult(chooserIntent, GALLERY_PHOTO );
//    }
//
////    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == GALLERY_PHOTO  && data.getData() != null) {
////            String filePath = GetFilePathFromDevice.getPath(SetupActivity.this, data.getData());
////            tvFileLocation.setText("path "+filePath);
////            ImageView imageView = (ImageView) findViewById(R.id.ivNewImage);
////            imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
//        } else {
////            String filepath = data.getData().toString();
//            String filePath = null;
//            try {
//                filePath = PathUtil.getPath(SetupActivity.this, data.getData());
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
////            tvFileLocation.setText("path " + String.valueOf(requestCode) + " " + filePath);
////            ImageView imageView = (ImageView) findViewById(R.id.ivNewImage);
////            imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
////            File imgFile = new File(getRealPathFromURI(selectedImageURI));
////            Bitmap myBitmap = BitmapFactory.decodeFile(filePath);
////            ImageView ivNewImage = (ImageView) findViewById(R.id.ivNewImage);
////            ivNewImage.setImageBitmap(myBitmap);
//
//            File imgFile = new  File("/storage/emulated/0/Download/1280px-HardCandy.jpg");
//            if(imgFile.exists()){
////                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                Bitmap myBitmap = BitmapFactory.decodeByteArray(bitmapProfilePic, 0, 10);
////                ImageView myImage = (ImageView) findViewById(R.id.ivNewImage);
////                ImageView myImage = (ImageView) findViewById(R.id.ivOldImage);
////                Bitmap myPictureBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                myPictureBitmap = Bitmap.createScaledBitmap(myPictureBitmap, myImage.getWidth(),myImage.getHeight(),true);
////                myImage.setImageBitmap(myPictureBitmap);
//
//                ImageView myImage = (ImageView) findViewById(R.id.ivOldImage);
//                Bitmap bmImg = BitmapFactory.decodeFile("/storage/emulated/0/Download/1280px-HardCandy.jpg");
//                myImage.setImageBitmap(bmImg);
//
////                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                ImageView myImage = (ImageView) findViewById(R.id.ivOldImage);
////                myImage.setImageBitmap(myBitmap);
//
//                tvFileLocation.setText("File Found "+filePath);
//            } else {
//                tvFileLocation.setText("Not Found "+filePath);
//            }
//
//        }

//        switch (requestCode) {
//            case 10:
//                if (resultCode == RESULT_OK) {
//                    Uri uri = data.getData();
//                    File file = new File(uri.getPath());//create path from uri
//                    final String[] split = file.getPath().split(":");//split the path.
//                    String filePath = split[1];//assign it to a string(your choice).
//
////                    String path = data.getData().getPath();
////                    tvFileLocation.setText("path "+path+" "+filePath);
////                    File imgFile = new File(path);
//
////                    Uri selectedImageURI = data.getData();
////                    String path = getRealPathFromURI(selectedImageURI);
//
//                    String path = GetFilePathFromDevice.getPath(SetupActivity.this, data.getData());
//                    tvFileLocation.setText("path "+path+" "+filePath);
////                    File imgFile = new File(getRealPathFromURI(selectedImageURI));
//
////                    if (imgFile.exists()) {
////                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                        ImageView ivNewImage = (ImageView) findViewById(R.id.ivNewImage);
////                        ivNewImage.setImageBitmap(myBitmap);
////                    }
//                }
//                break;
//        }
//    }

//    private String getRealPathFromURI(Uri contentURI) {
//        String result;
//        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) { // Source is Dropbox or other similar local file path
//            result = contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            result = cursor.getString(idx);
//            cursor.close();
//        }
//        return result;
//    }
}