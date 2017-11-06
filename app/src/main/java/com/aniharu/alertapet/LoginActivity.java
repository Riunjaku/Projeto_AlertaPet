package com.aniharu.alertapet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button mButton =(Button) findViewById(R.id.locker_image);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onStart()
    {
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
                final String vPassword = mPassword.getText().toString();
                final String vPasswordConfirm = mPasswordConfirm.getText().toString();

                if(mEmail.getText().toString().isEmpty() && mPassword.getText().toString().isEmpty() && mPasswordConfirm.getText().toString().isEmpty()){
                                finish();
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


                if(vPassword.isEmpty() && vEmail.isEmpty()){
                   dialog.dismiss();
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
