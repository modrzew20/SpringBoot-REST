package com.example.springbootrest.repository;


import com.example.springbootrest.model.Reservation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ReservationRepository implements RepositoryInterface<Reservation> {

    private final List<Reservation> reservationList = new ArrayList<>();

    @Override
    public List<Reservation> readAll() {
        return reservationList;
    }

    @Override
    public Reservation readById(UUID uuid) {
        return reservationList.stream().filter(reservation1 -> uuid.equals(reservation1.getUuid())).findFirst().orElse(null);
    }

    @Override
    public Reservation create(Reservation object) {
        UUID uuid = UUID.randomUUID();
        while (checkIfExists(reservationList, uuid)) {
            uuid = UUID.randomUUID();
        }
        object.setUuid(uuid);
        reservationList.add(object);
        return object;
    }

    @Override
    public Reservation delete(UUID uuid) {
        LocalDateTime time = LocalDateTime.now();
        for (int i = 0; i < reservationList.size(); i++) {
            if( reservationList.get(i).getUuid().equals(uuid)
                    && ( reservationList.get(i).getEndReservation().isBefore(time)
                    ||  reservationList.get(i).getEndReservation() == null)) {
                Reservation  reservation = reservationList.get(i);
                reservationList.remove(i);
                return reservation;
            }
        }
        return null;
    }

    @Override
    public Reservation update(Reservation object) {
        return null;
    }





    public List<Reservation> pastClientReservations(UUID clientsUUID) {
        List<Reservation> list = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now();
        for (int i = 0; i < reservationList.size(); i++) {
            if(reservationList.get(i).getClient().getUuid().equals(clientsUUID)
                    && reservationList.get(i).getEndReservation().isBefore(time)) list.add(reservationList.get(i));
        }
        return list;
    }

    public List<Reservation> presentClientReservations(UUID clientsUUID) {
        List<Reservation> list = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now();
        for (int i = 0; i < reservationList.size(); i++) {
            if(reservationList.get(i).getClient().getUuid().equals(clientsUUID)
                    && (reservationList.get(i).getEndReservation().isAfter(time)
                    || reservationList.get(i).getEndReservation() == null )) list.add(reservationList.get(i));
        }
        return list;
    }


    public List<Reservation> pastLaneReservations(UUID UUIDLane) {
        List<Reservation> list = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now();
        for (int i = 0; i < reservationList.size(); i++) {
            if(reservationList.get(i).getLane().getUuid().equals(UUIDLane)
                    && reservationList.get(i).getEndReservation().isBefore(time)) list.add(reservationList.get(i));
        }
        return list;
    }

    public List<Reservation> presentLaneReservations(UUID UUIDLane) {
        List<Reservation> list = new ArrayList<>();
        LocalDateTime time = LocalDateTime.now();
        for (int i = 0; i < reservationList.size(); i++) {
            if(reservationList.get(i).getLane().getUuid().equals(UUIDLane)
                    && (reservationList.get(i).getEndReservation().isAfter(time)
                    || reservationList.get(i).getEndReservation() == null )) list.add(reservationList.get(i));
        }
        return list;
    }

    public Reservation endReservation ( UUID uuid, LocalDateTime localDateTime) {
        for (int i = 0; i < reservationList.size(); i++) {
            if(reservationList.get(i).getUuid().equals(uuid)) {
                Reservation reservation = reservationList.get(i);
                reservationList.get(i).setEndReservation(localDateTime);
                return reservation;
            }
        }
        return null;
    }


    public boolean reservedLine(UUID uuidlane, LocalDateTime start, LocalDateTime end) {
        for (int i = 0; i < reservationList.size(); i++) {
            if(reservationList.get(i).getLane().getUuid().equals(uuidlane)
                    && (reservationList.get(i).getEndReservation().isAfter(start)
                        && reservationList.get(i).getEndReservation().isBefore(end))) return true;
            if(reservationList.get(i).getLane().getUuid().equals(uuidlane)
                    && (reservationList.get(i).getStartReservation().isAfter(start)
                        && reservationList.get(i).getStartReservation().isBefore(end)
                    && reservationList.get(i).getEndReservation().isAfter(start)
                        && reservationList.get(i).getEndReservation().isBefore(end))) return true;
            if(reservationList.get(i).getLane().getUuid().equals(uuidlane)
                    && (reservationList.get(i).getStartReservation().isAfter(start)
                    && reservationList.get(i).getStartReservation().isBefore(end))) return true;

            if(reservationList.get(i).getLane().getUuid().equals(uuidlane)
                    && (reservationList.get(i).getStartReservation().isBefore(start)
                    && reservationList.get(i).getEndReservation().isAfter(end))) return true;
            if(reservationList.get(i).getLane().getUuid().equals(uuidlane)
                    && (reservationList.get(i).getStartReservation().isEqual(start)
                    && reservationList.get(i).getEndReservation().isEqual(end))) return true;
        }
        return false;
    }

    private static boolean checkIfExists(List<Reservation> list, UUID uuid) {
        return list.stream().anyMatch(reserv -> reserv.getUuid().equals(uuid));
    }
}
