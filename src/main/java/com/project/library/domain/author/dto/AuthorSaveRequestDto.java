package com.project.library.domain.author.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "작가 저장용 요청 DTO")
public class AuthorSaveRequestDto {
    @Schema(description = "작가명", example = "송영호")
    @NotBlank(message = "작가명을 입력하세요.")
    private String name;
}
