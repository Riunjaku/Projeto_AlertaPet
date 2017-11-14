package com.aniharu.alertapet.Classes;

/**
 * Created by riunjaku on 14/11/2017.
 */

public class Animal {

    public String infoAdicional, especie, genero;
    public boolean castrado, vermifugado;
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

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }
    public Boolean getCastrado() {
        return castrado;
    }

    public void setVermifugado(boolean vermifugado) {
        this.vermifugado = vermifugado;
    }
    public Boolean getVermifugado() {
        return vermifugado;
    }

    public Byte[] getImagem() {
        return imagem;
    }
    public void setImagem(Byte[] imagem) {
        this.imagem = imagem;
    }

}
