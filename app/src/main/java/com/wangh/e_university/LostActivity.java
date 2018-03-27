package com.wangh.e_university;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class LostActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private static final int PERMISSION_PICK_PHOTO = 3;
    private static final int PERMISSION_TAKE_PHOTO = 4;
    private static final String PHOTO_DIR = "eUniversity/";
    private static final String TAG = "LostActivity";


    private TextInputLayout mTitleTextInputLayout;
    private TextInputLayout mPhoneTextInputLayout;
    private TextInputLayout mDescriptionTextInputLayout;
    private Button mTakePhotoButton;
    private Button mPickPhotoButton;
    private Button mSubmitButton;
    private Switch mLostOrFoundSwitch;
    private ImageView mPhotoImageView;
    private Toolbar mToolbar;

    private String title;
    private String phone;
    private String description;
    private String path;
    private BmobFile photo;

    private Uri photoUri;
    private File tempPhoto;
    private boolean hasPhoto;
    private boolean found = false;
    private LostProperty lostProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lost);
        mTitleTextInputLayout = (TextInputLayout) findViewById(R.id.lostTitle);
        mPhoneTextInputLayout = (TextInputLayout) findViewById(R.id.lostPhone);
        mDescriptionTextInputLayout = (TextInputLayout) findViewById(R.id.lostDescription);
        mTakePhotoButton = (Button) findViewById(R.id.lostTakePhoto);
        mPickPhotoButton = (Button) findViewById(R.id.lostPickPhoto);
        mSubmitButton = (Button) findViewById(R.id.lostSubmit);
        mPhotoImageView = (ImageView) findViewById(R.id.lostPhoto);
        mLostOrFoundSwitch = (Switch) findViewById(R.id.lostOrFound);
        mToolbar = (Toolbar) findViewById(R.id.toolbarOfLost);

        mToolbar.setNavigationIcon(R.drawable.arrow_left);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mLostOrFoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!compoundButton.isChecked()) {
                    compoundButton.setHint(R.string.lost_lost);
                    found = false;
                } else {
                    compoundButton.setHint(R.string.lost_found);
                    found = true;
                }
            }
        });
        mTakePhotoButton.setOnClickListener(this);
        mPickPhotoButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
    }

    private void pickPhoto() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, PICK_PHOTO);
    }

    private void takePhoto() {
        File file = new File(Environment.getExternalStorageDirectory(), PHOTO_DIR);
        if (!file.exists()) {
            file.mkdir();
        }
        tempPhoto = new File(file, System.currentTimeMillis() + ".jpg");
//        photoUri = Uri.fromFile(tempPhoto);
        photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".photo", tempPhoto);
        path = tempPhoto.getPath();
        Log.d(TAG, "takePhoto: path " + path);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        i.setData(photoUri);
//        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        i.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(i, TAKE_PHOTO);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lostTakePhoto:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_TAKE_PHOTO);
                } else {
                    takePhoto();
                }
                break;
            case R.id.lostPickPhoto:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_PICK_PHOTO);
                } else {
                    pickPhoto();
                }
                break;
            case R.id.lostSubmit:
                title = mTitleTextInputLayout.getEditText().getText().toString();
                phone = mPhoneTextInputLayout.getEditText().getText().toString();
                description = mDescriptionTextInputLayout.getEditText().getText().toString();
                boolean hasEmpty = false;
                if (title.isEmpty()) {
                    mTitleTextInputLayout.setErrorEnabled(true);
                    mTitleTextInputLayout.setError("请输入标题");
                    hasEmpty = true;
                } else {
                    mTitleTextInputLayout.setErrorEnabled(false);
                    mTitleTextInputLayout.setError("");
                }
                if (phone.isEmpty()) {
                    mPhoneTextInputLayout.setErrorEnabled(true);
                    mPhoneTextInputLayout.setError("请输入电话");
                    hasEmpty = true;
                } else {
                    mPhoneTextInputLayout.setErrorEnabled(false);
                    mPhoneTextInputLayout.setError("");
                }
                if (description.isEmpty()) {
                    mDescriptionTextInputLayout.setErrorEnabled(true);
                    mDescriptionTextInputLayout.setError("请输入描述");
                    hasEmpty = true;
                } else {
                    mDescriptionTextInputLayout.setErrorEnabled(false);
                    mDescriptionTextInputLayout.setError("");
                }
                if (hasEmpty) {
                    break;
                }
                lostProperty =
                        new LostProperty(title, "20152739", phone, description, photo, found);
                if (hasPhoto) {
                    photo.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.d(TAG, "done: photo uploaded");
                                uploadLostProperty(lostProperty);
                            } else {
                                Toast.makeText(getApplication(), "上传失败，请重试", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "done: photo upload failed " + e.getMessage());
                            }
                        }
                    });
                } else {
                    uploadLostProperty(lostProperty);
                }

                break;
            default:
                break;
        }
    }

    private void uploadLostProperty(LostProperty lostProperty) {
        lostProperty.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "done: upload success");
                    finish();
                } else {
                    Toast.makeText(getApplication(), "上传失败，请重试", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "done: upload failed " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    path = getDataColumn(this, uri, null, null);
                    photo = new BmobFile(new File(path));
                    Log.d(TAG, "onActivityResult: " + path);
                    try {
                        Bitmap bitmap =
                                BitmapFactory.decodeStream(
                                        getContentResolver().openInputStream(uri)
                                );
                        mPhotoImageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.d(TAG, "onActivityResult: pick photo failed");
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
//                    Uri uri = data.getData();
                    photo = new BmobFile(new File(path));
//                    photo.setUrl(photoUri);
                    Log.d(TAG, "onActivityResult: filename " + photo.getFilename());
                    Log.d(TAG, "onActivityResult: uri " + photo.getUrl());
                    Log.d(TAG, "onActivityResult: local filename " + photo.getLocalFile().getName());
//                    photo.setUrl(uri.toString());
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(photoUri)
                        );
                        mPhotoImageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "onActivityResult: take photo failed");
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_PICK_PHOTO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhoto();
                } else {
                    Toast.makeText(this, "没有权限", Toast.LENGTH_LONG).show();
                }
                break;
            case PERMISSION_TAKE_PHOTO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Toast.makeText(this, "没有权限", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public static String getDataColumn(
            Context context,
            Uri uri,
            String selection,
            String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
