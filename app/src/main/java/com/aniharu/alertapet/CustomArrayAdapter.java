package com.aniharu.alertapet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.aniharu.alertapet.Classes.Animal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class CustomArrayAdapter extends ArrayAdapter<Animal> {

    //Construtor da classe adapter, tudo que tiver que passar por parametro tem que passar aqui
    CustomArrayAdapter(Context context, ArrayList<Animal> animals) {
        super(context, 0, animals);
    }

    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {

        //pega o item do arraylist e coloca no objeto
        Animal animal = getItem(position);



        if (convertView == null) {
            //infla a view com o layout que vc criou
           convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_item_list, parent, false);
        }

        ImageView mImageView =  convertView.findViewById(R.id.petPicture);
        if(animal != null) {
            if (animal.imageUrl.equals("")) {
                Picasso.with(getContext())
                        .load(R.drawable.placeholder_no_image)
                        .placeholder(R.drawable.placeholder)
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(mImageView);
            } else {
                Picasso.with(getContext())
                        .load(animal.imageUrl)
                        .placeholder(R.drawable.placeholder)
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .centerCrop()
                        .into(mImageView);
            }
        }
        //Retorna a View
        return convertView;
    }
}