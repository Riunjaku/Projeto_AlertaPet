package com.aniharu.alertapet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aniharu.alertapet.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    String email = "";
    String password = "";
    User user = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button mButton =(Button) findViewById(R.id.locker_image);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Carregando...",
                        Toast.LENGTH_LONG).show();

                //adicionar persistencia do usuario.
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                // passando o obj usuario
                intent.putExtra("userLogged", user);

                startActivity(intent);
            }
        });
    }

    protected void onStart(){
        super.onStart();
        login();
    }

    protected void registrar(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_register, null);
        final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);
        final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
        final EditText mPasswordConfirm = (EditText) mView.findViewById(R.id.etPasswordConfirm);
        Button mRegistrar = (Button) mView.findViewById(R.id.btnRegistrar);
        Button mVoltar = (Button) mView.findViewById(R.id.btnVoltar);


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        //cancela a opção de fechar o alertdialog com o botão de voltar
        dialog.setCancelable(false);
        //cancela a opção de fechar o alertdialog clicando do lado de fora dele
        dialog.setCanceledOnTouchOutside(false);

        mRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");

                final String vEmail = mEmail.getText().toString();
                final String vPassword = mPassword.getText().toString();
                final String vPasswordConfirm = mPasswordConfirm.getText().toString();

                //Verifica se os campos foram preenchidos
                if(!vEmail.isEmpty() &&  !vPassword.isEmpty() && !vPasswordConfirm.isEmpty()){

                    if(vPassword.equals(vPasswordConfirm)) {

                        if(Patterns.EMAIL_ADDRESS.matcher(vEmail).matches())
                        {
                            // Criando um novo nó de usuário, que retorna a chave única
                            // o novo nó será /users/$userid/
                            String userId = mDatabase.push().getKey();

                            // criando um objeto de usuário
                            User user = new User(userId,"", vEmail, vPassword, "", "", "");

                            // salvando o usuário no nó ‘users’ usando o id do usuário
                            mDatabase.child(userId).setValue(user, new DatabaseReference.CompletionListener() {
                                public void onComplete(DatabaseError error, DatabaseReference ref) {
                                    if (error != null) {
                                       Log.i("Erro gravando db","Data could not be saved. " + error.getMessage());
                                    } else {
                                        Log.i("sucesso","Data saved successfully. ");
                                        Toast.makeText(getApplicationContext(), "Cadastrado com sucesso",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            dialog.dismiss();
                            login();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Digite um Email valido",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Senhas não conferem",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos",
                            Toast.LENGTH_LONG).show();
                }




            }
        });
        mVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                login();
            }
        });

        dialog.show();
    }

    protected void login(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);

        View mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);
        final EditText mPassword = (EditText) mView.findViewById(R.id.etPassword);
        final TextView mRegisterButton = (TextView) mView.findViewById(R.id.register_link);
        Button mLogin = (Button) mView.findViewById(R.id.btnLogin);

        mBuilder.setView(mView);

        final AlertDialog dialog = mBuilder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String vPassword = mPassword.getText().toString();
                final String vEmail = mEmail.getText().toString();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                //Query select * from users where email = vEmail
                Query query = mDatabase.child("users").orderByChild("email").equalTo(vEmail);

                if(!vPassword.isEmpty() && !vEmail.isEmpty()) {

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Pega o resultado da query em um array

                            for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                user = messageSnapshot.getValue(User.class);
                                email = user.email;
                                password = user.password;
                            }

                            if (vEmail.equalsIgnoreCase(email) && vPassword.equals(password)) {

                                Toast.makeText(getApplicationContext(), "Conectado com sucesso",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Email ou senha incorretos",
                                        Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.i("Erro na query", "Query login erro" + databaseError.getMessage());
                        }

                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Digite todos os campos",
                            Toast.LENGTH_LONG).show();
                }
                        }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                registrar();
            }
        });
        dialog.show();
        }



}
