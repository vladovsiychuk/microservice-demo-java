package com.microservice.post;

import org.junit.jupiter.api.Test;

public class PostTest {

    @Test
    void createTest() {
        var command = new PostCommand("hello", false);
        var result = Post.create(command);

        assert result.getContent().equals("hello");
        assert !result.isPrivate();
    }

    @Test
    void updateTest() {
        var createCommand = new PostCommand("hello", false);
        var post = Post.create(createCommand);

        var updateCommand = new PostCommand("hello mod", true);
        var result = post.update(updateCommand);

        assert result.getContent().equals("hello mod");
        assert result.isPrivate();
    }
}
