package com.amanullah.myapplication98.ui.auth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amanullah.myapplication98.MainActivity;
import com.amanullah.myapplication98.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {
    private static final String TAG = "VerifyPhoneActivity";

    private String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText;
    private TextView login_title, login_desc;
    private Button verify_btn;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();

        login_title= findViewById(R.id.login_title);
        login_desc = findViewById(R.id.login_desc);
        editText = findViewById(R.id.otp_text_view);
        progressBar = findViewById(R.id.otp_progress_bar);
        verify_btn = findViewById(R.id.verify_btn);

        number = getIntent().getStringExtra("number");
        sendVerificationCode("+88" + number);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editText.getText().toString().trim();
                if (code.length() != 6) {
                    editText.setError("Enter right OTP");
                    editText.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        changeColor();
    }

    private void verifyCode(String code) {
        if(verificationId!=null) {
            progressBar.setVisibility(View.VISIBLE);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithCredential(credential);
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                                Log.d(TAG, "onCreate: "+FirebaseAuth.getInstance().getUid());
                                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                                DocumentReference docIdRef = rootRef.collection("user_list").document(FirebaseAuth.getInstance().getUid());
                                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "Document exists!");
                                                Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Log.d(TAG, "Document does not exist!");
                                                Intent intent = new Intent(VerifyPhoneActivity.this, CreateAccountActivity.class);
                                                intent.putExtra("phoneNo", number);
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            Log.d(TAG, "Failed with: ", task.getException());
                                            Toast.makeText(VerifyPhoneActivity.this, "Failed to get document because cannot connect to internet", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                login_title.setVisibility(View.INVISIBLE);
                login_desc.setVisibility(View.INVISIBLE);
                verify_btn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                editText.setFocusable(false);
                editText.setHint("Sending verification code");
            }
        });
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                120,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            Log.d(TAG, "onCodeSent: "+s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    login_title.setVisibility(View.VISIBLE);
                    login_desc.setVisibility(View.VISIBLE);
                    verify_btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    editText.setFocusableInTouchMode(true);
                    editText.setHint("Enter OTP");
                }
            });
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
//            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            createAlertDialog1(VerifyPhoneActivity.this,"Faild", "Please, connect to internet. "+e.getMessage(),false);
        }
    };


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

    public void createAlertDialog1(Context context, String title, String msg, boolean isCancelable){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(isCancelable);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onBackPressed();
            }
        });

        AlertDialog alert = dialog.create();
        alert.show();
    }
}
