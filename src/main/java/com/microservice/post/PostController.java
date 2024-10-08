package com.microservice.post;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    @GetMapping
    public List<PostDTO> listPosts() {
        return List.of(new PostDTO(UUID.randomUUID(), "foo"));
    }
 }
