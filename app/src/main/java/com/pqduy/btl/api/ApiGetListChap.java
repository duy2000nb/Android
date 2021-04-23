package com.pqduy.btl.api;

import android.os.AsyncTask;

import com.pqduy.btl.interfaces.GetListChap;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApiGetListChap extends AsyncTask<Void, Void, Void>{
    Integer idNovel;
    String data;
    GetListChap getListChap;

    public ApiGetListChap(Integer idNovel, GetListChap getListChap) {
        this.idNovel = idNovel;
        this.getListChap = getListChap;
    }

    @Override
    protected void onPreExecute() {
        this.getListChap.batDau();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://laptrinhandroid-k60.000webhostapp.com/getListChap.php?idNovel="+idNovel)
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
    protected void onPostExecute(Void aVoid) {
        if(data == null){
            this.getListChap.biLoi();
        }
        else {
            this.getListChap.ketThuc(data);
        }
    }
}
