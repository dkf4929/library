package com.project.library.domain.category.mapper;

import com.project.library.domain.category.Category;
import com.project.library.domain.category.dto.CategoryResponseDto;
import com.project.library.domain.category.dto.CategorySaveRequestDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-18T16:46:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponseDto categoryToCategoryResponseDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();

        categoryResponseDto.setCategoryId( category.getId() );
        categoryResponseDto.setName( category.getName() );

        return categoryResponseDto;
    }

    @Override
    public Category saveRequestDtoToCategory(CategorySaveRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( requestDto.getName() );

        return category;
    }
}
