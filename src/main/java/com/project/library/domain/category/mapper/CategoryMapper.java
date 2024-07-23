package com.project.library.domain.category.mapper;

import com.project.library.domain.category.Category;
import com.project.library.domain.category.dto.CategoryResponseDto;
import com.project.library.domain.category.dto.CategorySaveRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // entity to responseDto
    @Mapping(source = "id", target = "categoryId")
    CategoryResponseDto categoryToCategoryResponseDto(Category category);

    // requestDto to entity
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(target = "bookCategories", ignore = true)
    Category saveRequestDtoToCategory(CategorySaveRequestDto requestDto);
}
