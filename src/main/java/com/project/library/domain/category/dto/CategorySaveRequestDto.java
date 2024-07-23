package com.project.library.domain.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "카테고리 응답 DTO")
public class CategorySaveRequestDto {
    @Schema(description = "카테고리명", example = "과학")
    private String name;
}
