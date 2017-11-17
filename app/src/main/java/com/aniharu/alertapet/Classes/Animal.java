package com.aniharu.alertapet.Classes;

import java.io.Serializable;

/**
 * Created by riunjaku on 14/11/2017.
 */

public class Animal implements Serializable {

    public String infoAdicional, especie, genero, castrado, vermifugado;
    public Byte[] imagem;

    public void setInfoAdicional(String infoAdicional) {
        this.infoAdicional = infoAdicional;
    }
    public String getInfoAdicional() {
        return infoAdicional;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public String getEspecie() {
        return especie;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getGenero() {
        return genero;
    }

    public void setCastrado(String castrado) {
        this.castrado = castrado;
    }
    public String getCastrado() {
        return castrado;
    }

    public void setVermifugado(String vermifugado) {
        this.vermifugado = vermifugado;
    }
    public String getVermifugado() {
        return vermifugado;
    }

    public Byte[] getImagem() {
        return imagem;
    }
    public void setImagem(Byte[] imagem) {
        this.imagem = imagem;
    }

}
