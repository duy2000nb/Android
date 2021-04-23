package com.pqduy.btl.api;

import android.os.AsyncTask;


import com.pqduy.btl.interfaces.SignUp;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

public class ApiSignUp extends AsyncTask<Void, Void, Void> {
    SignUp signUp;
    String username, password;
    String data;

    public ApiSignUp(SignUp signUp, String username, String password) {
        this.signUp = signUp;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        this.signUp.batDau();
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
                .url("https://laptrinhandroid-k60.000webhostapp.com/signUp.php")
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
        this.signUp.ketThuc(data);
    }

}
