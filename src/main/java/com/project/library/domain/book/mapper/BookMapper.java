package com.project.library.domain.book.mapper;

import com.project.library.domain.author.Author;
import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.book.Book;
import com.project.library.domain.book.dto.BookResponseDto;
import com.project.library.domain.book.dto.BookSaveRequestDto;
import com.project.library.domain.book.dto.BookUpdateRequestDto;
import com.project.library.domain.bookCategory.BookCategory;
import com.project.library.domain.category.dto.CategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    // 저장 requestDto to entity
    @Mapping(source = "requestDto.title", target = "title")
    @Mapping(source = "requestDto.available", target = "available")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "author", target = "author")
    Book requestDtoToBook(BookSaveRequestDto requestDto, Author author);

    // entity to response
    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "author", target = "author")
    BookResponseDto bookToBookResponseDto(Book book, AuthorResponseDto author, List<CategoryResponseDto> categories);

    // updateDto to entity
    @Mapping(source = "dto.title", target = "title")
    @Mapping(source = "dto.available", target = "available")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "bookCategories", target = "bookCategories")
    @Mapping(target = "id", ignore = true)
    Book updateDtoToBook(BookUpdateRequestDto dto, @MappingTarget Book book, Author author, List<BookCategory> bookCategories);
}
