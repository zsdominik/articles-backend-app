package com.zsdominik.articlesbackendapp.service;

import com.zsdominik.articlesbackendapp.entity.Article;
import com.zsdominik.articlesbackendapp.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    public Optional<Article> getOneArticleById(Long articleId) {
        return articleRepository.findById(articleId);
    }

    public Article saveNewArticle(Article article) {
        return articleRepository.save(article);
    }

}
