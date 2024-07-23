package com.project.library.domain.author.mapper;

import com.project.library.domain.author.Author;
import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.author.dto.AuthorSaveRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    // entity to responseDto 변환
    @Mapping(source = "id", target = "authorId")
    AuthorResponseDto authorToAuthorResponseDto(Author author);

    // 저장 requestDto to entity
    @Mapping(source = "name", target = "name")
    @Mapping(target = "id", ignore = true)
    Author saveRequestDtoToAuthor(AuthorSaveRequestDto requestDto);
}
