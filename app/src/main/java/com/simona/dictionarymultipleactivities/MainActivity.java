package com.simona.dictionarymultipleactivities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EditWordInterface {

    EditText inputWordED;
    Button addBtn, toSearchActivityBtn;
    RecyclerView wordsListRV;
    /* public static */ ArrayList<Word> arrayWords;
    Adapter myAdapter;

    public static final String WORD_FOR_EDIT = "wfe";
    int positionForEdit;

    ActivityResultLauncher<Intent> launchSearchActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 10) {
                        resultReceivedFromEdit(result);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        swipeToDelete();
    }

    void addWordToDictionary() {
        String wordToBeAdded = inputWordED.getText().toString().trim();
        arrayWords.add(new Word(wordToBeAdded));
        inputWordED.setText(null);
        myAdapter.notifyDataSetChanged();
    }

    void goToSearchActivity() {
        Intent intentToSearchActivity = new Intent(MainActivity.this, SearchActivity.class);
        intentToSearchActivity.putExtra("ORIGINAL_ARRAY", (Serializable) arrayWords);
        launchSearchActivity.launch(intentToSearchActivity);
    }

    void swipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int positionToBeRemoved = viewHolder.getAdapterPosition();
                arrayWords.remove(positionToBeRemoved);
                myAdapter.notifyDataSetChanged();
                wordsListRV.setAdapter(myAdapter);
            }
        }).attachToRecyclerView(wordsListRV);
    }

    void initViews() {
        inputWordED = findViewById(R.id.inputWordEditText);
        addBtn = findViewById(R.id.addButton);
        addBtn.setOnClickListener(this);
        toSearchActivityBtn = findViewById(R.id.toSearchButton);
        toSearchActivityBtn.setOnClickListener(this);
        wordsListRV = findViewById(R.id.wordListRV);
        arrayWords = new ArrayList<>();
        myAdapter = new Adapter(this, arrayWords, this);
        wordsListRV.setLayoutManager(new LinearLayoutManager(this));
        wordsListRV.setAdapter(myAdapter);
        wordsListRV.setHasFixedSize(true);
        wordsListRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    void resultReceivedFromEdit(ActivityResult receivedResult) {
        if (receivedResult.getResultCode() == 10) {
            Intent receivedResultFromEdit = receivedResult.getData();
            if (receivedResultFromEdit != null) {
                String editedWord = receivedResultFromEdit.getStringExtra(EditWordActivity.EDITED_WORD);
                Word editedWordObject = arrayWords.get(positionForEdit);
                editedWordObject.setWord(editedWord);
                myAdapter.notifyDataSetChanged();
                wordsListRV.setAdapter(myAdapter);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int clickedID = view.getId();
        switch (clickedID) {
            case R.id.addButton:
                addWordToDictionary();
                break;
            case R.id.toSearchButton:
                goToSearchActivity();
                break;
            default:
                return;
        }
    }

    @Override
    public void sendWordToEdit(int position) {
        Word wordToBeEdited = arrayWords.get(position);
        positionForEdit = position;
        Toast.makeText(this, "editezi cuvantul " + wordToBeEdited.getWord(), Toast.LENGTH_SHORT).show();
        Intent intentToEditActivity = new Intent(MainActivity.this, EditWordActivity.class);
        intentToEditActivity.putExtra(WORD_FOR_EDIT, wordToBeEdited.getWord());
        launchSearchActivity.launch(intentToEditActivity);
    }

}