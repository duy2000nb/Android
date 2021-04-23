package com.pqduy.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pqduy.btl.api.ApiSignUp;
import com.pqduy.btl.interfaces.SignUp;

public class SignUpActivity extends AppCompatActivity implements SignUp {
    EditText edtUsename, edtPassword, edtConfirm;
    Button btSignUp;
    String regexUsername = "^[a-z0-9_-]{3,20}$"; //Tên đăng nhập chỉ gồm chữ cái, số, gạch dưới và gạch ngang, có độ dài từ 3->20
    String regexPassword = "^[a-z0-9_-]{6,20}$"; //Mật khẩu chỉ gồm chữ cái, số, gạch dưới và gạch ngang, có độ dài từ 6->20
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        anhXa();
        setClick();
    }

    private void anhXa(){
        edtUsename  = (EditText) findViewById(R.id.edtSignUpUsername);
        edtPassword = (EditText) findViewById(R.id.edtSignUpPassword);
        edtConfirm  = (EditText) findViewById(R.id.edtSignUpConfirmPassword);
        btSignUp    = (Button) findViewById(R.id.btSignUp);
    }

    private void setClick() {
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username         = edtUsename.getText().toString();
                String password         = edtPassword.getText().toString();
                String confirmPassword  = edtConfirm.getText().toString();
                boolean checkUsername = username.matches(regexUsername);
                boolean checkPassword = password.matches(regexPassword);
                boolean checkConfirmPassword = password.equals(confirmPassword);
                if(!checkUsername){
                    Toast.makeText(context,"Tên đăng nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!checkPassword){
                        Toast.makeText(context,"Mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if(!checkConfirmPassword) {
                            Toast.makeText(context, "Mật khẩu và mật khẩu xác nhận không trùng nhau", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            ApiSignUp signUpAsynTask = new ApiSignUp((SignUp) context, username, password);
                            signUpAsynTask.execute();
                        }
                    }

                }
            }
        });
    }

    @Override
    public void batDau() {

    }

    @Override
    public void ketThuc(String data) {
        if(data.equals("1")){
            Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(context, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
        }
    }
}
