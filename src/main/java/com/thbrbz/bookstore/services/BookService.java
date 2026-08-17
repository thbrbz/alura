package com.thbrbz.bookstore.services;

import com.thbrbz.bookstore.dtos.BookDto;
import com.thbrbz.bookstore.models.AuthorModel;
import com.thbrbz.bookstore.models.BookModel;
import com.thbrbz.bookstore.models.ReviewModel;
import com.thbrbz.bookstore.repositories.AuthorRepository;
import com.thbrbz.bookstore.repositories.BookRepository;
import com.thbrbz.bookstore.repositories.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Transactional
    public BookModel saveBook(BookDto bookDto) throws Exception {
        BookModel book = new BookModel();

        book.setTitle(bookDto.title());
        book.setAuthors(new HashSet<>(authorRepository.findAllById(bookDto.authorsId())));
        book.setPublisher(publisherRepository.findById(
                bookDto.idPublisher()
        ).orElseThrow(() -> new RuntimeException("Publisher not found.")));

        ReviewModel review = new ReviewModel();
        review.setComment(bookDto.reviewComment());
        review.setBook(book);
        book.setReview(review);

        return bookRepository.save(book);
    }

    public List<BookModel> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
