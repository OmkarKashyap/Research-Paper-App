package com.research.controller;

import com.research.service.ConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/conferences")
@CrossOrigin(origins = "*")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchConferences(@RequestParam String keyword) {
        return ResponseEntity.ok(conferenceService.searchConferences(keyword));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllConferences() {
        try {
            Map<String, Object> conferences = conferenceService.getAllConferences();
            return ResponseEntity.ok(conferences);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}