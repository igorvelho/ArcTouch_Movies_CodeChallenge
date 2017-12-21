package com.app.arctouch_movies_codechallenge.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igorv on 30/07/2017.
 */

public class SharedPrefsHelper {
    private String KeyGenre;
    private String KeyImages;
    private Context context;

    public SharedPrefsHelper(Context context) {
        this.KeyGenre = "Genre";
        this.KeyImages = "Images";
        this.context = context;
    }

    public void WiteGenre(int genreId, String genre) {
        SharedPreferences.Editor editor = this.context.getSharedPreferences(KeyGenre, 0).edit();
        editor.putString(String.valueOf(genreId), genre);
        editor.commit();
    }

    public String GetGenre(int genreId) {
        return this.context.getSharedPreferences(this.KeyGenre, 0).getString(String.valueOf(genreId), "");
    }

    public void WiteImage(String imageUrl, Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String base64Image = Base64.encodeToString(b, Base64.DEFAULT);

        SharedPreferences.Editor editor = this.context.getSharedPreferences(KeyImages, 0).edit();
        editor.putString(imageUrl, base64Image);
        editor.commit();
    }

    public Bitmap GetImage(String imageUrl) {
        String imageBase64 =  this.context.getSharedPreferences(this.KeyImages, 0).getString(imageUrl, "");
        byte[] imageAsBytes = Base64.decode(imageBase64.getBytes(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        return bmp;
    }

    public boolean ExistsImage(String imageUrl)
    {
        return !this.context.getSharedPreferences(this.KeyImages, 0).getString(imageUrl, "").isEmpty();
    }
}