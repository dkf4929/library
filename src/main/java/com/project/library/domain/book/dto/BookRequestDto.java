package com.project.library.domain.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "도서 조회용 요청 DTO")
public class BookRequestDto {
    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryId;

    @Schema(description = "작가 ID", example = "1")
    private Long authorId;

    @Schema(description = "제목", example = "너에게 해주지 못한 말들")
    private String title;
}
