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
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "도서 수정용 요청 DTO")
public class BookUpdateRequestDto {
    @NotNull(message = "bookId를 입력하세요.")
    private Long bookId;

    @NotNull(message = "작가를 입력하세요.")
    private Long authorId;

    @NotEmpty(message = "카테고리는 하나 이상 입력해야합니다.")
    private List<Long> categoryId = new ArrayList<>();

    @NotBlank(message = "제목을 입력하세요.")
    private String title;

    private boolean available;
}
