package com.pqduy.btl.api;

import android.os.AsyncTask;

import com.pqduy.btl.interfaces.Login;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class ApiLogin extends AsyncTask<Void, Void, Void> {
    Login login;
    String username, password;
    String data;

    public ApiLogin(Login login, String username, String password) {
        this.login = login;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        this.login.batDau();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody =  new MultipartBuilder()
                .addFormDataPart("username", this.username)
                .addFormDataPart("password", this.password)
                .type(MultipartBuilder.FORM)
                .build();
        Request request = new Request.Builder()
                .url("https://laptrinhandroid-k60.000webhostapp.com/login.php")
                .post(requestBody)
                .build();
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
        this.login.ketThuc(data);
    }

}
