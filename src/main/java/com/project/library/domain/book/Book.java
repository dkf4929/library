package com.project.library.domain.book;

import com.project.library.domain.author.Author;
import com.project.library.domain.bookCategory.BookCategory;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NamedEntityGraph(name = "book-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "author"),
                @NamedAttributeNode(value = "bookCategories", subgraph = "category-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(name = "category-subgraph", attributeNodes = @NamedAttributeNode("category"))
        }
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    // 작가
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, foreignKey = @ForeignKey(name = "fk_author_to_book"))
    private Author author;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookCategory> bookCategories = new ArrayList<>();

    // 도서 제목
    @Column(nullable = false)
    private String title;

    // 대여 가능여부
    @Column(nullable = false, name = "is_available")
    private boolean available;

    public void addBookCategory(BookCategory bookCategory) {
        this.bookCategories.add(bookCategory);
    }
}
