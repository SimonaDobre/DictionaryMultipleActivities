package com.simona.dictionarymultipleactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditWordActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editWordED;
    Button saveAndBackToMainBtn;
    public static final String EDITED_WORD = "ew";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        initViews();
        receivedWordToBeEdited();


    }

    void receivedWordToBeEdited(){
        Intent receivedIntent = getIntent();
        String receivedWord = receivedIntent.getStringExtra(MainActivity.WORD_FOR_EDIT);
        editWordED.setText(receivedWord);
        editWordED.requestFocus();
    }

    void initViews(){
        editWordED = findViewById(R.id.editWordEditText);
        saveAndBackToMainBtn = findViewById(R.id.saveBackToMainButton);
        saveAndBackToMainBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String editedWord = editWordED.getText().toString().trim();
        Intent backToMain = new Intent(EditWordActivity.this, MainActivity.class);
        backToMain.putExtra(EDITED_WORD, editedWord);
        setResult(10, backToMain);
        finish();
    }

}