package com.amanullah.myapplication98.ui.auth;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.amanullah.myapplication98.MainActivity;
import com.amanullah.myapplication98.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText phone_number_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone_number_text = findViewById(R.id.phone_number_text);

        findViewById(R.id.generate_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phone_number_text.getText().toString().trim();

                if (number.isEmpty() || number.length() < 11) {
                    phone_number_text.setError("Valid number is required");
                    phone_number_text.requestFocus();
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);

            }
        });

        changeColor();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void changeColor() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int flags = getWindow().getDecorView().getSystemUiVisibility();
                    flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    getWindow().getDecorView().setSystemUiVisibility(flags);
                    getWindow().setStatusBarColor(Color.WHITE);
                }
            }
        });
    }
}
