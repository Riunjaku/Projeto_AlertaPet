package com.aniharu.alertapet;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.aniharu.alertapet.Classes.Animal;
import com.aniharu.alertapet.Classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrarAnimaisFragment extends Fragment {



    final int GALLERY = 1;
    final int CAMERA =3;
    String valorVermifugado;
    String valorCastrado;
    String especieSelecionado;
    String valorGenero;
    String valorInfoAdicional;
    int PROFILE_PIC_COUNT = 0;
    ImageView mImageView;
    String downloadUrl = "";
    Animal animal;
    User user;

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.get("userLogged");
        }

        mImageView = view.findViewById(R.id.imageview_default_picture);
        final Spinner mSpinner = view.findViewById(R.id.SpinnerEspecie);
        final EditText mInfoAdicional = view.findViewById(R.id.infoAdicionais);

        //RadioGroup vermifugado
        final RadioGroup mRadiogroup_vermifugado = view.findViewById(R.id.radiogroup_vermifugado);
        final RadioButton mRadioButtonVermifugadoS = view.findViewById(R.id.radiobutton_vermifugado_sim);
        final RadioButton mRadioButtonVermifugadoN = view.findViewById(R.id.radiobutton_vermifugado_nao);

        //RadioGroup castrado
        final RadioGroup mRadioGroup_castrado = view.findViewById(R.id.radiogroup_castrado);
        final RadioButton mRadioButtonCastradoS = view.findViewById(R.id.radiobutton_castrado_sim);
        final RadioButton mRadioButtonCastradoN = view.findViewById(R.id.radiobutton_castrado_nao);

        //RadioGroup genero
        final RadioGroup mRadioGroup_genero = view.findViewById(R.id.radiogroup_genero);
        final RadioButton mRadioButtonGeneroF = view.findViewById(R.id.radiobutton_genero_fem);
        final RadioButton mRadioButtonGeneroM = view.findViewById(R.id.radiobutton_genero_masc);

        Picasso.with(getContext())
                .load(R.drawable.placeholder_image)
                .placeholder(R.drawable.placeholder)
                .error(R.mipmap.ic_launcher)
                .fit()
                .into(mImageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Tirar foto", "Escolher da galeria", "Cancelar"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Adicionar foto!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (items[item].equals("Tirar foto")) {
                            PROFILE_PIC_COUNT = 1;
                            Intent takePictureIntent = new
                                    Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getActivity().getPackageManager())!= null){
                                startActivityForResult(takePictureIntent, CAMERA);
                            }
                        } else if (items[item].equals("Escolher da galeria")) {
                            PROFILE_PIC_COUNT = 1;
                            Intent galleryPictureIntent = new
                                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            galleryPictureIntent.setType("image/*");
                            galleryPictureIntent.setAction(Intent.ACTION_GET_CONTENT);
                            if(galleryPictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivityForResult(galleryPictureIntent, GALLERY);
                            }
                        } else if (items[item].equals("Cancelar")) {
                            PROFILE_PIC_COUNT = 0;
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        Button mCadastrar = view.findViewById(R.id.btnCadastrar);
        mCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                especieSelecionado = mSpinner.getSelectedItem().toString();
                valorInfoAdicional = mInfoAdicional.getText().toString();

                if(valorVermifugado == null || valorCastrado == null || valorGenero == null || valorInfoAdicional.equals("")) {
                    Toast.makeText(getContext(), "Preencha todos os campos",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                        //salvar o resultado desse metodo no banco de dados e abrir o preview desse animal
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("pets");
                        final String petId = mDatabase.push().getKey();

                        // criando um objeto de animal para salvar no banco
                        animal = new Animal(petId,user.id,valorInfoAdicional, especieSelecionado, valorGenero, valorCastrado, valorVermifugado, downloadUrl);
                        // salvando o animal no nó ‘pets’ usando o id do usuário
                        mDatabase.child(petId).setValue(animal, new DatabaseReference.CompletionListener() {
                            public void onComplete(DatabaseError error, DatabaseReference ref) {
                                if (error != null) {
                                    Log.i("Erro gravando db","Data could not be saved. " + error.getMessage());
                                    Toast.makeText(getContext(),
                                            "Um erro inesperado ocorreu, por favor tente novamente, ou entre em contato com o suporte",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Log.i("sucesso","Data saved successfully. ");
                                    Toast.makeText(getContext(), "Cadastrado com sucesso",
                                            Toast.LENGTH_LONG).show();
                                    salvandoProfilePicture();
                                    previewAnimal(petId);

                                }
                            }
                        });
                }
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
            }
        });
    }

    public void previewAnimal(String petId)
    {
        Bundle args = new Bundle();
        args.putSerializable("id", petId);
        PreviewAnimalFragment fragment = new PreviewAnimalFragment();
        fragment.setArguments(args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment,"RegistrarAnimal")
                .addToBackStack("RegistrarAnimal").commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap;
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
                mImageView.setImageBitmap(imageBitmap);
            }
        }
        else if(requestCode == GALLERY && resultCode == RESULT_OK)
        {
            Uri imageUri= data.getData();
            mImageView.setImageURI(imageUri);
        }
    }

    public void salvandoProfilePicture()
    {
        //referencias
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("pets");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageRef.child("pets/"+animal.id+"/petPicture.jpg");

        //pegando a imagem
        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache();
        Bitmap bitmap = mImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileRef.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getDownloadUrl() != null) {
                    downloadUrl = taskSnapshot.getDownloadUrl().toString();
                    if(!downloadUrl.equals(""))
                    {
                        mDatabase.child(animal.id).child("imageUrl").setValue(downloadUrl, new DatabaseReference.CompletionListener() {
                            public void onComplete(DatabaseError error, DatabaseReference ref) {
                                if (error != null) {
                                    Log.i("Erro gravando db","Data could not be saved. " + error.getMessage());
                                } else {
                                    Log.i("sucesso","Data saved successfully. ");
                                    Toast.makeText(getContext(), "Cadastrado com sucesso",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Falha em conectar com o servidor",
                        Toast.LENGTH_LONG).show();
            }
        });




    }

}
