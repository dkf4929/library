package com.project.library.domain.category;

import com.project.library.domain.category.dto.CategoryResponseDto;
import com.project.library.domain.category.dto.CategorySaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class CategoryServiceTest {
    @Autowired CategoryService categoryService;
    @Autowired CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 조회")
    @Transactional(readOnly = true)
    void findAllCategory() {
        List<CategoryResponseDto> categoryList = categoryService.findAllCategory();

        // 카테고리 총 5개
        Assertions.assertThat(categoryList.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("카테고리 저장")
    @Transactional
    void saveCategory() {
        CategorySaveRequestDto requestDto = CategorySaveRequestDto.builder()
                .name("CDRI")
                .build();

        //카테고리 저장
        Category category = categoryService.saveCategory(requestDto);

        Assertions.assertThat(category.getName()).isEqualTo("CDRI");
    }

    @Test
    @DisplayName("카테고리 삭제")
    @Transactional
    void deleteCategory() {
        CategorySaveRequestDto requestDto = CategorySaveRequestDto.builder()
                .name("CDRI")
                .build();

        //카테고리 저장
        Category category = categoryService.saveCategory(requestDto);

        // 삭제
        categoryService.deleteCategory(category.getId());

        // 비어있는지 확인
        Assertions.assertThat(categoryRepository.findById(category.getId())).isEqualTo(Optional.empty());
    }
}