package com.microservice.post;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/posts")
public class PostController {

    @Autowired
    PostService service;

    @GetMapping
    public List<PostDTO> listPosts() {
        return service.create();
    }
 }
