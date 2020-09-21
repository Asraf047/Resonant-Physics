package com.amanullah.myapplication98.ui.auth;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amanullah.myapplication98.MainActivity;
import com.amanullah.myapplication98.R;
import com.amanullah.myapplication98.model.UserItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class CreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "CreateAccountActivity";
    private static final int PERMISSION_REQUEST_CODE = 1 ;
    private static final int IMAGE_REQUEST_CODE = 1 ;

    private EditText userEmail,userName,phone;
    private ProgressBar loadingProgress,otp_progress_bar;
    private Button regBtn;
    private ImageView ImgUserPhoto;
    private CardView profile_image_holder;
    private Uri mImageUri = null;

    private FirebaseFirestore db;
    private CollectionReference userRef;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private String phoneNo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        userName = findViewById(R.id.tv_name);
        userEmail = findViewById(R.id.tv_email);
        phone = findViewById(R.id.tv_phone);
        ImgUserPhoto = findViewById(R.id.profile_image) ;
        profile_image_holder = findViewById(R.id.profile_image_holder) ;
        regBtn = findViewById(R.id.save_btn);
        loadingProgress = findViewById(R.id.regProgressBar);
        otp_progress_bar = findViewById(R.id.otp_progress_bar);
        loadingProgress.setVisibility(View.GONE);
        otp_progress_bar.setVisibility(View.GONE);

        phoneNo = getIntent().getStringExtra("phoneNo");
        phone.setText("+88" + phoneNo);

        profile_image_holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    openGallery();
                }
            }});

        db = FirebaseFirestore.getInstance();
        userRef = db.collection("user_list");
        mStorageRef = FirebaseStorage.getInstance().getReference("profile_pic");

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString().trim();

                if (name.isEmpty()) {
                    userName.setError("Please, enter your name");
                    userName.requestFocus();
                } else if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(CreateAccountActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });


        changeColor();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (FirebaseAuth.getInstance().getUid() != null && phoneNo != null){
            otp_progress_bar.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.VISIBLE);
            if (mImageUri != null) {
                StorageReference fileReference = mStorageRef.child(phoneNo + "." + getFileExtension(mImageUri));
                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                if (taskSnapshot.getMetadata() != null) {
                                    if (taskSnapshot.getMetadata().getReference() != null) {
                                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUrl = uri.toString();
                                                UserItem nUserItem = new UserItem();
                                                nUserItem.setPic_url(imageUrl);
                                                nUserItem.setUsername(userName.getText().toString().trim());
                                                nUserItem.setEmail(userEmail.getText().toString().trim());
                                                nUserItem.setPhone(phoneNo);
                                                nUserItem.setReferral_code(phoneNo);
                                                nUserItem.setReferral_done(String.valueOf(0));
                                                nUserItem.setReferral_purchase(String.valueOf(0));
                                                nUserItem.setReferral_balance(String.valueOf(0));
                                                userRef.document(FirebaseAuth.getInstance().getUid()).set(nUserItem)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d(TAG, "onSuccess: 100");
                                                                setProgressBer(100);
                                                                Toast.makeText(CreateAccountActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                otp_progress_bar.setVisibility(View.GONE);
                                                                loadingProgress.setVisibility(View.GONE);
                                                                Toast.makeText(CreateAccountActivity.this, "Upload failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        });
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                otp_progress_bar.setVisibility(View.GONE);
                                loadingProgress.setVisibility(View.GONE);
                                Toast.makeText(CreateAccountActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                if (progress < 95) {
                                    setProgressBer((int) progress);
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                UserItem nUserItem = new UserItem();
                nUserItem.setUsername(userName.getText().toString().trim());
                nUserItem.setEmail(userEmail.getText().toString().trim());
                nUserItem.setPhone(phoneNo);
                nUserItem.setReferral_code(phoneNo);
                nUserItem.setReferral_done(String.valueOf(0));
                nUserItem.setReferral_purchase(String.valueOf(0));
                nUserItem.setReferral_balance(String.valueOf(0));
                userRef.document(FirebaseAuth.getInstance().getUid()).set(nUserItem)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: 100");
                                setProgressBer(100);
                                Toast.makeText(CreateAccountActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                otp_progress_bar.setVisibility(View.GONE);
                                loadingProgress.setVisibility(View.GONE);
                                Toast.makeText(CreateAccountActivity.this, "Upload failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(CreateAccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CreateAccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(CreateAccountActivity.this,"Please allow Storage Permission To upload image from gallery.",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(CreateAccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(CreateAccountActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
        else {
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(CreateAccountActivity.this, "Please allow Storage Permission To upload image from gallery.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE && data != null && data.getData() != null) {
            mImageUri = data.getData();
            ImgUserPhoto.setImageURI(data.getData());
        } else {
            Toast.makeText(CreateAccountActivity.this, "No image selected", Toast.LENGTH_LONG).show();
        }
    }

    public void setProgressBer(int progress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingProgress.setProgress(progress);
            }
        });
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

    private void openImagesActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}