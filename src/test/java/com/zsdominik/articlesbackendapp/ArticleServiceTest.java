package com.zsdominik.articlesbackendapp;

import com.zsdominik.articlesbackendapp.entity.Article;
import com.zsdominik.articlesbackendapp.entity.Author;
import com.zsdominik.articlesbackendapp.repository.ArticleRepository;
import com.zsdominik.articlesbackendapp.service.ArticleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

    private Article mockArticle1;
    private Article mockArticle2;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    @Before
    public void init() {
        OffsetDateTime mockDate = OffsetDateTime.parse("2019-10-21T12:00-06:00");
        Author mockAuthor = new Author(1L, "John", "Doe");

        mockArticle1 = new Article(1L, "Article 1 Title", "Article 1 Summary", "Article text", mockAuthor, mockDate, mockDate);
        mockArticle2 = new Article(2L, "Article 2 Title", "Article 2 Summary", "Article text", mockAuthor, mockDate, mockDate);
        Article mockArticle3 = new Article(3L, "Article 3 Title", "Article 3 Summary", "Article text", mockAuthor, mockDate, mockDate);

        List<Article> mockArticleList = new LinkedList<>();
        mockArticleList.add(mockArticle1);
        mockArticleList.add(mockArticle2);
        mockArticleList.add(mockArticle3);

        Mockito.when(articleRepository.findById(2L)).thenReturn(java.util.Optional.of(mockArticle2));
        Mockito.when(articleRepository.findAll()).thenReturn(mockArticleList);
        Mockito.when(articleRepository.save(mockArticle1)).thenReturn(mockArticle1);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllArticleTest() {
        int expectedAmountOfArticles = 3;
        List<Article> allArticleList = articleService.getAllArticle();

        assertNotNull(allArticleList);
        assertEquals(expectedAmountOfArticles, allArticleList.size());
    }

    @Test
    public void getArticleByIdTest() {
        Long articleId = 2L;
        Optional<Article> optionalArticleById = articleService.getOneArticleById(articleId);

        Article expectedArticle = mockArticle2;

        assertNotNull(optionalArticleById);
        assertTrue(optionalArticleById.isPresent());

        Article articleById = optionalArticleById.get();

        assertEquals(expectedArticle.getId(), articleById.getId());
        assertEquals(expectedArticle.getAuthor().getId(), articleById.getAuthor().getId());
        assertEquals(expectedArticle.getAuthor().getFirstName(), articleById.getAuthor().getFirstName());
        assertEquals(expectedArticle.getAuthor().getLastName(), articleById.getAuthor().getLastName());
        assertEquals(expectedArticle.getDateCreated(), articleById.getDateCreated());
        assertEquals(expectedArticle.getDateUpdated(), articleById.getDateUpdated());
        assertEquals(expectedArticle.getSummary(), articleById.getSummary());
        assertEquals(expectedArticle.getTitle(), articleById.getTitle());
        assertEquals(expectedArticle.getText(), articleById.getText());
    }

    @Test
    public void saveNewArticleTest() {
        Article savedArticle = articleService.saveNewArticle(mockArticle1);

        assertNotNull(savedArticle);
        assertEquals(savedArticle.getText(), mockArticle1.getText());
        assertEquals(savedArticle.getId(), mockArticle1.getId());
        assertEquals(savedArticle.getTitle(), mockArticle1.getTitle());
        assertEquals(savedArticle.getDateUpdated(), mockArticle1.getDateUpdated());
        assertEquals(savedArticle.getDateCreated(), mockArticle1.getDateCreated());
        assertEquals(savedArticle.getSummary(), mockArticle1.getSummary());
        assertEquals(savedArticle.getAuthor().getId(), mockArticle1.getAuthor().getId());
        assertEquals(savedArticle.getAuthor().getLastName(), mockArticle1.getAuthor().getLastName());
        assertEquals(savedArticle.getAuthor().getFirstName(), mockArticle1.getAuthor().getFirstName());

    }

}
