package com.example.springbootrest.model;

import java.util.Objects;
import java.util.UUID;

public class Lane {

    private UUID uuid;
    private LANE_TYPE type;

    public Lane(UUID uuid, LANE_TYPE type) {
        this.uuid = uuid;
        this.type = type;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LANE_TYPE getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Lane{" +
                "uuid=" + uuid +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lane)) return false;
        Lane lane = (Lane) o;
        return Objects.equals(getUuid(), lane.getUuid()) && getType() == lane.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getType());
    }

    public void setType(LANE_TYPE type) {
        this.type = type;
    }
}

