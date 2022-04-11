package com.ultrafastapp.ultrafast.Models;

public class ClienteBookin {

    String idHistoryBooking;
    String idClient;
    String idDriver;
    String destination;
    String origin;
    String time;
    String km;

    public ClienteBookin() {
    }

    public ClienteBookin(String idHistoryBooking, String idClient, String idDriver, String destination, String origin, String time, String km) {
        this.idHistoryBooking = idHistoryBooking;
        this.idClient = idClient;
        this.idDriver = idDriver;
        this.destination = destination;
        this.origin = origin;
        this.time = time;
        this.km = km;
    }

    public String getIdHistoryBooking() {
        return idHistoryBooking;
    }

    public void setIdHistoryBooking(String idHistoryBooking) {
        this.idHistoryBooking = idHistoryBooking;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String  getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }
}