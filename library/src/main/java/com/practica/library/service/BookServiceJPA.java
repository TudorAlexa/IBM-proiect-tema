package com.practica.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.library.model.Book;
import com.practica.library.model.BookDTO;
import com.practica.library.repository.BookRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceJPA {

    private final BookRepositoryJPA bookRepositoryJPA;
    private final ObjectMapper mapper;

    public BookServiceJPA(BookRepositoryJPA bookRepositoryJPA, ObjectMapper mapper) {
        this.bookRepositoryJPA = bookRepositoryJPA;
        this.mapper = mapper;
    }

    public List<BookDTO> getBooks() {

        return bookRepositoryJPA.findAll()
                .stream()
                .map(book -> mapper.convertValue(book, BookDTO.class))
                .toList();
    }

    public BookDTO saveBook(Book book) {

        return mapper.convertValue(bookRepositoryJPA.save(book), BookDTO.class);
    }

    public BookDTO lostBook(Long id) {
        Book book = bookRepositoryJPA.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setLost(true);
        bookRepositoryJPA.save(book);

        return mapper.convertValue(book, BookDTO.class);
    }

    public List<BookDTO> findBooksThatAreLost(boolean lostOrNot) {
        if (lostOrNot) {

            return bookRepositoryJPA.findBooksThatAreLost()
                    .stream()
                    .map(book -> mapper.convertValue(book, BookDTO.class))
                    .toList();
        }
        return bookRepositoryJPA.findBooksThatAreNotLost()
                .stream()
                .map(book -> mapper.convertValue(book, BookDTO.class))
                .toList();

    }

}

