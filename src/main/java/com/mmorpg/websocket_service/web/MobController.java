package com.microservice.websocket_service.web;

import com.microservice.commons.MobDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/mobs")
public class MobController {

    @GetMapping
    public List<MobDTO> listMobs() {
        return List.of(new MobDTO(UUID.randomUUID(), 100));
    }
 }
