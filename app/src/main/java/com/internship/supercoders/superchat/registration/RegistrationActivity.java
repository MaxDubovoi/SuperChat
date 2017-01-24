package com.internship.supercoders.superchat.registration;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.internship.supercoders.superchat.MainActivity;
import com.internship.supercoders.superchat.R;
import com.internship.supercoders.superchat.registration.RegistrationView;
import com.squareup.picasso.Picasso;

import id.zelory.compressor.Compressor;
import io.fabric.sdk.android.Fabric;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {
    private EditText emailET, passwordET, conf_passwET, fullnameET, phoneET, websiteET;
    private Button facebookBtn, signupBtn;
    private CircleImageView userPhoto;
    private Toolbar toolbar;
    TextInputLayout input_layout_password, input_layout_conf_password, input_layout_email;
    RegistrationPresenter registrationPresenter;
    ProgressBar progressbar;
    private static final int SELECT_PICTURE = 100;
    Uri selectedImageUri;
    File photo_file;
    File actualImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        emailET = (EditText) findViewById(R.id.input_email);
        passwordET = (EditText) findViewById(R.id.input_password);
        conf_passwET = (EditText) findViewById(R.id.input_confirm_password);
        fullnameET = (EditText) findViewById(R.id.input_fullname);
        phoneET = (EditText) findViewById(R.id.input_phone);
        websiteET = (EditText) findViewById(R.id.input_website);
        userPhoto = (CircleImageView) findViewById(R.id.photo);
        signupBtn = (Button) findViewById(R.id.signup_btn);
        input_layout_password = (TextInputLayout) findViewById(R.id.input_layout_password);
        input_layout_conf_password = (TextInputLayout) findViewById(R.id.input_layout_password2);
        input_layout_email = (TextInputLayout) findViewById(R.id.input_layout_email);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        userPhoto = (CircleImageView) findViewById(R.id.photo);
        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

        registrationPresenter = new RegistrationPresenterImpl(this, new RegistrInteractorImpl());


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String conf_password = conf_passwET.getText().toString().trim();
                String fullname = fullnameET.getText().toString().trim();
                String phone = phoneET.getText().toString().trim();
                String website = websiteET.getText().toString().trim();
                registration(photo_file,email,password,conf_password,fullname,phone,website);
            }
        });

    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setEmailError() {
        input_layout_email.setError("invalid email");
    }

    @Override
    public void setPasswordError() {
        input_layout_conf_password.setError("password is not the same");


    }

    @Override
    public void hidePasswordError() {
        input_layout_conf_password.setError("");
        input_layout_password.setError("");
    }

    @Override
    public void setBlankFields() {
        Toast.makeText(this, "fields cannot be blank", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin(String token) {
        Toast.makeText(this, "registration successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    public void hideEmailError() {
        input_layout_email.setError("");
    }

    @Override
    public void registration( File photo, String email, String password, String conf_password, String fullname, String phone,String website ) {

        boolean valid_data = isValidData(email, password, conf_password);
        if (valid_data)
            registrationPresenter.validateData(photo, email, password, fullname, phone, website);
    }

    @Override
    public void registrationError() {
        Toast.makeText(this, "Registration Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public boolean isValidData(String email, String password, String confirm_password) {
        if (email.equals("") || password.equals("") || confirm_password.equals("") ||
                !password.equals(confirm_password) || !email.contains("@")) {
            if (email.equals("")) {
                input_layout_email.setError("This field cannot be blank");
            }

            if ( password.equals("")) {
                input_layout_password.setError("This field cannot be blank");
            }

            if (confirm_password.equals("")) {
                input_layout_conf_password.setError("This field cannot be blank");
            }

            if (!password.equals(confirm_password)) {
                input_layout_conf_password.setError("Password is not the same");
            }
            if (!email.contains("@")) {
                input_layout_email.setError("Invalid email");
            }
            return false;

        }
        return true;
    }

    @Override
    public Context getContext() {
        return RegistrationActivity.this;
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                Picasso.with(this).load(selectedImageUri).into(userPhoto);
                actualImage = new File(getRealPathFromURI(this, selectedImageUri));
                photo_file = Compressor.getDefault(this).compressToFile(actualImage);
                if (photo_file.exists())
                    Log.d("stas", "file ok");


            }
        }
    }
}
