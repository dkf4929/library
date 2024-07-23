package com.project.library.domain.book;

import com.project.library.domain.author.QAuthor;
import com.project.library.domain.bookCategory.QBookCategory;
import com.project.library.domain.category.QCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BookCustomRepositoryImpl implements BookCustomRepository {
    private final JPAQueryFactory queryFactory;
    private final QBook book;
    private final QBookCategory bookCategory;
    private final QAuthor author;
    private final QCategory category;

    public BookCustomRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.book = QBook.book;
        this.bookCategory = QBookCategory.bookCategory;
        this.author = QAuthor.author;
        this.category = QCategory.category;
    }

    @Override
    public List<Book> findBooksByCategoryAndAuthorAndTitle(Long categoryId, Long authorId, String title) {
        // null일 경우 조건 무시.
        BooleanExpression subQuery = categoryId != null ?
                JPAExpressions
                        .selectOne()
                        .from(bookCategory)
                        .where(bookCategory.book.eq(book).and(bookCategory.category.id.eq(categoryId)))
                        .exists() : null;

        return queryFactory.selectFrom(book)
                .distinct()
                .leftJoin(book.author, author).fetchJoin()
                .leftJoin(book.bookCategories, bookCategory).fetchJoin()
                .leftJoin(bookCategory.category, category).fetchJoin()
                .where(
                        eqAuthorId(authorId),
                        eqTitle(title),
                        subQuery,
                        book.available.isTrue()
                ).fetch();
    }

    private BooleanExpression eqAuthorId(Long authorId) {
        return authorId != null ? author.id.eq(authorId) : null;
    }

    private BooleanExpression eqTitle(String title) {
        return title != null && !title.isBlank() ? book.title.eq(title) : null;
    }
}