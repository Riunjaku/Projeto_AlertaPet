package com.aniharu.alertapet;


import android.graphics.Bitmap;
import android.graphics.Region;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
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

import com.aniharu.alertapet.Classes.ProfilePictures;
import com.aniharu.alertapet.Classes.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    User user = new User();
    Uri downloadUrl;
    ImageView mImageView ;

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

        mImageView = (ImageView)view.findViewById(R.id.imageview_default_picture);
        final EditText mNome = (EditText) view.findViewById(R.id.editNome);
        final EditText mDtNascimento = (EditText) view.findViewById(R.id.editDataNascimento);
        final EditText mCelular = (EditText) view.findViewById(R.id.editCelular);
        final EditText mEmail = (EditText) view.findViewById(R.id.editEmail);
        final Button mBtnConfirmar = (Button) view.findViewById(R.id.btnConfirmar);
        final Button mBtnEditar = (Button) view.findViewById(R.id.btnEditar);

        //inserindo os dados na tela
        // mImageView.setImageURI(Uri.parse(user.imageUrl));
        mNome.setText(user.name);
        mDtNascimento.setText(user.dt_nascimento);
        mCelular.setText(user.telefone);
        mEmail.setText(user.email);

        //Listeners
        mBtnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mImageView.setClickable(true);
                mNome.setEnabled(true);
                mDtNascimento.setEnabled(true);
                mCelular.setEnabled(true);
                mEmail.setEnabled(true);
                mBtnConfirmar.setVisibility(View.VISIBLE);
                mBtnEditar.setVisibility(View.GONE);

            }
        });

        mBtnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mImageView.setClickable(false);
                mNome.setEnabled(false);
                mDtNascimento.setEnabled(false);
                mCelular.setEnabled(false);
                mEmail.setEnabled(false);
                mBtnConfirmar.setVisibility(View.GONE);
                mBtnEditar.setVisibility(View.VISIBLE);

                //salvar imagem(Eu acho tem que testar)
               // salvandoProfilePicture();

            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }

    public void salvandoProfilePicture()
    {
        //referencias
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("profilePictures");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference profilePictureRef = storageRef.child("profilePicture.jpg");
        StorageReference profileRef = storageRef.child("profile/profilePicture.jpg");
        profilePictureRef.getName().equals(profileRef.getName());    // true
        profilePictureRef.getPath().equals(profileRef.getPath());    // false

        //pegando a imagem
        mImageView.setDrawingCacheEnabled(true);
        mImageView.buildDrawingCache();
        Bitmap bitmap = mImageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("Erro gravando db","Data could not be saved. " + exception.getMessage());
                Toast.makeText(getContext(), "Falha em conectar com o servidor",
                        Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });


        String profilePicturesId = mDatabase.push().getKey();

        ProfilePictures profilePictures = new ProfilePictures(user.id, downloadUrl);
        mDatabase.child(profilePicturesId).setValue(profilePictures, new DatabaseReference.CompletionListener() {
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
