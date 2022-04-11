package com.ultrafastapp.ultrafast.Models;

public class ModelViajesCerca {

    String Origen;
    String Destino;
    String idViajes;
    String peso;
    String cityorigen;
    String citydestino;
    String fechapublicado;
    String paquetesasignados;

    public ModelViajesCerca(String origen, String destino, String idViajes, String peso, String cityorigen, String citydestino, String fechapublicado, String paquetesasignados) {
        Origen = origen;
        Destino = destino;
        this.idViajes = idViajes;
        this.peso = peso;
        this.cityorigen = cityorigen;
        this.citydestino = citydestino;
        this.fechapublicado = fechapublicado;
        this.paquetesasignados = paquetesasignados;
    }


    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String origen) {
        Origen = origen;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public String getIdViajes() {
        return idViajes;
    }

    public void setIdViajes(String idViajes) {
        this.idViajes = idViajes;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getCityorigen() {
        return cityorigen;
    }

    public void setCityorigen(String cityorigen) {
        this.cityorigen = cityorigen;
    }

    public String getCitydestino() {
        return citydestino;
    }

    public void setCitydestino(String citydestino) {
        this.citydestino = citydestino;
    }

    public String getFechapublicado() {
        return fechapublicado;
    }

    public void setFechapublicado(String fechapublicado) {
        this.fechapublicado = fechapublicado;
    }

    public String getPaquetesasignados() {
        return paquetesasignados;
    }

    public void setPaquetesasignados(String paquetesasignados) {
        this.paquetesasignados = paquetesasignados;
    }
}
