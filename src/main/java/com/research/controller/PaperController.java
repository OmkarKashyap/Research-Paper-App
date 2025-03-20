// package com.research.controller;

// import com.research.model.Paper;
// import com.research.model.Author;
// import com.research.service.PaperService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/papers")
// public class PaperController {
//     @Autowired
//     private PaperService paperService;

//     @PostMapping
//     public ResponseEntity<Paper> submitPaper(@RequestBody Paper paper) {
//         return ResponseEntity.ok(paperService.submitPaper(paper));
//     }

//     @GetMapping("/author/{authorId}")
//     public ResponseEntity<List<Paper>> getPapersByAuthor(@PathVariable Author author) {
//         return ResponseEntity.ok(paperService.getPapersByAuthor(author));
//     }

//     @GetMapping("/{paperId}")
//     public ResponseEntity<Paper> getPaper(@PathVariable Integer paperId) {
//         return ResponseEntity.ok(paperService.getPaper(paperId));
//     }

//     @DeleteMapping("/{paperId}")
//     public ResponseEntity<Void> deletePaper(@PathVariable Integer paperId) {
//         paperService.deletePaper(paperId);
//         return ResponseEntity.ok().build();
//     }

//     @GetMapping
//     public ResponseEntity<List<Paper>> getAllPapers() {
//         return ResponseEntity.ok(paperService.getAllPapers());
//     }
// }

package com.research.controller;

import com.research.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/papers")
public class PaperController {

    private final PaperService paperService;

    @Autowired
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping("/search")
    public Map<String, Object> searchPapers(@RequestParam String topic) {
        System.out.println("Hello");
        return paperService.searchPapers(topic);
    }

    @GetMapping("/all")
    public Map<String, Object> getAllPapers() {
        return paperService.getAllPapers();
    }
}
