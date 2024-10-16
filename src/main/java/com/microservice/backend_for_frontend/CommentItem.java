package com.microservice.backend_for_frontend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservice.comment.Comment;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentItem {
    @Id
    private UUID id;
    private String content;

    @Transient
    @JsonIgnore
    private boolean isNew;

    public static Comment create() {
        return null;
    }
}
