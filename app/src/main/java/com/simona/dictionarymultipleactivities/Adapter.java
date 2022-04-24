package com.simona.dictionarymultipleactivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.WordViewHolder> {

    Context context;
    ArrayList<Word> arrayWords;
    EditWordInterface editWordInterface;

    public Adapter(Context context, ArrayList<Word> arrayWords, EditWordInterface editWordInterface) {
        this.context = context;
        this.arrayWords = arrayWords;
        this.editWordInterface = editWordInterface;
    }

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView wordTV;
        ImageView editImageView;
        EditWordInterface ewi;

        public WordViewHolder(@NonNull View itemView, EditWordInterface ewi) {
            super(itemView);
            wordTV = itemView.findViewById(R.id.wordTextView);
            editImageView = itemView.findViewById(R.id.editImageView);
            editImageView.setOnClickListener(this);
            this.ewi = ewi;
        }

        @Override
        public void onClick(View view) {
            ewi.sendWordToEdit(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.row, parent,false);
        return new WordViewHolder(v, editWordInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word currentWord = arrayWords.get(position);
        holder.wordTV.setText(currentWord.getWord());
    }

    @Override
    public int getItemCount() {
        return arrayWords.size();
    }

}
