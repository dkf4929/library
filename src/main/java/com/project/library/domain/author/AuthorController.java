package com.project.library.domain.author;

import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.author.dto.AuthorSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AUTHOR REST API", description = "작가 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @Operation(summary = "작가 추가", description = "작가 정보를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작가 정보가 추가되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "작가 정보 수정 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"입력값 검증에 실패했습니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PostMapping
    public ResponseEntity<Long> saveAuthor(@Valid @RequestBody AuthorSaveRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(requestDto).getId());
    }

    @Operation(summary = "작가 조회", description = "등록된 모든 작가의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "작가 조회 완료.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @GetMapping
    public List<AuthorResponseDto> findAllAuthor() {
        return authorService.findAllAuthor();
    }

    @Operation(summary = "작가 삭제", description = "작가를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "작가가 삭제되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "작가 삭제 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"유효하지 않은 ID 입니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable("authorId") Long authorId) {
        authorService.deleteAuthor(authorId);
    }
}
