package com.example.springbootrest.api;

import com.example.springbootrest.managers.LaneService;
import com.example.springbootrest.model.Lane;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/lane")
@Validated
public class LaneController {

    private final LaneService laneService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity readAllLane() {
        List<Lane> lanes = laneService.readAllLane();
        if (lanes == null) return ResponseEntity.status(400).build();
        return ResponseEntity.ok(lanes);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity readLane(@PathVariable("uuid") @NotBlank @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}") String uuid){
        Lane lane = laneService.readOneLane(UUID.fromString(uuid));
        if (lane==null) return ResponseEntity.status(400).build();
        return ResponseEntity.ok(lane);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addLane(@RequestParam("type") @NotBlank @Pattern(regexp = "vip|normal|premium") String type) {
        Lane lane = laneService.addLane(type);
        if (lane == null) return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok(lane);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateLane(@RequestParam("id") @NotBlank @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}") String uuid , @RequestParam @NotBlank @Pattern(regexp = "vip|normal|premium") String type) {
        Lane lane = laneService.updateLane(UUID.fromString(uuid), type);
        if (lane == null) return ResponseEntity.status(400).build();
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/delete/{uuid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteLane(@PathVariable("uuid") @NotBlank @Pattern(regexp = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}") String uuid ) {
        Lane lane = laneService.deleteLine(UUID.fromString(uuid));
        if (lane == null) return ResponseEntity.status(400).build();
        return ResponseEntity.ok().build();
    }

}
