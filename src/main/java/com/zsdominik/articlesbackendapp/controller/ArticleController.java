package com.zsdominik.articlesbackendapp.controller;

import com.zsdominik.articlesbackendapp.entity.Article;
import com.zsdominik.articlesbackendapp.exception.ArticleNotFoundException;
import com.zsdominik.articlesbackendapp.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/article")
    public List<Article> getAllArticle() {
        return articleService.getAllArticle();
    }

    @GetMapping(value = "/article/{articleId}")
    public Article getArticleById(@PathVariable("articleId") Long articleId) throws ArticleNotFoundException {
        return articleService.getOneArticleById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
    }

    @PostMapping(value = "/article")
    public Article createNewArticle(@RequestBody Article article) {
        return articleService.saveNewArticle(article);
    }

}
