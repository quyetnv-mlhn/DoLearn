package com.example.dolearn.translate;


import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dolearn.HandleClass;
import com.example.dolearn.MainActivity;
import com.example.dolearn.R;
import com.example.dolearn.note.NoteActivity;
import com.example.dolearn.topic.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;

public class TranslateActivity extends AppCompatActivity {

    private TextInputEditText sourceEdit;
    private ImageView micIV, swapIV, camIV;
    private Button translateBtn;
    private TextView translatedTV, fromTV, toTV;
    private static final int REQUEST_MIC_CAPTURE = 1;
    private ImageButton addButton;
    private Bitmap imageBitmap;
    int fromLanguageCode = FirebaseTranslateLanguage.EN;
    int toLanguageCode = FirebaseTranslateLanguage.VI;
    String fromText = "eng";
    Boolean flags = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar();
        setContentView(R.layout.activity_translate);
        sourceEdit = findViewById(R.id.idEditSource);
        micIV = findViewById(R.id.idIVMic);
        swapIV = findViewById(R.id.idSwapBtn);
        camIV = findViewById(R.id.idIVCam);
        translateBtn = findViewById(R.id.idBtnTranslate);
        translatedTV = findViewById(R.id.idTVTranslatedTV);
        fromTV = findViewById(R.id.idFromTV);
        toTV = findViewById(R.id.idToTV);
        addButton = findViewById(R.id.addButton);

        // handle translate action
        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(sourceEdit.getWindowToken(), 0);
                sourceEdit.clearFocus();
                translatedTV.setText("");
                if(sourceEdit.getText().toString().isEmpty()) {
                    Toast.makeText(TranslateActivity.this, "Vui lòng nhập từ/văn bản cần dịch", Toast.LENGTH_SHORT).show();
                }else{
                    translateText(fromLanguageCode, toLanguageCode, sourceEdit.getText().toString());
                }
            }
        });

        // press mic to use speech-to-text
        micIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                isUsingMic = true;
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                if(fromLanguageCode == FirebaseTranslateLanguage.EN){
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                }else{
                    i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
                }
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói vào mic để chuyển thành từ/văn bản");
                try{
                    startActivityForResult(i, REQUEST_MIC_CAPTURE);
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(TranslateActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // press cam to image to text
        camIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()){
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(TranslateActivity.this);
                }else{
                    requestPermission();
                }
            }
        });

        // handle swap language
        swapIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapLanguage();
            }
        });

        // remove soft keyboard when not typing
        sourceEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (!b) {
                    imm.hideSoftInputFromWindow(sourceEdit.getWindowToken(), 0);
                    sourceEdit.clearFocus();
                }else{
                    sourceEdit.setSelectAllOnFocus(true);
                    if(!imm.isActive()){
                        sourceEdit.clearFocus();
                    }
                }
            }
        });

        // press enter to translate
        sourceEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
                if((actionID == EditorInfo.IME_ACTION_DONE) || ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN ))){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(sourceEdit.getWindowToken(), 0);
                    sourceEdit.clearFocus();
                    translatedTV.setText("");
                    translateText(fromLanguageCode, toLanguageCode, sourceEdit.getText().toString());
                    return true;
                }
                return false;
            }
        });

        // add word to note
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("FromText", fromText);
                    bundle.putString("engName", sourceEdit.getText().toString());
                    bundle.putString("vieName", translatedTV.getText().toString());


                    AddItemDialog addItemDialog = new AddItemDialog();
                    addItemDialog.setArguments(bundle);
                    addItemDialog.show(getSupportFragmentManager(), "add item dialog");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_MIC_CAPTURE) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                sourceEdit.setText(result.get(0));
            }
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    detextText();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void detextText(){
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        // Task<Text> result =
        recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text text) {
                for(Text.TextBlock block : text.getTextBlocks()){
                    String blockText = block.getText();
                    sourceEdit.setText(blockText);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TranslateActivity.this, "Failed to detect image from text: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void translateText(int fromLanguageCode, int toLanguageCode, String source){
        if(sourceEdit.getText().toString().isEmpty()) {
            Toast.makeText(TranslateActivity.this, "Vui lòng nhập từ/văn bản cần dịch", Toast.LENGTH_SHORT).show();
            return;
        }

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
                        flags = true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TranslateActivity.this, "Xảy ra lỗi khi dịch", Toast.LENGTH_SHORT).show();
                        flags = false;
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

        fromText = fromText.equals("eng") ? "vie" : "eng";
    }

    private boolean checkPermission(){
        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        int PERMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_CODE);
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
