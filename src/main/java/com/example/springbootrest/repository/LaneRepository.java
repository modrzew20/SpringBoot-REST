package com.example.springbootrest.repository;


import com.example.springbootrest.model.LANE_TYPE;
import com.example.springbootrest.model.Lane;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LaneRepository implements RepositoryInterface<Lane>{

    private final List<Lane> laneList;

    public LaneRepository() {
        this.laneList = new ArrayList<>();
    }

    @Override
    public List<Lane> readAll() {
        return laneList;
    }

    @Override
    public Lane readById(UUID uuid) {
        return laneList.stream().filter(lane1 -> uuid.equals(lane1.getUuid())).findFirst().orElse(null);
    }

    @Override
    public Lane create(Lane object) {
        if(object.getUuid()==null || checkIfExists(laneList,object.getUuid())) {
            UUID uuid = UUID.randomUUID();
            while (checkIfExists(laneList, uuid)) {
                uuid = UUID.randomUUID();
            }
            object.setUuid(uuid);
        }
        laneList.add(object);
        return object;
    }

    @Override
    public Lane delete(UUID uuid) {
        Optional<Lane> optional = laneList.stream().filter(lane -> uuid.equals(lane.getUuid())).findFirst();
        Lane lane = optional.orElse(null);
        if (lane != null) laneList.remove(lane);
        return lane;
    }

    @Override
    public Lane update(Lane object) {
        UUID uuid = object.getUuid();
        Optional<Lane> optional = laneList.stream().filter(lane -> uuid.equals(lane.getUuid())).findFirst();
        Lane lane = optional.orElse(null);
        if (lane != null) lane.setType(object.getType());
        return lane;
    }

    private static boolean checkIfExists(List<Lane> list, UUID uuid) {
        return list.stream().anyMatch(lane -> lane.getUuid().equals(uuid));
    }

}
