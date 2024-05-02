package com.example.webflux1.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
