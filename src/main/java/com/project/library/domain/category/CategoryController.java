package com.project.library.domain.category;

import com.project.library.domain.category.dto.CategoryResponseDto;
import com.project.library.domain.category.dto.CategorySaveRequestDto;
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

@Tag(name = "CATEGORY REST API", description = "카테고리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "카테고리 추가", description = "카테고리 정보를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 정보가 추가되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "카테고리 정보 추가 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"입력값 검증에 실패했습니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @PostMapping
    public ResponseEntity<Long> saveCategory(@Valid @RequestBody CategorySaveRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(requestDto).getId());
    }

    @Operation(summary = "카테고리 조회", description = "등록된 모든 카테고리 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 조회 완료.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @GetMapping
    public List<CategoryResponseDto> findAllCategory() {
        return categoryService.findAllCategory();
    }

    @Operation(summary = "카테고리 삭제", description = "카테고리를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "카테고리 삭제되었습니다.", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "카테고리 삭제 실패", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"유효하지 않은 ID 입니다.\" }"))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"message\": \"서버 오류 발생.\" }"))),
    })
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
