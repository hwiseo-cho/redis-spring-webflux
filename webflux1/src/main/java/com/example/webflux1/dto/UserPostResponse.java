package com.example.webflux1.dto;

import com.example.webflux1.repository.Post;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserPostResponse {

    private Long id;
    private String userName;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static UserPostResponse of(Post post) {
        return UserPostResponse.builder()
                .id(post.getId())
                .userName("TODO")
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .updateAt(post.getUpdateAt())
                .build();
    }
}
