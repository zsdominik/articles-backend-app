package com.zsdominik.articlesbackendapp.controller;

import com.zsdominik.articlesbackendapp.dto.NewArticleRequestDto;
import com.zsdominik.articlesbackendapp.dto.UpdateArticleRequestDto;
import com.zsdominik.articlesbackendapp.entity.Article;
import com.zsdominik.articlesbackendapp.exception.ArticleNotFoundException;
import com.zsdominik.articlesbackendapp.exception.AuthorNotFoundException;
import com.zsdominik.articlesbackendapp.exception.MoreThanOneFieldUpdateException;
import com.zsdominik.articlesbackendapp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = "/articles")
    public List<Article> getAllArticle() {
        return articleService.getAllArticleOrderedByTitle();
    }

    @PostMapping(value = "/articles")
    public Article createNewArticle(@RequestBody @Valid NewArticleRequestDto newArticle) throws AuthorNotFoundException {
        return articleService.saveNewArticle(newArticle);
    }

    @GetMapping(value = "/articles/{articleId}")
    public Article getArticleById(@PathVariable("articleId") Long articleId) throws ArticleNotFoundException {
        return articleService.getOneArticleById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
    }

    @PatchMapping(value = "/articles/{articleId}")
    public Article updateArticle(@RequestBody @Valid UpdateArticleRequestDto updatingArticle, @PathVariable("articleId") Long articleId) throws ArticleNotFoundException, MoreThanOneFieldUpdateException {
        return articleService.updateOneFieldOfArticle(updatingArticle, articleId);
    }

    @DeleteMapping(value = "/articles/{articleId}")
    public void deleteArticle(@PathVariable("articleId") Long articleId) {
        articleService.deleteArticleById(articleId);
    }

}
