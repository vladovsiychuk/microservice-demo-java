package com.microservice.post;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    List<PostDTO> create() {
        return List.of(new PostDTO(UUID.randomUUID(), "foo"));
    }
}
