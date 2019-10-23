package com.zsdominik.articlesbackendapp.service;

import com.google.common.collect.Lists;
import com.zsdominik.articlesbackendapp.dto.NewArticleRequestDto;
import com.zsdominik.articlesbackendapp.dto.UpdateArticleRequestDto;
import com.zsdominik.articlesbackendapp.entity.Article;
import com.zsdominik.articlesbackendapp.entity.Author;
import com.zsdominik.articlesbackendapp.exception.ArticleNotFoundException;
import com.zsdominik.articlesbackendapp.exception.AuthorNotFoundException;
import com.zsdominik.articlesbackendapp.exception.EmptyPropertiesException;
import com.zsdominik.articlesbackendapp.exception.MoreThanOneFieldUpdateException;
import com.zsdominik.articlesbackendapp.mapper.ArticleMapper;
import com.zsdominik.articlesbackendapp.repository.ArticleRepository;
import com.zsdominik.articlesbackendapp.repository.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, AuthorRepository authorRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
        this.articleMapper = articleMapper;
    }

    @Transactional
    public List<Article> getAllArticleOrderedByTitle() {
        return articleRepository.findAll(Sort.by("title"));
    }

    @Transactional
    public Optional<Article> getOneArticleById(Long articleId) {
        return articleRepository.findById(articleId);
    }

    @Transactional
    public Article saveNewArticle(NewArticleRequestDto articleDto) throws AuthorNotFoundException {
        Author author = authorRepository.findById(articleDto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(articleDto.getAuthorId()));
        Article newArticle = articleMapper.toArticle(articleDto);
        newArticle.setAuthor(author);
        return articleRepository.save(newArticle);
    }

    @Transactional
    public void deleteArticleById(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Transactional
    public Article updateOneFieldOfArticle(UpdateArticleRequestDto updatingArticle, Long articleId) throws ArticleNotFoundException, MoreThanOneFieldUpdateException {
        validateChangingFieldAmount(updatingArticle);
        return articleRepository.findById(articleId)
                .map(article -> updateArticleByOneField(updatingArticle, article))
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
    }

    private Article updateArticleByOneField(UpdateArticleRequestDto updatingArticle, Article article) {
        String text = updatingArticle.getText();
        String title = updatingArticle.getTitle();
        String summary = updatingArticle.getSummary();

        // TODO find more generic solution if the number of fields are increasing
        if (isNotBlank(text)) article.setText(text);
        else if (isNotBlank(title)) article.setTitle(title);
        else if (isNotBlank(summary)) article.setSummary(summary);
        else throw new EmptyPropertiesException();

        return articleRepository.save(article);
    }

    private void validateChangingFieldAmount(UpdateArticleRequestDto updatingArticle) throws MoreThanOneFieldUpdateException {

        long result = Lists.newArrayList(updatingArticle.getSummary(), updatingArticle.getTitle(), updatingArticle.getText())
                .stream()
                .filter(StringUtils::isNotBlank)
                .count();
        if (result > 1) {
            throw new MoreThanOneFieldUpdateException("Only one field is updatable at the same time!");
        }

    }


}
