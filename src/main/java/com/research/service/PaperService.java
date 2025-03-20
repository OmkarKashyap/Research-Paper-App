// package com.research.service;

// import com.research.model.Paper;
// import com.research.model.Author;
// import com.research.repository.PaperRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class PaperService {
//     @Autowired
//     private PaperRepository paperRepository;

//     public Paper submitPaper(Paper paper) {
//         return paperRepository.save(paper);
//     }

//     public List<Paper> getPapersByAuthor(Author author) {
//         return paperRepository.findByAuthor(author);
//     }

//     public Paper getPaper(Integer paperId) {
//         return paperRepository.findById(paperId)
//                 .orElseThrow(() -> new RuntimeException("Paper not found"));
//     }

//     public void deletePaper(Integer paperId) {
//         paperRepository.deleteById(paperId);
//     }

//     public List<Paper> getAllPapers() {
//         return paperRepository.findAll();
//     }
// }

package com.research.service;

import com.research.model.Paper;
import com.research.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class PaperService {

    private static final String API_URL = "https://api.semanticscholar.org/graph/v1/paper/search/bulk?query=";

    private final PaperRepository paperRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public PaperService(PaperRepository paperRepository, RestTemplate restTemplate) {
        this.paperRepository = paperRepository;
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> searchPapers(String topic) {
        String url = API_URL + topic;
        
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        List<Paper> papers = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();

        if (response.getStatusCode().is2xxSuccessful()) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray data = jsonResponse.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject paperJson = data.getJSONObject(i);
                Paper paper = new Paper();
                
                paper.setId(paperJson.optString("paperId"));
                paper.setTitle(paperJson.optString("title"));
                paper.setAbstractText(paperJson.optString("abstract"));
                paper.setUrl(paperJson.optString("url"));
                paper.setVenue(paperJson.optString("venue"));
                paper.setPublicationDate(paperJson.optString("publicationDate"));
                paper.setCitationCount(paperJson.optInt("citationCount", 0));
                
                // Extract open access PDF link if available
                JSONObject openAccessPdf = paperJson.optJSONObject("openAccessPdf");
                if (openAccessPdf != null) {
                    paper.setPdfUrl(openAccessPdf.optString("url"));
                }
                
                // Extract authors
                JSONArray authorsArray = paperJson.optJSONArray("authors");
                if (authorsArray != null) {
                    List<String> authorNames = new ArrayList<>();
                    for (int j = 0; j < authorsArray.length(); j++) {
                        authorNames.add(authorsArray.getJSONObject(j).optString("name"));
                    }
                    paper.setAuthors(String.join(", ", authorNames));
                }
                
                papers.add(paper);
            }
        }
        System.out.println("Fetched Papers for topic: " + topic);
        for (Paper p : papers) {
            System.out.println(p);
        }
        paperRepository.saveAll(papers);
        result.put("data", papers);
        return result;
    }

    public Map<String, Object> getAllPapers() {
        Map<String, Object> result = new HashMap<>();
        result.put("data", paperRepository.findAll());
        return result;
    }
}
