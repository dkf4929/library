package com.project.library.domain.book.mapper;

import com.project.library.domain.author.Author;
import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.book.Book;
import com.project.library.domain.book.dto.BookResponseDto;
import com.project.library.domain.book.dto.BookSaveRequestDto;
import com.project.library.domain.book.dto.BookUpdateRequestDto;
import com.project.library.domain.bookCategory.BookCategory;
import com.project.library.domain.category.dto.CategoryResponseDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-18T16:46:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public Book requestDtoToBook(BookSaveRequestDto requestDto, Author author) {
        if ( requestDto == null && author == null ) {
            return null;
        }

        Book book = new Book();

        if ( requestDto != null ) {
            book.setTitle( requestDto.getTitle() );
            book.setAvailable( requestDto.isAvailable() );
        }
        book.setAuthor( author );

        return book;
    }

    @Override
    public BookResponseDto bookToBookResponseDto(Book book, AuthorResponseDto author, List<CategoryResponseDto> categories) {
        if ( book == null && author == null && categories == null ) {
            return null;
        }

        BookResponseDto bookResponseDto = new BookResponseDto();

        if ( book != null ) {
            bookResponseDto.setBookId( book.getId() );
            bookResponseDto.setTitle( book.getTitle() );
            bookResponseDto.setAvailable( book.isAvailable() );
        }
        bookResponseDto.setAuthor( author );
        List<CategoryResponseDto> list = categories;
        if ( list != null ) {
            bookResponseDto.setCategories( new ArrayList<CategoryResponseDto>( list ) );
        }

        return bookResponseDto;
    }

    @Override
    public Book updateDtoToBook(BookUpdateRequestDto dto, Book book, Author author, List<BookCategory> bookCategories) {
        if ( dto == null && author == null && bookCategories == null ) {
            return book;
        }

        if ( dto != null ) {
            book.setTitle( dto.getTitle() );
            book.setAvailable( dto.isAvailable() );
        }
        book.setAuthor( author );
        if ( book.getBookCategories() != null ) {
            List<BookCategory> list = bookCategories;
            if ( list != null ) {
                book.getBookCategories().clear();
                book.getBookCategories().addAll( list );
            }
            else {
                book.setBookCategories( null );
            }
        }
        else {
            List<BookCategory> list = bookCategories;
            if ( list != null ) {
                book.setBookCategories( new ArrayList<BookCategory>( list ) );
            }
        }

        return book;
    }
}
