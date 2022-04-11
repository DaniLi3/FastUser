package com.ultrafastapp.ultrafast.Models;

public class Vehiculos {
    String Marca;
    String Modelos;

    public Vehiculos() {
    }

    public Vehiculos(String marca, String modelos) {
        Marca = marca;
        Modelos = modelos;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelos() {
        return Modelos;
    }

    public void setModelos(String modelos) {
        Modelos = modelos;
    }
}
