package com.zsdominik.articlesbackendapp.mapper;

import com.zsdominik.articlesbackendapp.dto.NewArticleRequestDto;
import com.zsdominik.articlesbackendapp.entity.Article;
import org.springframework.stereotype.Component;

// TODO in a live application use mapping framework
@Component
public class ArticleMapper {

    public Article toArticle(NewArticleRequestDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setSummary(articleDto.getSummary());
        article.setText(articleDto.getText());
        return article;
    }
}
