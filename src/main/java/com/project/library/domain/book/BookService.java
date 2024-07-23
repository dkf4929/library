package com.project.library.domain.book;

import com.project.library.domain.author.Author;
import com.project.library.domain.author.AuthorRepository;
import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.author.mapper.AuthorMapper;
import com.project.library.domain.book.dto.BookRequestDto;
import com.project.library.domain.book.dto.BookResponseDto;
import com.project.library.domain.book.dto.BookSaveRequestDto;
import com.project.library.domain.book.dto.BookUpdateRequestDto;
import com.project.library.domain.book.mapper.BookMapper;
import com.project.library.domain.bookCategory.BookCategory;
import com.project.library.domain.bookCategory.BookCategoryRepository;
import com.project.library.domain.bookCategory.mapper.BookCategoryMapper;
import com.project.library.domain.category.Category;
import com.project.library.domain.category.CategoryRepository;
import com.project.library.domain.category.dto.CategoryResponseDto;
import com.project.library.domain.category.mapper.CategoryMapper;
import com.project.library.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;

    private Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("해당 도서를 찾을 수 없습니다."));
    }

    @Transactional
    public Book saveBook(BookSaveRequestDto requestDto) {
        Author author = authorRepository.findById(requestDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("작가를 찾을 수 없습니다."));

        // Book entity 변환
        Book book = BookMapper.INSTANCE.requestDtoToBook(requestDto, author);

        // 저장
        bookRepository.save(book);

        requestDto.getCategoryId().stream()
                .map(categoryId -> {
                    // bookCategory 변환
                    Category category = categoryRepository.findById(categoryId)
                            .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));
                    return BookCategoryMapper.INSTANCE.bookAndCategoryToBookCategory(book, category);
                })
                .forEach(bookCategory -> {
                    // bookCategory 저장
                    bookCategoryRepository.save(bookCategory);

                    // 연관관계 매핑
                    book.addBookCategory(bookCategory);

                    // 저장
                    categoryRepository.save(bookCategory.getCategory());
                });

        return book;
    }

    @Transactional(readOnly = true)
    public List<BookResponseDto> findBookAll() {
        // 모든 도서 정보를 찾는다.
        // 대여가능한 도서만 필터링 한다.
        // MapperStruct를 통해, responseDto로 변환한다.
        return bookRepository.findAll().stream()
                .filter(Book::isAvailable)
                .map(book -> {
                    Author author = book.getAuthor();

                    AuthorResponseDto authorResponseDto = AuthorMapper.INSTANCE.authorToAuthorResponseDto(author);

                    List<CategoryResponseDto> categoryResponseDtoList = book.getBookCategories().stream()
                            .map(bookCategory -> CategoryMapper.INSTANCE
                                    .categoryToCategoryResponseDto(bookCategory.getCategory()))
                            .collect(Collectors.toList());

                    return BookMapper.INSTANCE.bookToBookResponseDto(book, authorResponseDto, categoryResponseDtoList);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookResponseDto> findBookByCategoryAndAuthorAndTitle(BookRequestDto requestDto) {
        // 조건에 맞는 도서 조회
        List<Book> books = bookRepository.findBooksByCategoryAndAuthorAndTitle(
                requestDto.getCategoryId(),
                requestDto.getAuthorId(),
                requestDto.getTitle()
        );

        return books.stream()
                .map(book -> {
                    // MapperStruct를 통한 변환
                    List<CategoryResponseDto> categoryResponseDtoList = book.getBookCategories().stream()
                            .map(bookCategory -> CategoryMapper.INSTANCE
                                    .categoryToCategoryResponseDto(bookCategory.getCategory()))
                            .collect(Collectors.toList());

                    return BookMapper.INSTANCE.bookToBookResponseDto(book,
                            AuthorMapper.INSTANCE.authorToAuthorResponseDto(book.getAuthor()),
                            categoryResponseDtoList);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateBook(BookUpdateRequestDto requestDto) {
        // 필요한 엔티티를 찾는다.
        Book book = findById(requestDto.getBookId());

        Author author = authorRepository.findById(requestDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("작가를 찾을 수 없습니다."));

        List<Category> categories = categoryRepository.findAllById(requestDto.getCategoryId());

        List<BookCategory> bookCategoryList = categories.stream()
                .map(category -> BookCategoryMapper.INSTANCE.bookAndCategoryToBookCategory(book, category))
                .collect(Collectors.toList());

        // MapperStruct를 통한 엔티티 업데이트
        BookMapper.INSTANCE.updateDtoToBook(requestDto, book, author, bookCategoryList);
    }
}
