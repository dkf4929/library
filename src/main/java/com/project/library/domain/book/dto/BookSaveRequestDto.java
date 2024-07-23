package com.project.library.domain.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "도서 저장 요청 DTO")
public class BookSaveRequestDto {
    @NotNull(message = "작가를 입력하세요.")
    @Schema(description = "작가 ID", example = "1")
    private Long authorId;

    @NotEmpty(message = "카테고리는 하나 이상 입력해야합니다.")
    @Schema(description = "카테고리 ID", example = "1")
    private Set<Long> categoryId = new HashSet<>();

    @NotBlank(message = "제목을 입력하세요.")
    @Schema(description = "도서 제목", example = "너에게 해주지 못한 말들")
    private String title;

    @Schema(description = "대여 가능여부", example = "true")
    private boolean available;
}
