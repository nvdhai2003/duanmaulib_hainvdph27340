package hainvdph27340.fpoly.libpn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import hainvdph27340.fpoly.libpn.dao.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edtUsername, edtPassword;
    AppCompatButton btnLogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.primary_color));
        }

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);

        SharedPreferences pref = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        edtUsername.setText(pref.getString("MATT", ""));
        edtPassword.setText(pref.getString("MATKHAU", ""));
        ThuThuDAO dao = new ThuThuDAO(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (!dao.checkLogin(username, password)) {
                    Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("MATT", username);
                    editor.putString("MATKHAU", password);
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}