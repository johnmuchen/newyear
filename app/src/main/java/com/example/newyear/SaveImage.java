package com.example.newyear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public final class SaveImage {

    private Context context;
    private BitmapDrawable bitmapDrawable;
    private FileOutputStream outputStream;

    public SaveImage(Context context, BitmapDrawable bitmapDrawable) {
        this.context = context;
        this.bitmapDrawable = bitmapDrawable;
    }

//    BitmapDrawable drawable = (BitmapDrawable) ivNewImage.getDrawable();
    public void ToStorage(String child){
        Bitmap bitmap = bitmapDrawable.getBitmap();
        File parent = new File(context.getExternalFilesDir(null).toString()+"/Images/");
        parent.mkdirs();
        File file = new File(parent, child);
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    File parent = new File(getApplicationContext().getExternalFilesDir(null),"/Images/");
//    parent.mkdirs();
//    String qNum = tvQuestionNumber.getText().toString();
//    File file = new File(parent, "question_image_"+qNum+".jpg");
//                try {
//        outputStream = new FileOutputStream(file);
//    } catch (
//    FileNotFoundException e) {
//        e.printStackTrace();
//    }
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                Toast.makeText(getApplicationContext(), file.getAbsolutePath() + " saved", Toast.LENGTH_SHORT).show();
//                try {
//        outputStream.flush();
//    } catch (
//    IOException e) {
//        e.printStackTrace();
//    }
//                try {
//        outputStream.close();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
}
