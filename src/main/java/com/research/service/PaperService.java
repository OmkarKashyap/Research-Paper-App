package com.research.service;

import com.research.model.Paper;
import com.research.model.Author;
import com.research.repository.PaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperService {
    @Autowired
    private PaperRepository paperRepository;

    public Paper submitPaper(Paper paper) {
        return paperRepository.save(paper);
    }

    public List<Paper> getPapersByAuthor(Author author) {
        return paperRepository.findByAuthor(author);
    }

    public Paper getPaper(Integer paperId) {
        return paperRepository.findById(paperId)
                .orElseThrow(() -> new RuntimeException("Paper not found"));
    }

    public void deletePaper(Integer paperId) {
        paperRepository.deleteById(paperId);
    }

    public List<Paper> getAllPapers() {
        return paperRepository.findAll();
    }
}