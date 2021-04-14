package com.pqduy.btl.api;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.pqduy.btl.interfaces.GetNovel;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApiGetNovel extends AsyncTask<Void, Void, Void> {
    String data = null;
    GetNovel getNovel;
    final String dbName = "/data/data/com.pqduy.btl/databases/novel.db";
    SQLiteDatabase db;
    boolean check = false;

    public ApiGetNovel(GetNovel getNovel) {
        this.getNovel = getNovel;
    }

    private String getTimeUpdateFromServer(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://laptrinhandroid-k60.000webhostapp.com/getTimeUpdate.php")
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
        if(data != null){
            try{
                JSONObject object = new JSONObject(data);
                return object.getString("time");
            }
            catch (JSONException e){

            }
        }
        return null;
    }

    private boolean checkTimeUpdate(){
        boolean check = false;
        db = db.openOrCreateDatabase(dbName, null);
        String time = getTimeUpdateFromServer();
        String sql = "SELECT time FROM timeUpdate LIMIT 1";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if(time == cursor.getString(0)){
            check = true;
        }
        else{
            sql = "UPDATE timeUpdate SET time = ?";
            db.execSQL(sql, new String[]{time});
        }
        cursor.close();
        db.close();
        return check;
    }

    private void getNovelFromServer(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://laptrinhandroid-k60.000webhostapp.com/getListNovel.php")
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
    }

    @Override
    protected void onPreExecute() {
        this.getNovel.batDau();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (checkTimeUpdate()){
            data = null;
            check = true;
        }
        else{
            getNovelFromServer();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(data == null){
            if(!check){
                this.getNovel.biLoi();
            }
            this.getNovel.ketThuc();
        }
        else{
            this.getNovel.ketThuc(data);
        }
    }
}
