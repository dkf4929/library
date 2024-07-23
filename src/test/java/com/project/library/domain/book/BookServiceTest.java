package com.project.library.domain.book;

import com.project.library.domain.author.Author;
import com.project.library.domain.author.AuthorRepository;
import com.project.library.domain.book.dto.BookRequestDto;
import com.project.library.domain.book.dto.BookResponseDto;
import com.project.library.domain.book.dto.BookSaveRequestDto;
import com.project.library.domain.book.dto.BookUpdateRequestDto;
import com.project.library.domain.book.mapper.BookMapper;
import com.project.library.domain.bookCategory.BookCategory;
import com.project.library.domain.bookCategory.BookCategoryRepository;
import com.project.library.domain.category.Category;
import com.project.library.domain.category.CategoryRepository;
import com.project.library.domain.category.dto.CategoryResponseDto;
import com.project.library.global.exception.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class BookServiceTest {
    @Autowired BookService bookService;
    @Autowired CategoryRepository categoryRepository;
    @Autowired BookRepository bookRepository;
    @Autowired AuthorRepository authorRepository;
    @Autowired BookCategoryRepository bookCategoryRepository;

    @Test
    @DisplayName("도서 전체 조회")
    @Transactional(readOnly = true)
    void findAllBook() {
        List<BookResponseDto> bookList = bookService.findBookAll();

        // 초기 데이터 15
        Assertions.assertThat(bookList.size()).isEqualTo(15);
    }

    @Test
    @DisplayName("도서 저장")
    @Transactional
    void saveBook() {
        BookSaveRequestDto errorRequestDto = BookSaveRequestDto.builder()
                .title("")
                .authorId(100L)
                .categoryId(List.of())
                .build();

        // ID 미존재 시, exception 발생
        Assertions.assertThatThrownBy(() -> bookService.saveBook(errorRequestDto))
                .isInstanceOf(EntityNotFoundException.class);

        BookSaveRequestDto requestDto = BookSaveRequestDto.builder()
                .title("CDRI")
                .authorId(1L)
                .categoryId(List.of(1L))
                .available(true)
                .build();

        Book savedBook = bookService.saveBook(requestDto);

        // 타이틀이 같은지 비교한다.
        Assertions.assertThat(savedBook.getTitle()).isEqualTo(requestDto.getTitle());
    }

    @Test
    @DisplayName("카테고리, 작가, 제목으로 도서 검색")
    @Transactional(readOnly = true)
    void findBookByCategoryAndAuthorAndTitle() {
        Long authorId = 1L;
        Long categoryId = 1L;

        // dto 생성
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                .authorId(authorId)
                .categoryId(categoryId)
                .title("너에게 해주지 못한 말들")
                .build();

        // 조회조건에 일치하는 response 조회
        List<BookResponseDto> bookResponseDtoList = bookService.findBookByCategoryAndAuthorAndTitle(bookRequestDto);

        // 조건에 맞는 요소만 추출
        List<BookResponseDto> filteringList = bookResponseDtoList.stream()
                .filter(bookResponseDto -> {
                    List<Long> categoryIdList = bookResponseDto.getCategories().stream()
                            .map(CategoryResponseDto::getCategoryId)
                            .collect(Collectors.toList());

                    return bookResponseDto.isAvailable() &&
                            bookResponseDto.getAuthor().getAuthorId() == authorId &&
                            categoryIdList.contains(categoryId) &&
                            bookResponseDto.getTitle().equals("너에게 해주지 못한 말들");
                })
                .collect(Collectors.toList());

        Assertions.assertThat(filteringList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("도서 정보 수정")
    @Transactional
    void updateBook() {
        Long bookId = 1L;
        Long authorId = 3L;
        Long categoryId = 3L;
        String title = "CDRI";
        boolean available = false;

        // dto 생성
        BookUpdateRequestDto requestDto = BookUpdateRequestDto.builder()
                .bookId(bookId)
                .authorId(authorId)
                .categoryId(List.of(categoryId))
                .title(title)
                .available(available)
                .build();

        // 업데이트 수행
        bookService.updateBook(requestDto);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("해당 도서를 찾을 수 없습니다."));

        Assertions.assertThat(book.getAuthor().getId()).isEqualTo(authorId);

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("해당 작가를 찾을 수 없습니다."));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));

        List<BookCategory> bookCategoryList = bookCategoryRepository.findByCategoryAndBook(category, book);

        // 매퍼를 통해 entity 변환
        Book updateBook = BookMapper.INSTANCE.updateDtoToBook(requestDto,
                book,
                author,
                bookCategoryList);

        // 동등 비교
        Assertions.assertThat(updateBook).usingRecursiveComparison().isEqualTo(book);
    }
}