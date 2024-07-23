package com.project.library.domain.book;

import com.project.library.domain.book.dto.BookRequestDto;
import com.project.library.domain.book.dto.BookResponseDto;
import com.project.library.domain.book.dto.BookSaveRequestDto;
import com.project.library.domain.book.dto.BookUpdateRequestDto;
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

@Tag(name = "BOOK REST API", description = "도서 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "도서 추가", description = "도서 정보를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 정보가 추가되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "도서 정보 추가 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"입력값 검증에 실패했습니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PostMapping
    public ResponseEntity<Long> saveBook(@Valid @RequestBody BookSaveRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(requestDto).getId());
    }

    @Operation(summary = "모든 도서 조회", description = "등록된 모든 도서 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 정보 조회 완료.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @GetMapping
    public List<BookResponseDto> findBookAll() {
        return bookService.findBookAll();
    }

    @Operation(summary = "도서 조회", description = "조건에 만족하는 도서를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 조회 완료.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PostMapping("/search")
    public List<BookResponseDto> findBookByCategoryAndAuthorAndTitle(@RequestBody BookRequestDto requestDto) {
        return bookService.findBookByCategoryAndAuthorAndTitle(requestDto);
    }

    @Operation(summary = "도서 수정", description = "도서 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도서 정보가 수정되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "도서 정보 수정 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"입력값 검증에 실패했습니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@Valid @RequestBody BookUpdateRequestDto requestDto) {
        bookService.updateBook(requestDto);
    }
}
