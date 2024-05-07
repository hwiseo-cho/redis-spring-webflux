package com.example.webflux1.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("posts")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post {

    @Id
    private Long id;

    @Column("user_id")
    private Long userId;

    private String title;

    private String content;

    @Transient
    private User user;

    @Column("create_at")
    @CreatedDate
    private LocalDateTime createAt;

    @Column("update_at")
    @LastModifiedDate
    private LocalDateTime updateAt;
}
