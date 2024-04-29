package com.example.redis.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SessionController {

    @GetMapping("/")
    public Map<String, String> home(HttpSession session) {
        Integer visitCount = (Integer) session.getAttribute("visits");

        if(visitCount == null) {
            visitCount = 0;
        }

        session.setAttribute("visits", ++ visitCount);
        return Map.of("sessionId", session.getId(), "visits", String.valueOf(visitCount));
    }
}
