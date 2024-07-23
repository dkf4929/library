package com.project.library.domain.book.dto;

import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.category.dto.CategoryResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(title = "도서 조회용 응답 DTO")
public class BookResponseDto {
    @Schema(description = "도서 ID", example = "1")
    private Long bookId;

    private AuthorResponseDto author;

    @Schema(description = "도서 제목", example = "너에게 해주지 못한 말들")
    private String title;

    private List<CategoryResponseDto> categories = new ArrayList<>();

    @Schema(description = "대여 가능여부", example = "true")
    private boolean isAvailable;
}
