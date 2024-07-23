package com.project.library.domain.category;

import com.project.library.domain.category.dto.CategoryResponseDto;
import com.project.library.domain.category.dto.CategorySaveRequestDto;
import com.project.library.domain.category.mapper.CategoryMapper;
import com.project.library.global.exception.DuplicationDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveCategory(CategorySaveRequestDto requestDto) {
        Category category = null;

        // 중복데이터 삽입 시, EXCEPTION
        try {
            category = categoryRepository.save(CategoryMapper.INSTANCE.saveRequestDtoToCategory(requestDto));
        } catch (DataIntegrityViolationException e) {
            throw new DuplicationDataException("중복 데이터가 삽입되었습니다.");
        }

        return category;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAllCategory() {
        // 모든 카테고리 검색
        // MapperStruct를 통한 dto 변환
        return categoryRepository.findAll().stream()
                .map(category -> CategoryMapper.INSTANCE.categoryToCategoryResponseDto(category))
                .sorted(Comparator.comparingLong(CategoryResponseDto::getCategoryId))
                .collect(Collectors.toList());
    }

    // 삭제
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
