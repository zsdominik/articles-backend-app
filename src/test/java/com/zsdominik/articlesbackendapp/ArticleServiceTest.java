package com.zsdominik.articlesbackendapp;

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
import com.zsdominik.articlesbackendapp.service.ArticleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    private Article mockArticleA;
    private Article mockArticleB;
    private Article mockArticleC;
    private Author mockAuthor;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleService articleService;

    @BeforeEach
    public void init() {
        OffsetDateTime mockDate = OffsetDateTime.parse("2019-10-21T12:00-06:00");
        mockAuthor = new Author(1L, "John", "Doe");

        mockArticleB = new Article(1L, "B Article Title", "Article 1 Summary", "Article text", mockAuthor, mockDate, mockDate);
        mockArticleC = new Article(2L, "C Article Title", "Article 2 Summary", "Article text", mockAuthor, mockDate, mockDate);
        mockArticleA = new Article(3L, "A Article Title", "Article 3 Summary", "Article text", mockAuthor, mockDate, mockDate);

        Mockito.mockitoSession().initMocks(this);
        articleService = new ArticleService(articleRepository, authorRepository, articleMapper);
    }

    @Test
    public void getAllArticleOrderedByTitleTest() {
        int expectedAmountOfArticles = 3;
        List<Article> mockArticleList = new LinkedList<>();
        mockArticleList.add(mockArticleA);
        mockArticleList.add(mockArticleB);
        mockArticleList.add(mockArticleC);

        when(articleRepository.findAll(Sort.by("title"))).thenReturn(mockArticleList);

        List<Article> allArticleListOrderedByTitle = articleService.getAllArticleOrderedByTitle();

        assertNotNull(allArticleListOrderedByTitle);
        assertEquals(expectedAmountOfArticles, allArticleListOrderedByTitle.size());
    }

    @Test
    public void getArticleByIdTest() {
        Long articleId = 2L;
        when(articleRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(mockArticleC));
        Optional<Article> optionalArticleById = articleService.getOneArticleById(articleId);

        Article expectedArticle = mockArticleC;

        assertNotNull(optionalArticleById);
        assertTrue(optionalArticleById.isPresent());

        Article articleById = optionalArticleById.get();

        assertEquals(expectedArticle.getId(), articleById.getId());
    }

    @Test
    public void saveNewArticleTest() throws AuthorNotFoundException {
        NewArticleRequestDto newArticleRequestDto = new NewArticleRequestDto(1L, "text", "summary", "title");

        when(articleMapper.toArticle(any(NewArticleRequestDto.class))).thenReturn(mockArticleB);
        when(articleRepository.save(any(Article.class))).thenReturn(mockArticleB);
        when(authorRepository.findById(any(Long.class))).thenReturn(Optional.of(mockAuthor));

        Article savedArticle = articleService.saveNewArticle(newArticleRequestDto);

        assertNotNull(savedArticle);
        assertEquals(savedArticle.getId(), mockArticleB.getId());

    }

    @Test
    public void updateArticleTestWithMoreThanOnePropertyTest() {
        UpdateArticleRequestDto updateArticleRequestDto = new UpdateArticleRequestDto("text", "summary", "title");

        assertThrows(MoreThanOneFieldUpdateException.class, () ->
                articleService.updateOneFieldOfArticle(updateArticleRequestDto, 2L));
    }

    @Test
    public void updateArticleTestWithEmptyPropertyTest() throws MoreThanOneFieldUpdateException, ArticleNotFoundException {
        UpdateArticleRequestDto updateArticleRequestDto = new UpdateArticleRequestDto("", "", "");
        when(articleRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(mockArticleC));

        assertThrows(EmptyPropertiesException.class, () ->
                articleService.updateOneFieldOfArticle(updateArticleRequestDto, 2L));

    }

    @Test
    public void updateArticleTestWithNullPropertyTest() throws MoreThanOneFieldUpdateException, ArticleNotFoundException {
        UpdateArticleRequestDto updateArticleRequestDto = new UpdateArticleRequestDto(null, null, null);

        when(articleRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(mockArticleC));

        assertThrows(EmptyPropertiesException.class, () ->
                articleService.updateOneFieldOfArticle(updateArticleRequestDto, 2L));

    }

    @Test
    public void updateArticleWithTextTest() {
        UpdateArticleRequestDto updateArticleRequestDto = new UpdateArticleRequestDto("text", "", "");
        when(articleRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(mockArticleC));
        when(articleRepository.save(any(Article.class))).thenReturn(mockArticleC);

        Article updatedArticle = articleService.updateOneFieldOfArticle(updateArticleRequestDto, 2L);
        assertEquals("text", updatedArticle.getText());
    }

    @Test
    public void updateArticleWithSummaryTest() {
        UpdateArticleRequestDto updateArticleRequestDto = new UpdateArticleRequestDto("", "summary", "");
        when(articleRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(mockArticleC));
        when(articleRepository.save(any(Article.class))).thenReturn(mockArticleC);

        Article updatedArticle = articleService.updateOneFieldOfArticle(updateArticleRequestDto, 2L);
        assertEquals("summary", updatedArticle.getSummary());

    }

    @Test
    public void updateArticleWithTitleTest() {
        UpdateArticleRequestDto updateArticleRequestDto = new UpdateArticleRequestDto("", "", "title");
        when(articleRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(mockArticleC));
        when(articleRepository.save(any(Article.class))).thenReturn(mockArticleC);

        Article updatedArticle = articleService.updateOneFieldOfArticle(updateArticleRequestDto, 2L);
        assertEquals("title", updatedArticle.getTitle());
    }

}
