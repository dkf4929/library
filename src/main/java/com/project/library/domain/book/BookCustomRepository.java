package com.project.library.domain.book;

import java.util.List;

public interface BookCustomRepository {
    List<Book> findBooksByCategoryAndAuthorAndTitle(Long categoryId, Long authorId, String title);
}
