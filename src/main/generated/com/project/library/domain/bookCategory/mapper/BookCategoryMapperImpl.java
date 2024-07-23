package com.project.library.domain.bookCategory.mapper;

import com.project.library.domain.book.Book;
import com.project.library.domain.bookCategory.BookCategory;
import com.project.library.domain.category.Category;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-18T16:46:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class BookCategoryMapperImpl implements BookCategoryMapper {

    @Override
    public BookCategory bookAndCategoryToBookCategory(Book book, Category category) {
        if ( book == null && category == null ) {
            return null;
        }

        BookCategory bookCategory = new BookCategory();

        bookCategory.setBook( book );
        bookCategory.setCategory( category );

        return bookCategory;
    }
}
