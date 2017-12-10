package com.aniharu.alertapet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aniharu.alertapet.Classes.User;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.aniharu.alertapet.R.string.NameNotFound;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pega o usuario
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            user = (User) bundle.get("userLogged");
            bundle.putSerializable("userLogged",user);
        }

        PerfilFragment fragment = new PerfilFragment();
        //Coloca o usuario logado no perfil
        fragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView =  navigationView.getHeaderView(0);
        TextView mTextView = hView.findViewById(R.id.username);
        CircleImageView mImageView = hView.findViewById(R.id.profile_image);

        if(!user.name.equals("") || user.name == null)
        {
            mTextView.setText(user.name);
        }
        else
        {
            mTextView.setText(NameNotFound);
        }

        if(!user.imageUrl.equals("") || user.imageUrl == null)
        {
            Picasso.with(getApplicationContext())
                    .load(user.imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        }
        else
        {
            Picasso.with(getApplicationContext())
                    .load(R.drawable.placeholder_no_image)
                    .placeholder(R.drawable.placeholder)
                    .error(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        }
    }

    //Verifica o item selecionado no menu e realiza e chama o fragmento correspondente
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // cuida do menu de navegação trocando os fragmentos na activity

        int id = item.getItemId();

        if (id == R.id.nav_perfil) {

            PerfilFragment fragment = new PerfilFragment();

            //Passando usuario
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                user = (User) bundle.get("userLogged");
                bundle.putSerializable("userLogged", user);
            }
            fragment.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_adocao) {

            MainFragment fragment = new MainFragment();

            //Passando usuario
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                user = (User) bundle.get("userLogged");
                bundle.putSerializable("userLogged", user);
            }
            fragment.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_lista_animais) {

            ListaAnimaisFragment fragment = new ListaAnimaisFragment();

            //Passando usuario
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                user = (User) bundle.get("userLogged");
                bundle.putSerializable("userLogged", user);
            }
            fragment.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_registrar_animais) {

            RegistrarAnimaisFragment fragment = new RegistrarAnimaisFragment();

            //Passando usuario
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                user = (User) bundle.get("userLogged");
                bundle.putSerializable("userLogged", user);
            }
            fragment.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_chat) {

            BatePapoFragment fragment = new BatePapoFragment();

            //Passando usuario
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                user = (User) bundle.get("userLogged");
                bundle.putSerializable("userLogged", user);
            }
            fragment.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_config) {

            ConfigFragment fragment = new ConfigFragment();

            //Passando usuario
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null) {
                user = (User) bundle.get("userLogged");
                bundle.putSerializable("userLogged", user);
            }
            fragment.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_logoff) {
            this.finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
