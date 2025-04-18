package com.research.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConferenceService {

    private final RestTemplate restTemplate;

    private final String supabaseUrl = "https://pnodilhotdaenfongojd.supabase.co/rest/v1";
    private final String supabaseApiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBub2RpbGhvdGRhZW5mb25nb2pkIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDMzOTE0MzAsImV4cCI6MjA1ODk2NzQzMH0.t181IckYFVqjpk9IvdlkB3rLmzfaURlMA5GpgDkBI7g";

    public ConferenceService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> getAllConferences() {
        Map<String, Object> result = new HashMap<>();
        try {
            String url = supabaseUrl + "/conferences";

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

            List<Map<String, Object>> conferences = response.getBody();
            result.put("data", conferences);
            result.put("total", conferences != null ? conferences.size() : 0);
        } catch (Exception e) {
            result.put("error", "An error occurred: " + e.getMessage());
        }
        return result;
    }

    public Map<String, Object> searchConferences(String keyword) {
        Map<String, Object> result = new HashMap<>();
        try {
            String url = supabaseUrl + "/conferences?title=ilike.%25" + keyword + "%25&select=*";

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

            List<Map<String, Object>> conferences = response.getBody();
            result.put("data", conferences);
            result.put("total", conferences != null ? conferences.size() : 0);
        } catch (Exception e) {
            result.put("error", "An error occurred: " + e.getMessage());
        }
        return result;
    }
}