package com.example.dolearn.translate;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolearn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.google.logging.type.HttpRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

public class TranslateActivity extends AppCompatActivity {

    private TextInputEditText sourceEdit;
    private ImageView micIV, swapIV;
    private MaterialButton translateBtn;
    private TextView translatedTV, fromTV, toTV;
    private static final int REQUEST_PERMISSION_CODE = 1;
    int fromLanguageCode = FirebaseTranslateLanguage.EN;
    int toLanguageCode = FirebaseTranslateLanguage.VI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_translate);
        sourceEdit = findViewById(R.id.idEditSource);
        micIV = findViewById(R.id.idIVMic);
        swapIV = findViewById(R.id.idSwapBtn);
        translateBtn = findViewById(R.id.idBtnTranslate);
        translatedTV = findViewById(R.id.idTVTranslatedTV);
        fromTV = findViewById(R.id.idFromTV);
        toTV = findViewById(R.id.idToTV);

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translatedTV.setText("");
                if(sourceEdit.getText().toString().isEmpty()) {
                    Toast.makeText(TranslateActivity.this, "Vui lòng nhập từ/văn bản cần dịch", Toast.LENGTH_SHORT).show();
                }else{
                    translateText(fromLanguageCode, toLanguageCode, sourceEdit.getText().toString());
                }
            }
        });

        micIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                if(fromLanguageCode == FirebaseTranslateLanguage.EN){
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                }else{
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
                }
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói vào mic để chuyển thành từ/văn bản");
                try{
                    startActivityForResult(i, REQUEST_PERMISSION_CODE);
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(TranslateActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        swapIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapLanguage();
            }
        });

        sourceEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(sourceEdit.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_PERMISSION_CODE){
            if(resultCode == RESULT_OK && data != null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                sourceEdit.setText(result.get(0));
            }
        }
    }

    private void translateText(int fromLanguageCode, int toLanguageCode, String source){
        translatedTV.setText("Đang tải dữ liệu...");
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(toLanguageCode)
                .build();

        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();

        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translatedTV.setText("Đang dịch...");
                translator.translate(source).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        translatedTV.setText(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TranslateActivity.this, "Xảy ra lỗi khi dịch", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TranslateActivity.this, "Xảy ra lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void swapLanguage(){
        String tempLanguage = fromTV.getText().toString();
        fromTV.setText(toTV.getText().toString());
        toTV.setText(tempLanguage);

        int tempLanguageCode = fromLanguageCode;
        fromLanguageCode = toLanguageCode;
        toLanguageCode = tempLanguageCode;
    }

    public void actionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Translate");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Handle click backIcon
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
