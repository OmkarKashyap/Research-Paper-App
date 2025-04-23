package com.research.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperService {

    private final JdbcTemplate jdbcTemplate;

    private final RestTemplate restTemplate;

    private final String supabaseUrl = "https://pnodilhotdaenfongojd.supabase.co/rest/v1";
    private final String supabaseApiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBub2RpbGhvdGRhZW5mb25nb2pkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDMzOTE0MzAsImV4cCI6MjA1ODk2NzQzMH0.t181IckYFVqjpk9IvdlkB3rLmzfaURlMA5GpgDkBI7g";

    public PaperService(JdbcTemplate jdbcTemplate) {
        this.restTemplate = new RestTemplate();
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getAllPapers() {
        Map<String, Object> result = new HashMap<>();
        try {
            String url = supabaseUrl + "/papers";

            HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", supabaseApiKey);
            headers.set("Authorization", "Bearer " + supabaseApiKey);
            headers.set("Accept", "application/json");
            headers.set("Prefer", "return=representation");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    });

            List<Map<String, Object>> papers = response.getBody();
            result.put("data", papers);
            result.put("total", papers != null ? papers.size() : 0);
        } catch (Exception e) {
            result.put("error", "An error occurred: " + e.getMessage());
        }
        return result;
    }

    public Map<String, Object> searchPapers(String topic) {
        Map<String, Object> result = new HashMap<>();
        try {
            String url = supabaseUrl + "/papers?topic=ilike.%25" + topic + "%25&select=*";

            HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", supabaseApiKey);
            headers.set("Authorization", "Bearer " + supabaseApiKey);
            headers.set("Accept", "application/json");
            headers.set("Prefer", "return=representation");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    });

            List<Map<String, Object>> papers = response.getBody();
            result.put("data", papers);
            result.put("total", papers != null ? papers.size() : 0);
        } catch (Exception e) {
            result.put("error", "An error occurred: " + e.getMessage());
        }
        return result;
    }
    public Map<String, Object> getPaperById(String paperId) {
    Map<String, Object> result = new HashMap<>();
    try {
        String url = supabaseUrl + "/papers?id=eq." + paperId + "&select=*";

        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", supabaseApiKey);
        headers.set("Authorization", "Bearer " + supabaseApiKey);
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                });

        List<Map<String, Object>> papers = response.getBody();
        if (papers != null && !papers.isEmpty()) {
             System.out.println("Fetched paper: " + papers.get(0));
            result.put("paper", papers.get(0));
        } else {
            result.put("error", "Paper not found");
        }
    } catch (Exception e) {
        result.put("error", "An error occurred: " + e.getMessage());
    }
    return result;
}
    public Map<String, Object> addComment(String paperId, String userId, String commentText) {
    Map<String, Object> result = new HashMap<>();
    try {
        String jsonComment = String.format("{\"%s\": \"%s\"}", userId, commentText);
        System.out.println(jsonComment);
        String query = "UPDATE papers SET comments = array_append(comments, ?::jsonb) WHERE id = ?";
        jdbcTemplate.update(query, jsonComment, paperId);

        result.put("success", true);
        result.put("message", "Comment added successfully");
    } catch (Exception e) {
        result.put("success", false);
        result.put("error", e.getMessage());
    }
    return result;
}


    public Map<String, Object> likePaper(String userId, String paperId) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("User ID: " + userId + ", Paper ID: " + paperId);
        try {
            // Convert userId to Long (int8 in PostgreSQL), paperId stays as String
            Long userIdLong = Long.parseLong(userId);

            // Check if the user already liked the paper
            String checkQuery = "SELECT COUNT(*) FROM user_likes WHERE user_id = ? AND paper_id = ?";
            int count = jdbcTemplate.queryForObject(checkQuery, Integer.class, userIdLong, paperId);

            if (count > 0) {
                // Unlike the paper
                String deleteQuery = "DELETE FROM user_likes WHERE user_id = ? AND paper_id = ?";
                jdbcTemplate.update(deleteQuery, userIdLong, paperId);
                result.put("message", "Paper unliked successfully");
            } else {
                // Like the paper
                String insertQuery = "INSERT INTO user_likes (user_id, paper_id) VALUES (?, ?)";
                jdbcTemplate.update(insertQuery, userIdLong, paperId);
                result.put("message", "Paper liked successfully");
            }

            result.put("success", true);
        } catch (NumberFormatException e) {
            result.put("success", false);
            result.put("error", "Invalid user ID format. Must be a number.");
        } catch (Exception e) {
            System.err.println("Error in likePaper: " + e.getMessage());
            result.put("success", false);
            result.put("error", "An error occurred: " + e.getMessage());
        }

        return result;
    }

    public Map<String, Object> getLikedPapers(int userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            String query = "SELECT p.* FROM papers p INNER JOIN user_likes ul ON p.id = ul.paper_id WHERE ul.user_id = ?";
            List<Map<String, Object>> likedPapers = jdbcTemplate.queryForList(query, userId);

            result.put("data", likedPapers);
            result.put("total", likedPapers.size());
        } catch (Exception e) {
            result.put("error", "An error occurred: " + e.getMessage());
        }
        return result;
    }
}
