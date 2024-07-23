package com.project.library.domain.author.mapper;

import com.project.library.domain.author.Author;
import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.author.dto.AuthorSaveRequestDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-18T16:46:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorResponseDto authorToAuthorResponseDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorResponseDto authorResponseDto = new AuthorResponseDto();

        authorResponseDto.setAuthorId( author.getId() );
        authorResponseDto.setName( author.getName() );

        return authorResponseDto;
    }

    @Override
    public Author saveRequestDtoToAuthor(AuthorSaveRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Author author = new Author();

        author.setName( requestDto.getName() );

        return author;
    }
}
