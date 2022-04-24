package com.simona.dictionarymultipleactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchedWordED;
    TextView messageTV;
    Button searchBtn, backToMainBtn;
    ArrayList<Word> receivedArrayFromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initiews();

    }


    void initiews() {
        searchedWordED = findViewById(R.id.searchedWordEditText);
        messageTV = findViewById(R.id.messageTextView);
        searchBtn = findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(this);
        backToMainBtn = findViewById(R.id.backToMainButton);
        backToMainBtn.setOnClickListener(this);
        receivedArrayFromMain = new ArrayList<>();
    }

    void searchForWord(String w) {
        Intent receivedIntent = getIntent();
        receivedArrayFromMain = (ArrayList<Word>) receivedIntent.getSerializableExtra("ORIGINAL_ARRAY");
        boolean wordFound = false;
        for (int i = 0; i < receivedArrayFromMain.size(); i++) {
            if (w.equalsIgnoreCase(receivedArrayFromMain.get(i).getWord())) {
                wordFound = true;
                break;
            }
        }
        if (wordFound) {
            messageTV.setText("cuvantul " + w + " este in dictionar");
        } else {
            messageTV.setText("cuvantul " + w + " NU este in dictionar");
        }
    }

    void backToMainActivity() {
        Intent backToMainIntent = new Intent(SearchActivity.this, MainActivity.class);
        setResult(RESULT_OK, backToMainIntent);
        finish();
    }

    @Override
    public void onClick(View view) {
        int clickedID = view.getId();
        switch (clickedID) {
            case R.id.backToMainButton:
                backToMainActivity();
                break;
            case R.id.searchButton:
                String w = searchedWordED.getText().toString().trim();
                messageTV.setText(null);
                searchForWord(w);
                break;
            default:
                return;
        }
    }

}