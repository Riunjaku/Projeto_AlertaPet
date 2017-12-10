package com.aniharu.alertapet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniharu.alertapet.Classes.Animal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviewAnimalFragment extends Fragment {

    String petId;
    Animal animal;
    TextView mGenero;
    TextView mEspecie;
    TextView mInfoAdicional;
    ImageView mImageView;

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

        mImageView = view.findViewById(R.id.imageview_default_picture);
        mGenero = view.findViewById(R.id.edtGenero);
        mEspecie = view.findViewById(R.id.edtEspecie);
        mInfoAdicional = view.findViewById(R.id.edtInfoAdicional);

        final CheckBox mCastradoSim = view.findViewById(R.id.castradoSim);
        final CheckBox mCastradoNao = view.findViewById(R.id.castradoNao);

        final CheckBox mVermifugadoSim = view.findViewById(R.id.vermifugadoSim);
        final CheckBox mVermifugadoNao = view.findViewById(R.id.vermifugadoNao);

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

                                if(!animal.imageUrl.equals("") || animal == null) {
                                    Picasso.with(getContext())
                                            .load(animal.imageUrl)
                                            .placeholder(R.drawable.placeholder)
                                            .error(R.mipmap.ic_launcher)
                                            .fit()
                                            .centerCrop()
                                            .into(mImageView);
                                }
                                else
                                {
                                    Picasso.with(getContext())
                                            .load(R.drawable.placeholder_no_image)
                                            .placeholder(R.drawable.placeholder)
                                            .error(R.mipmap.ic_launcher)
                                            .fit()
                                            .centerCrop()
                                            .into(mImageView);
                                }
                                mGenero.setText(animal.genero);
                                mEspecie.setText(animal.especie);
                                mInfoAdicional.setText(animal.infoAdicional);

                                if(animal.vermifugado.equals("Sim"))
                                {
                                    mVermifugadoSim.setChecked(true);
                                    mVermifugadoNao.setChecked(false);
                                }
                                else
                                {
                                    mVermifugadoSim.setChecked(false);
                                    mVermifugadoNao.setChecked(true);
                                }

                                if(animal.castrado.equals("Sim"))
                                {
                                    mCastradoSim.setChecked(true);
                                    mCastradoNao.setChecked(false);
                                }
                                else
                                {
                                    mCastradoSim.setChecked(false);
                                    mCastradoNao.setChecked(true);
                                }
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
