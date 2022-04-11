    package com.ultrafastapp.ultrafast.Models;

public class SolicitudesModel {

    String fechadesalida;
    String fechadesolicitud;
    String fechadepublicacionviaje;
     public String origen;
    public String destino;
    public String idcliente;
    public String idchofer;
    public String idnoty;
    public String status;
    public String precio;
    public String costopaquete;
    public String fechavalidar;
    public String horasalida;
    public String seo;
    public String seocliente;
    public String kilos;
    public double origenlat;
    public double origenlog;
    public double destinolatt;
    public double destinolog;

    public double getOrigenlat() {
        return origenlat;
    }

    public void setOrigenlat(double origenlat) {
        this.origenlat = origenlat;
    }

    public double getOrigenlog() {
        return origenlog;
    }

    public void setOrigenlog(double origenlog) {
        this.origenlog = origenlog;
    }

    public double getDestinolatt() {
        return destinolatt;
    }

    public void setDestinolatt(double destinolatt) {
        this.destinolatt = destinolatt;
    }

    public double getDestinolog() {
        return destinolog;
    }

    public void setDestinolog(double destinolog) {
        this.destinolog = destinolog;
    }

    public long timestamp;
    String image;
    public String personaquerecibe;
    public String notapaquete;
    public String contenidopaquete;
    public String dimensiones;
    public String telpersona;
    public String estado;
    public String idviaje;

    public SolicitudesModel(String fechadesalida, String horasalida, String fechadesolicitud, String fechadepublicacionviaje, String origen, String destino, String idcliente, String idchofer, String idnoty, String status, String precio, String costopaquete, String fechavalidar, String seo, String seocliente, String kilos, double origenlat, double origenlog, double destinolat, double destinolog, long timestamp, String image, String personaquerecibe, String notapaquete, String contenidopaquete, String dimensiones, String telpersona, String estado, String idviaje) {
        this.fechadesalida = fechadesalida;
        this.fechadesolicitud = fechadesolicitud;
        this.fechadepublicacionviaje = fechadepublicacionviaje;
        this.origen = origen;
        this.destino = destino;
        this.idcliente = idcliente;
        this.idchofer = idchofer;
        this.idnoty = idnoty;
        this.status = status;
        this.precio = precio;
        this.costopaquete = costopaquete;
        this.fechavalidar = fechavalidar;
        this.horasalida = horasalida;
        this.seo = seo;
        this.seocliente = seocliente;
        this.kilos = kilos;
        this.origenlat = origenlat;
        this.origenlog = origenlog;
        this.destinolatt = destinolat;
        this.destinolog = destinolog;
        this.timestamp = timestamp;
        this.image = image;
        this.personaquerecibe = personaquerecibe;
        this.notapaquete = notapaquete;
        this.contenidopaquete = contenidopaquete;
        this.dimensiones = dimensiones;
        this.telpersona = telpersona;
        this.estado = estado;
        this.idviaje = idviaje;
    }

    public String getIdviaje() {
        return idviaje;
    }

    public void setIdviaje(String idviaje) {
        this.idviaje = idviaje;
    }

    public String getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(String horasalida) {
        this.horasalida = horasalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public SolicitudesModel() {
    }





    public String getCostopaquete() {
        return costopaquete;
    }

    public void setCostopaquete(String costopaquete) {
        this.costopaquete = costopaquete;
    }

    public String getFechavalidar() {
        return fechavalidar;
    }

    public void setFechavalidar(String fechavalidar) {
        this.fechavalidar = fechavalidar;
    }

    public String getTelpersona() {
        return telpersona;
    }

    public void setTelpersona(String telpersona) {
        this.telpersona = telpersona;
    }

    public String getSeocliente() {
        return seocliente;
    }

    public void setSeocliente(String seocliente) {
        this.seocliente = seocliente;
    }

    public String getKilos() {
        return kilos;
    }

    public void setKilos(String kilos) {
        this.kilos = kilos;
    }

    public String getContenidopaquete() {
        return contenidopaquete;
    }

    public void setContenidopaquete(String contenidopaquete) {
        this.contenidopaquete = contenidopaquete;
    }

    public String getPersonaquerecibe() {
        return personaquerecibe;
    }

    public void setPersonaquerecibe(String personaquerecibe) {
        this.personaquerecibe = personaquerecibe;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public String getNotapaquete() {
        return notapaquete;
    }

    public void setNotapaquete(String notapaquete) {
        this.notapaquete = notapaquete;
    }

    public String getFechadesalida() {
        return fechadesalida;
    }

    public void setFechadesalida(String fechadesalida) {
        this.fechadesalida = fechadesalida;
    }

    public String getFechadesolicitud() {
        return fechadesolicitud;
    }

    public void setFechadesolicitud(String fechadesolicitud) {
        this.fechadesolicitud = fechadesolicitud;
    }

    public String getFechadepublicacionviaje() {
        return fechadepublicacionviaje;
    }

    public void setFechadepublicacionviaje(String fechadepublicacionviaje) {
        this.fechadepublicacionviaje = fechadepublicacionviaje;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getIdchofer() {
        return idchofer;
    }

    public void setIdchofer(String idchofer) {
        this.idchofer = idchofer;
    }

    public String getIdnoty() {
        return idnoty;
    }

    public void setIdnoty(String idnoty) {
        this.idnoty = idnoty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }



    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

