package com.aniharu.alertapet.Classes;

import java.io.Serializable;

public class Animal implements Serializable {

    public String id, userId, infoAdicional, especie, genero, castrado, vermifugado, imageUrl;

    public Animal() {
    }

    public Animal(String id, String userId, String infoAdicional, String especie, String genero, String castrado, String vermifugado, String imageUrl) {
        this.id = id;
        this.userId = userId;
        this.infoAdicional = infoAdicional;
        this.especie = especie;
        this.genero = genero;
        this.castrado = castrado;
        this.vermifugado = vermifugado;
        this.imageUrl = imageUrl;
    }

    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCastrado() {
        return castrado;
    }

    public void setCastrado(String castrado) {
        this.castrado = castrado;
    }

    public String getVermifugado() {
        return vermifugado;
    }

    public void setVermifugado(String vermifugado) {
        this.vermifugado = vermifugado;
    }

    public String getimageUrl() {
        return imageUrl;
    }
    public void setimageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
