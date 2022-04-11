package com.ultrafastapp.ultrafast.Models;

public class ViajesPublicados {
    String Origen;
    String OrigenLtnLong;
    String OrigenLat;
    String OrigenLog;
    String DestinoLat;
    String DestinoLog;
    String Destino;
    String DestinoLtnLong;
    String Fecha;
    String Hora;
    String IdUser;
    String idViajes;
    String precio;
    String fechaactual;
    String horaactual;
    String peso;
    String comentario;
    String dimensiones;
    String cityOrigen;
    String cityDestino;
    String seo;
    String fechavali;
    String cantidadentregas;
    String paquetesasignados;
    String estado;
    String status;
    Long Timestamp;

    public ViajesPublicados(String origen, String origenLtnLong, String origenLat, String origenLog, String destinoLat, String destinoLog, String destino, String destinoLtnLong, String fecha, String hora, String idUser, String idViajes, String precio, String fechaactual, String horaactual, String peso, String comentario, String dimensiones, String cityOrigen, String cityDestino, String seo, String cantidadentregas, String paquetesasignados, Long timestamp,String fechavali,String estado,String status) {
        Origen = origen;
        OrigenLtnLong = origenLtnLong;
        OrigenLat = origenLat;
        OrigenLog = origenLog;
        DestinoLat = destinoLat;
        DestinoLog = destinoLog;
        Destino = destino;
        DestinoLtnLong = destinoLtnLong;
        Fecha = fecha;
        Hora = hora;
        IdUser = idUser;
        this.idViajes = idViajes;
        this.precio = precio;
        this.fechaactual = fechaactual;
        this.horaactual = horaactual;
        this.peso = peso;
        this.comentario = comentario;
        this.dimensiones = dimensiones;
        this.cityOrigen = cityOrigen;
        this.cityDestino = cityDestino;
        this.seo = seo;
        this.cantidadentregas = cantidadentregas;
        this.paquetesasignados = paquetesasignados;
        this.estado = estado;
        Timestamp = timestamp;
        fechavali = fechavali;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechavali() {
        return fechavali;
    }

    public void setFechavali(String fechavali) {
        this.fechavali = fechavali;
    }

    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }

    public String getPaquetesasignados() {
        return paquetesasignados;
    }

    public void setPaquetesasignados(String paquetesasignados) {
        this.paquetesasignados = paquetesasignados;
    }

    public String getCantidadentregas() {
        return cantidadentregas;
    }

    public void setCantidadentregas(String cantidadentregas) {
        this.cantidadentregas = cantidadentregas;
    }

    public ViajesPublicados() {
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public String getCityOrigen() {
        return cityOrigen;
    }

    public void setCityOrigen(String cityOrigen) {
        this.cityOrigen = cityOrigen;
    }

    public String getCityDestino() {
        return cityDestino;
    }

    public void setCityDestino(String cityDestino) {
        this.cityDestino = cityDestino;
    }

    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String origen) {
        Origen = origen;
    }

    public String getOrigenLtnLong() {
        return OrigenLtnLong;
    }

    public void setOrigenLtnLong(String origenLtnLong) {
        OrigenLtnLong = origenLtnLong;
    }

    public String getOrigenLat() {
        return OrigenLat;
    }

    public void setOrigenLat(String origenLat) {
        OrigenLat = origenLat;
    }

    public String getOrigenLog() {
        return OrigenLog;
    }

    public void setOrigenLog(String origenLog) {
        OrigenLog = origenLog;
    }

    public String getDestinoLat() {
        return DestinoLat;
    }

    public void setDestinoLat(String destinoLat) {
        DestinoLat = destinoLat;
    }

    public String getDestinoLog() {
        return DestinoLog;
    }

    public void setDestinoLog(String destinoLog) {
        DestinoLog = destinoLog;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public String getDestinoLtnLong() {
        return DestinoLtnLong;
    }

    public void setDestinoLtnLong(String destinoLtnLong) {
        DestinoLtnLong = destinoLtnLong;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getIdViajes() {
        return idViajes;
    }

    public void setIdViajes(String idViajes) {
        this.idViajes = idViajes;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFechaactual() {
        return fechaactual;
    }

    public void setFechaactual(String fechaactual) {
        this.fechaactual = fechaactual;
    }

    public String getHoraactual() {
        return horaactual;
    }

    public void setHoraactual(String horaactual) {
        this.horaactual = horaactual;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }
}

