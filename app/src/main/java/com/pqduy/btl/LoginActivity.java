package com.pqduy.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pqduy.btl.api.ApiLogin;
import com.pqduy.btl.interfaces.Login;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Login {
    EditText edtUsername;
    EditText edtPassword;
    Button btLogin;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        anhXa();
        setClick();
    }

    private void anhXa(){
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btLogin     = (Button) findViewById(R.id.btLogin);
    }

    private void setClick(){
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiLogin loginAsynTask = new ApiLogin((Login) context, edtUsername.getText().toString(), edtPassword.getText().toString());
                loginAsynTask.execute();
            }
        });
    }

    @Override
    public void batDau() {
    }

    @Override
    public void ketThuc(String data) {
        JSONObject jsonObject;
        Integer id;
        try {
            jsonObject = new JSONObject(data);
            id = jsonObject.getInt("id");
        } catch (JSONException e) {
            return;
        }
        if(id == 0){
            Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(context, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id", id);
            bundle.putString("username", edtUsername.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}