package com.example.springbootrest.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {

    private UUID uuid;
    private Lane lane;
    private User client;
    private LocalDateTime startReservation, endReservation;

    public Reservation(UUID uuid, Lane lane, User client, LocalDateTime startReservation, LocalDateTime endReservation) {
        this.uuid = uuid;
        this.lane = lane;
        this.client = client;
        this.startReservation = startReservation;
        this.endReservation = endReservation;
    }

    public Lane getLane() {
        return lane;
    }

    public User getClient() {
        return client;
    }

    public LocalDateTime getStartReservation() {
        return startReservation;
    }

    public LocalDateTime getEndReservation() {
        return endReservation;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setLane(Lane lane) {
        this.lane = lane;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public void setStartReservation(LocalDateTime startReservation) {
        this.startReservation = startReservation;
    }

    public void setEndReservation(LocalDateTime endReservation) {
        this.endReservation = endReservation;
    }
}
