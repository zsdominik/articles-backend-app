package com.zsdominik.articlesbackendapp.repository;

import com.zsdominik.articlesbackendapp.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
