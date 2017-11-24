package com.aniharu.alertapet.Classes;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by riunjaku on 19/11/2017.
 */

@IgnoreExtraProperties
public class User implements Serializable {

    public String id;
    public String name;
    public String email;
    public String password;
    public String dt_nascimento;
    public String telefone;
    public String imageUrl;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String id, String name, String email, String password, String dt_nascimento, String telefone, String imageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dt_nascimento = dt_nascimento;
        this.telefone = telefone;
        this.imageUrl = imageUrl;
    }


}