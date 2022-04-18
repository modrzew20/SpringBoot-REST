package com.example.springbootrest.managers;

import com.example.springbootrest.model.LANE_TYPE;
import com.example.springbootrest.model.Lane;
import com.example.springbootrest.repository.LaneRepository;
import com.example.springbootrest.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LaneService {

    private final LaneRepository laneRepository;
    private final ReservationRepository reservationRepository;

    private Object lock = new Object();

    public List<Lane> readAllLane() {
        synchronized(lock) {
            return laneRepository.readAll();
        }
    }

    public Lane addLane(String lane_type){
        synchronized(lock) {
            return laneRepository.create(new Lane(null, LANE_TYPE.valueOf(lane_type)));
        }
    }

    public Lane updateLane(UUID uuid, String lane_type) {
        if (reservationRepository.presentLaneReservations(uuid).size() != 0) return null;
        synchronized(lock) {
            return laneRepository.update(new Lane(uuid, LANE_TYPE.valueOf(lane_type)));
        }
    }

    public Lane readOneLane(UUID uuid) {
        synchronized(lock) {
            return laneRepository.readById(uuid);
        }
    }

    public Lane deleteLine(UUID uuid) {
        if (reservationRepository.presentLaneReservations(uuid).size() != 0) return null;
        synchronized(lock) {
            return laneRepository.delete(uuid);
        }
    }
}


