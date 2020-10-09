package com.example.mowiklanguageapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;



public class numberAdapter extends ArrayAdapter<numbers> {
    private int color;
    public numberAdapter(@NonNull Context context, ArrayList<numbers> number,int color) {
        super(context,0,number);
        this.color=color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View k=convertView;
               if(k==null){
                   k= LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);}
                numbers currentNumbers=getItem(position);

        TextView movik=(TextView)k.findViewById(R.id.mowik);
        movik.setText(currentNumbers.getMovikLanguage());
        TextView english=(TextView)k.findViewById(R.id.english);
        english.setText(currentNumbers.getEnglishLanguage());
        ImageView image=(ImageView)k.findViewById(R.id.image);
        if(currentNumbers.hasImage()){

            image.setImageResource(currentNumbers.getImageResource());
        image.setVisibility(View.VISIBLE);
        }
        else
        {

            image.setVisibility(View.GONE);
        }
        View changecolor=(View)k.findViewById(R.id.layout);
        int colors= ContextCompat.getColor(getContext(),color);
        changecolor.setBackgroundColor(colors);
        return k;
    }
}
