package com.simats.hirebridge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetailsActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 101;
    private Uri resumeUri;

    private EditText etUsername, etEmail, etPhone, etLocation, etLanguages, etCollege, etCgpa, etDomain;
    private Spinner spinnerGender;
    private Button btnUploadResume, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etLocation = findViewById(R.id.etLocation);
        etLanguages = findViewById(R.id.etLanguages);
        etCollege = findViewById(R.id.etCollege);
        etCgpa = findViewById(R.id.etCgpa);
        etDomain = findViewById(R.id.etDomain);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnUploadResume = findViewById(R.id.btnUploadResume);
        btnNext = findViewById(R.id.btnNext);

        // ✅ Restrict phone number input: only digits, max length = 10
        etPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        etPhone.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        btnUploadResume.setOnClickListener(v -> selectFile());
        btnNext.setOnClickListener(v -> uploadDetails());
    }

    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Select Resume"), FILE_SELECT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK && data != null) {
            resumeUri = data.getData();
            Toast.makeText(this, "Resume Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadDetails() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem() != null ? spinnerGender.getSelectedItem().toString() : "";
        String languages = etLanguages.getText().toString().trim();
        String college = etCollege.getText().toString().trim();
        String cgpa = etCgpa.getText().toString().trim();
        String domain = etDomain.getText().toString().trim();

        // ✅ Phone number validation (exactly 10 digits)
        if (TextUtils.isEmpty(phone) || !phone.matches("\\d{10}")) {
            Toast.makeText(this, "Please enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        MultipartBody.Part filePart = null;
        if (resumeUri != null) {
            try {
                File file = createTempFileFromUri(this, resumeUri);
                RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
                filePart = MultipartBody.Part.createFormData("resume", file.getName(), requestFile);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to process file", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ApiResponse> call = apiService.uploadPersonalDetails(
                RequestBody.create(MediaType.parse("text/plain"), username),
                RequestBody.create(MediaType.parse("text/plain"), email),
                RequestBody.create(MediaType.parse("text/plain"), phone),
                RequestBody.create(MediaType.parse("text/plain"), location),
                RequestBody.create(MediaType.parse("text/plain"), gender),
                RequestBody.create(MediaType.parse("text/plain"), languages),
                RequestBody.create(MediaType.parse("text/plain"), college),
                RequestBody.create(MediaType.parse("text/plain"), cgpa),
                RequestBody.create(MediaType.parse("text/plain"), domain),
                filePart
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(PersonalDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    // Save in SharedPreferences
                    getSharedPreferences("HireBridgePrefs", MODE_PRIVATE)
                            .edit()
                            .putBoolean("isDetailsFilled", true)
                            .apply();

                    startActivity(new Intent(PersonalDetailsActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(PersonalDetailsActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PersonalDetailsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File createTempFileFromUri(Context context, Uri uri) throws Exception {
        InputStream inputStream = context.getContentResolver().openInputStream(uri);
        File tempFile = new File(context.getCacheDir(), "temp_resume.pdf");
        tempFile.createNewFile();
        OutputStream outputStream = new FileOutputStream(tempFile);
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return tempFile;
    }
}
