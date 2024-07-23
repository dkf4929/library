package com.project.library.domain.author;

import com.project.library.domain.author.dto.AuthorResponseDto;
import com.project.library.domain.author.dto.AuthorSaveRequestDto;
import com.project.library.domain.author.mapper.AuthorMapper;
import com.project.library.global.exception.ReferentialIntegrityException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    @Transactional
    public Author saveAuthor(AuthorSaveRequestDto requestDto) {
        // dto to entity 변환
        Author author = AuthorMapper.INSTANCE.saveRequestDtoToAuthor(requestDto);

        // 저장
        return authorRepository.save(author);
    }

    public List<AuthorResponseDto> findAllAuthor() {
        // 모든 작가를 찾는다.
        // dto로 변환(MapperStruct)
        return authorRepository.findAll().stream()
                .map(author -> AuthorMapper.INSTANCE.authorToAuthorResponseDto(author))
                .collect(Collectors.toList());
    }

    public void deleteAuthor(Long authorId) {
        try {
            authorRepository.deleteById(authorId);
        } catch (DataIntegrityViolationException e) {
            throw new ReferentialIntegrityException("도서 정보 삭제 후 작가 삭제가 가능합니다.");
        }
    }
}
