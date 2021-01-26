package com.example.newyear;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class LoadImageURL extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;
    SetImage setImage = null;

    public LoadImageURL(SetImage setImage) {
        this.setImage = setImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlLink = strings[0];
        Bitmap bitmap = null;
        Log.d("LoadImage", urlLink);
        try {
            InputStream inputStream = new java.net.URL(urlLink).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
//            super.onPostExecute(bitmap);
        setImage.SetImageView(bitmap);
//        Log.d("LoadImage", "get bitmap ");
    }
}

