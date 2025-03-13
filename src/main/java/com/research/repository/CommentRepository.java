package com.research.repository;

import com.research.model.Comment;
import com.research.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPaper(Paper paper);
}