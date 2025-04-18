package com.research.controller;

import com.research.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/papers")
@CrossOrigin(origins = "*")
public class PaperController {

    private final PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchPapers(@RequestParam String topic) {
        return ResponseEntity.ok(paperService.searchPapers(topic));
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPapers() {
        try {
            Map<String, Object> papers = paperService.getAllPapers();
            return ResponseEntity.ok(papers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{paperId}/like")
    public ResponseEntity<Map<String, Object>> likePaper(@PathVariable String paperId,
            @RequestHeader("userId") String userId) {

        if (paperId == null || paperId.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", "Invalid paperId"));
        }

        try {
            System.out.println("Received paperId: " + paperId); // Debugging log
            System.out.println("Received userId: " + userId); // Debugging log

            Map<String, Object> response = paperService.likePaper(userId, paperId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", response.get("message")));
        } catch (Exception e) {
            System.err.println("Error in likePaper: " + e.getMessage()); // Debugging log
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    @GetMapping("/liked")
    public ResponseEntity<Map<String, Object>> getLikedPapers(@RequestHeader("userId") int userId) {
        try {
            Map<String, Object> response = paperService.getLikedPapers(userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}