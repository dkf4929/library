package com.project.library.domain.book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {
    @EntityGraph(value = "book-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();
}
