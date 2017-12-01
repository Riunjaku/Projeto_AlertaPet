package com.aniharu.alertapet;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Region;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
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

import android.content.pm.PackageManager;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    User user = new User();
    Uri downloadUrl;
    ImageView mImageView ;
    final int GALERIA_IMAGENS = 1;
    final int PERMISSAO_REQUEST = 2;
    final int TIRAR_FOTO =3;

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
        final Button mBtnFoto = (Button) view.findViewById(R.id.btnFoto);
        final Button mGaleria= (Button) view.findViewById(R.id.btnFotoGaleria);


        mImageView = (ImageView)view.findViewById(R.id.imageview_default_picture);
        Button galeria= (Button) view.findViewById(R.id.btnFotoGaleria);
        mGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALERIA_IMAGENS);
            }
        });

        Button foto = (Button) view.findViewById(R.id.btnFoto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager())!= null){
                    startActivityForResult(takePictureIntent, TIRAR_FOTO);
                }
            }
        });

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
                mBtnFoto.setVisibility(View.VISIBLE);
                mGaleria.setVisibility(View.VISIBLE);
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
                mBtnFoto.setVisibility(View.GONE);
                mGaleria.setVisibility(View.GONE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK && requestCode== GALERIA_IMAGENS) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap imagemGaleria = (BitmapFactory.decodeFile(picturePath));
            mImageView.setImageBitmap(imagemGaleria);
        }
    }

}
