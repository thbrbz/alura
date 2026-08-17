package com.thbrbz.bookstore.repositories;

import com.thbrbz.bookstore.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookModel, UUID> {

    BookModel findByTitle(String title); // Pesquisa com JPA

    @Query(value = "SELECT * FROM tb_book WHERE id_publisher = :id", nativeQuery = true) // Pesquisa com query nativa
    Set<BookModel> findBookByPublisherId(@Param("id") UUID id);
}
