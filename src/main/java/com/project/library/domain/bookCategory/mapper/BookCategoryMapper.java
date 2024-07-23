package com.project.library.domain.bookCategory.mapper;

import com.project.library.domain.book.Book;
import com.project.library.domain.bookCategory.BookCategory;
import com.project.library.domain.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookCategoryMapper {
    BookCategoryMapper INSTANCE = Mappers.getMapper(BookCategoryMapper.class);

    // book, category -> bookCategory
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "book", target = "book")
    @Mapping(source = "category", target = "category")
    BookCategory bookAndCategoryToBookCategory(Book book, Category category);
}
