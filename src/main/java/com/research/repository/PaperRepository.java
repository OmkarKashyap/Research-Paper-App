// package com.research.repository;

// import com.research.model.Paper;
// import com.research.model.Author;
// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

// public interface PaperRepository extends JpaRepository<Paper, Integer> {
//     List<Paper> findByAuthor(Author author);
// } 

package com.research.repository;

import com.research.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaperRepository extends JpaRepository<Paper, String> {
    Optional<Paper> findByTitleContaining(String keyword);
}