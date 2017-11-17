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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.aniharu.alertapet.Classes.Animal;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarAnimaisFragment extends Fragment {



    String valorVermifugado;
    String valorCastrado;
    String especieSelecionado;
    String valorGenero;
    String valorInfoAdicional;
    Byte[] valorImage;

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

        final ImageView mImageView = (ImageView) view.findViewById(R.id.imageview_default_picture);

        final Spinner mSpinner = (Spinner) view.findViewById(R.id.SpinnerEspecie);

        final EditText mInfoAdicional = (EditText) view.findViewById(R.id.infoAdicionais);

        //RadioGroup vermifugado
        final RadioGroup mRadiogroup_vermifugado = (RadioGroup) view.findViewById(R.id.radiogroup_vermifugado);
        final RadioButton mRadioButtonVermifugadoS = (RadioButton) view.findViewById(R.id.radiobutton_vermifugado_sim);
        final RadioButton mRadioButtonVermifugadoN = (RadioButton) view.findViewById(R.id.radiobutton_vermifugado_nao);

        //RadioGroup castrado
        final RadioGroup mRadioGroup_castrado = (RadioGroup) view.findViewById(R.id.radiogroup_castrado);
        final RadioButton mRadioButtonCastradoS = (RadioButton) view.findViewById(R.id.radiobutton_castrado_sim);
        final RadioButton mRadioButtonCastradoN = (RadioButton) view.findViewById(R.id.radiobutton_castrado_nao);

        //RadioGroup genero
        final RadioGroup mRadioGroup_genero = (RadioGroup) view.findViewById(R.id.radiogroup_genero);
        final RadioButton mRadioButtonGeneroF = (RadioButton) view.findViewById(R.id.radiobutton_genero_fem);
        final RadioButton mRadioButtonGeneroM = (RadioButton) view.findViewById(R.id.radiobutton_genero_masc);

        Button mCadastrar = (Button) view.findViewById(R.id.btnCadastrar);
        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                especieSelecionado = mSpinner.getSelectedItem().toString();
                valorInfoAdicional = mInfoAdicional.getText().toString();
                //valorImage = mImageView.

                //salvar o resultado desse metodo no banco de dados e abrir o preview desse animal
                criarAnimal();
                previewAnimal(criarAnimal());

                Log.i("123", especieSelecionado);
            }
        });

        mRadiogroup_vermifugado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radiobutton_vermifugado_sim){
                    valorVermifugado = mRadioButtonVermifugadoS.getText().toString();
                }
                if (checkedId == R.id.radiobutton_vermifugado_nao){
                    valorVermifugado = mRadioButtonVermifugadoN.getText().toString();
                }
                Log.i("valorVermifugado", valorVermifugado);
            }
        });

        mRadioGroup_castrado.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radiobutton_castrado_sim){
                    valorCastrado = mRadioButtonCastradoS.getText().toString();
                }
                if (checkedId == R.id.radiobutton_castrado_nao){
                    valorCastrado = mRadioButtonCastradoN .getText().toString();
                }
                Log.i("valorCastrado", valorCastrado);
            }
        });

        mRadioGroup_genero.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radiobutton_genero_fem){
                    valorGenero = mRadioButtonGeneroF.getText().toString();
                }
                if (checkedId == R.id.radiobutton_genero_masc){
                    valorGenero = mRadioButtonGeneroM .getText().toString();
                }
                Log.i("valorGenero", valorGenero);
            }
        });
    }

    public Animal criarAnimal()
    {
        Animal animal = new Animal();

        animal.vermifugado = valorVermifugado;
        animal.castrado = valorCastrado;
        animal.especie = especieSelecionado;
        animal.genero = valorGenero;
        animal.infoAdicional = valorInfoAdicional;
        animal.imagem = valorImage;

        return animal;
    }

    public void previewAnimal(Animal animal)
    {
        Bundle args = new Bundle();
        args.putSerializable("Animal", animal);
        ConfigFragment fragment = new ConfigFragment();
        fragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment,"RegistrarAnimal")
                .addToBackStack("RegistrarAnimal").commit();
    }



}
