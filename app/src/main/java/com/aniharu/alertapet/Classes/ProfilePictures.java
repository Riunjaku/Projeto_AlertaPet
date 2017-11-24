package com.aniharu.alertapet.Classes;

import android.net.Uri;

/**
 * Created by riunjaku on 24/11/2017.
 */

public class ProfilePictures {

    String userId;
    Uri imagem;

    public ProfilePictures() {
    }

    public ProfilePictures(String userId, Uri imagem) {
        this.userId = userId;
        this.imagem = imagem;
    }
}
