package com.pqduy.btl.api;

import android.os.AsyncTask;

import com.pqduy.btl.interfaces.GetContentChap;
import com.pqduy.btl.object.Chap;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class ApiGetContentChap extends AsyncTask<Void,Void,Void> {
    String data;
    GetContentChap getContentChap;
    Integer numberChapter, idNovel;

    public ApiGetContentChap(GetContentChap getContentChap, Integer numberChapter, Integer idNovel) {
        this.getContentChap = getContentChap;
        this.numberChapter  = numberChapter;
        this.idNovel        = idNovel;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://laptrinhandroid-k60.000webhostapp.com/getContentChap.php?numberChapter=" + numberChapter
                        + "&idNovel="+idNovel)
                .build();
        data = null;
        try{
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            data = body.string();
        }
        catch (IOException e){
            data = null;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        this.getContentChap.ketThuc(data);
    }
}
