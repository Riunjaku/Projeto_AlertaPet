package com.aniharu.alertapet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aniharu.alertapet.Classes.Animal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaAnimaisFragment extends Fragment {

    private ListView lstAnimal;
    private ArrayList<Animal> AnimaisList = new ArrayList<>();

    public ListaAnimaisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_animais, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lstAnimal =  view.findViewById(R.id.animalListView);

    }

    @Override
    public void onResume()
    {
        super.onResume();

        populaAnimais();
    }

    public void populaAnimais()
    {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Query query = mDatabase.child("pets");

            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    AnimaisList.add(dataSnapshot.getValue(Animal.class));
                    CustomArrayAdapter adpAnimais = new CustomArrayAdapter(getContext(),AnimaisList);
                    lstAnimal.setAdapter(adpAnimais);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}

