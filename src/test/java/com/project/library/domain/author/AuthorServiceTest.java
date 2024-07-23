package com.project.library.domain.author;

import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.author.dto.AuthorSaveRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class AuthorServiceTest {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("전체 조회")
    @Transactional(readOnly = true)
    void findAllAuthor() {
        List<AuthorResponseDto> authorList = authorService.findAllAuthor();

        // 초기 데이터 작가 총 9명
        Assertions.assertThat(authorList.size()).isEqualTo(9);
    }

    @Test
    @DisplayName("작가 저장")
    @Transactional
    void saveAuthor() {
        AuthorSaveRequestDto requestDto = AuthorSaveRequestDto.builder()
                .name("송영호")
                .build();

        Author author = authorService.saveAuthor(requestDto);

        Assertions.assertThat(author.getName()).isEqualTo("송영호");
    }

    @Test
    @DisplayName("작가 삭제")
    @Transactional
    void deleteAuthor() {
        AuthorSaveRequestDto requestDto = AuthorSaveRequestDto.builder()
                .name("송영호")
                .build();

        // 저장
        Author author = authorService.saveAuthor(requestDto);

        Assertions.assertThat(authorRepository.findById(author.getId())).isNotEmpty();

        authorService.deleteAuthor(author.getId());

        // 삭제 시 비어있는지 확인.
        Assertions.assertThat(authorRepository.findById(author.getId())).isEqualTo(Optional.empty());
    }
}