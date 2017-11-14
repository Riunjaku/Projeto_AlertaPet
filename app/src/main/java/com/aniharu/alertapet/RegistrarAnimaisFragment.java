package com.aniharu.alertapet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarAnimaisFragment extends Fragment {



    View mView;
    ImageView mImageView;
    Spinner mSpinner;
    EditText mInfoAdicional;

    public RegistrarAnimaisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar_animais, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mView = view.findViewById(R.id.fragAnimal);
        mView.onCreate(savedInstanceState);
        final ImageView mImageView = (ImageView) mView.findViewById(R.id.imageview_default_picture);
        //ver como pegar o valor do radio group
        final Spinner mSpinner = (Spinner) mView.findViewById(R.id.SpinnerEspecie);
        final EditText mInfoAdicional = (EditText) mView.findViewById(R.id.infoAdicionais);
        Button mCadastrar = (Button) mView.findViewById(R.id.btnCadastrar);
        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemselecionado = mSpinner.getSelectedItem().toString();

                Log.i("123", itemselecionado);
            }
        });
    }



}
