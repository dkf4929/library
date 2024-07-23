package com.project.library.domain.author.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "작가 응답 DTO")
public class AuthorResponseDto {
    @Schema(description = "작가 ID", example = "1")
    private Long authorId;

    @Schema(description = "작가명", example = "권태영")
    private String name;
}
