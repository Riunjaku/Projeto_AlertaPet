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
import android.widget.Toast;

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
public class PerfilFragment extends Fragment {

    final int GALLERY = 1;
    final int CAMERA =3;
    User user = new User();
    String downloadUrl;
    ImageView mImageView ;
    int PROFILE_PIC_COUNT = 0;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_usuario, container, false);



    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.get("userLogged");
        }

        mImageView = view.findViewById(R.id.imageview_default_picture);
        final EditText mNome = view.findViewById(R.id.editNome);
        final EditText mDtNascimento = view.findViewById(R.id.editDataNascimento);
        final EditText mCelular = view.findViewById(R.id.editCelular);
        final EditText mEmail = view.findViewById(R.id.editEmail);
        final Button mBtnConfirmar = view.findViewById(R.id.btnConfirmar);
        final Button mBtnEditar = view.findViewById(R.id.btnEditar);


        //inserindo os dados na tela
        if(user.imageUrl.equals(""))
        {
            Picasso.with(getContext())
                    .load(R.drawable.placeholder_imageprofile)
                    .placeholder(R.drawable.placeholder)
                    .error(R.mipmap.ic_launcher)
                    .into(mImageView);
        }
        else {
            Picasso.with(getContext())
                    .load(user.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.mipmap.ic_launcher)
                    .into(mImageView);
        }
        mNome.setText(user.name);
        mDtNascimento.setText(user.dt_nascimento);
        mCelular.setText(user.telefone);
        mEmail.setText(user.email);

        //Listeners
        mBtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNome.setEnabled(true);
                mDtNascimento.setEnabled(true);
                mCelular.setEnabled(true);
                mEmail.setEnabled(true);
                mBtnConfirmar.setVisibility(View.VISIBLE);
                mBtnEditar.setVisibility(View.GONE);

                mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selecionarImagem();
                    }
                });
            }
        });

        mBtnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNome.setEnabled(false);
                mDtNascimento.setEnabled(false);
                mCelular.setEnabled(false);
                mEmail.setEnabled(false);
                mBtnConfirmar.setVisibility(View.GONE);
                mBtnEditar.setVisibility(View.VISIBLE);

                mImageView.setOnClickListener(null);
            }
        });



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
                salvandoProfilePicture();
            }
        }
        else if(requestCode == GALLERY && resultCode == RESULT_OK)
        {
                Uri imageUri= data.getData();
                mImageView.setImageURI(imageUri);
                salvandoProfilePicture();
        }
    }

    public void salvandoProfilePicture()
    {
        //referencias
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageRef.child("users/"+user.id+"/profilePicture.jpg");

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
                }
            }
            }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Falha em conectar com o servidor",
                        Toast.LENGTH_LONG).show();
            }
        });



        mDatabase.child(user.id).child("imageUrl").setValue(downloadUrl, new DatabaseReference.CompletionListener() {
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

    public void selecionarImagem()
    {

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
}
