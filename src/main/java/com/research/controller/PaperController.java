// package com.research.controller;

// import com.research.service.PaperService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import java.util.Map;
// import org.springframework.http.HttpStatus;

// @RestController
// @RequestMapping("/api/papers")
// @CrossOrigin(origins = "*")
// public class PaperController {

//     private final PaperService paperService;

//     @Autowired
//     public PaperController(PaperService paperService) {
//         this.paperService = paperService;
//     }

//     @GetMapping("/search")
//     public ResponseEntity<Map<String, Object>> searchPapers(@RequestParam String topic) {
//         return ResponseEntity.ok(paperService.searchPapers(topic));
//     }

//     @GetMapping("/all")
//     public ResponseEntity<Map<String, Object>> getAllPapers() {
//         try {
//             Map<String, Object> papers = paperService.getAllPapers();
//             return ResponseEntity.ok(papers);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(Map.of("error", e.getMessage()));
//         }
//     }
// }

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
}