package com.aniharu.alertapet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aniharu.alertapet.Classes.Animal;
import com.aniharu.alertapet.Classes.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewAnimalFragment extends Fragment {

    String petId;
    Animal animal;
    TextView mVermifugado;
    TextView mCastrado;
    TextView mGenero;
    TextView mEspecie;
    TextView mInfoAdicional;

    public PreviewAnimalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        petId = (String) args
                .getSerializable("id");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preview_animal, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVermifugado = view.findViewById(R.id.edtVermifugado);
        mCastrado = view.findViewById(R.id.edtCastrado);
        mGenero = view.findViewById(R.id.edtGenero);
        mEspecie = view.findViewById(R.id.edtEspecie);
        mInfoAdicional = view.findViewById(R.id.edtInfoAdicional);



        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("pets")) {
                    Query query1 = mDatabase.child("pets").orderByChild("id").equalTo(petId);

                    query1.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                animal = dataSnapshot.getValue(Animal.class);

                                mVermifugado.setText(animal.vermifugado);
                                mCastrado.setText(animal.castrado);
                                mGenero.setText(animal.genero);
                                mEspecie.setText(animal.especie);
                                mInfoAdicional.setText(animal.infoAdicional);

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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

}
