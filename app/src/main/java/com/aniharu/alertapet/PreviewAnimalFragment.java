package com.aniharu.alertapet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aniharu.alertapet.Classes.Animal;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewAnimalFragment extends Fragment {


    public PreviewAnimalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        Animal animal = (Animal) args
                .getSerializable("Animal");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview_animal, container, false);
    }

}
