package com.project.library.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "카테고리 응답 DTO")
public class CategoryResponseDto {
    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @Schema(description = "카테고리명", example = "과학")
    private String name;
}
