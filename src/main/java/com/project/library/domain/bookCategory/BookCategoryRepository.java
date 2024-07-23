package com.project.library.domain.bookCategory;

import com.project.library.domain.book.Book;
import com.project.library.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
    List<BookCategory> findByCategoryAndBook(Category category, Book book);
}
