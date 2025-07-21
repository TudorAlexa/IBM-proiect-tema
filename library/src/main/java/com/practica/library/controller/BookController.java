package com.practica.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.library.model.Book;
import com.practica.library.model.BookDTO;
import com.practica.library.service.BookService;
import com.practica.library.service.BookServiceJPA;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Log4j2
@RestController
@RequestMapping("/book")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookController {

    Logger logger = Logger.getLogger("BookController");

    BookService bookService;
    BookServiceJPA bookServiceJPA;
    ObjectMapper mapper;

    public BookController(BookService bookService, ObjectMapper mapper, BookServiceJPA bookServiceJPA) {
        this.bookService = bookService;
        this.mapper = mapper;
        this.bookServiceJPA = bookServiceJPA;
    }

    @GetMapping
    public List<BookDTO> getBook() {

//        return bookService.getBooks();
        return bookServiceJPA.getBooks();
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody BookDTO book){
        logger.info("adding book: " + book.getName());
//        bookService.addBook(mapper.convertValue(book, Book.class));
        bookServiceJPA.saveBook(mapper.convertValue(book, Book.class));
        return ResponseEntity.accepted()
                .body(book.toString());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long id){
        logger.info("deleting book.");
        boolean wasDeleted = bookService.deleteBook(id);
        if(wasDeleted){
            logger.info("book with ID " + id + " was deleted.");
            return ResponseEntity
                    .status(201)
                    .body("book with ID " + id + " was deleted.");
        }

        else {
            logger.info("book with ID " + id + " does not exist.");
            return ResponseEntity
                    .status(404)
                    .body("book with ID " + id + " does not exist.");
        }
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") Long id, @RequestBody BookDTO book){
        logger.info("updating book.");
        boolean wasUpdated = bookService.updateBook(id,book);
        if(wasUpdated){
            logger.info("book with ID " + id + " was updated.");
            return ResponseEntity
                    .status(201)
                    .body("book with ID " + id + " was updated.");
        }

        else {
            logger.info("book with ID " + id + " does not exist.");
            return ResponseEntity
                    .status(404)
                    .body("book with ID " + id + " does not exist.");
        }
    }

    @PutMapping("/jpa/{id}")
    public ResponseEntity<BookDTO> updateBookJPA(@PathVariable("id") Long id, @RequestBody BookDTO book){
        logger.info("updating book: " + book);
        Book book1 = mapper.convertValue(book, Book.class);
        book1.setId(id);
        return ResponseEntity.ok(bookServiceJPA.saveBook(book1));
    }

    @PutMapping("/books{id}/lost")
    public ResponseEntity<BookDTO> lostBook(@PathVariable Long id) {
        BookDTO updateBook = bookServiceJPA.lostBook(id);
        return ResponseEntity.ok(updateBook);
    }

    @GetMapping("/book/lostList/{lostOrNot}")
    public ResponseEntity<List<BookDTO>> lostOrNotList(@PathVariable("lostOrNot") boolean lostOrNot){
        List<BookDTO>lostOrNotList=bookServiceJPA.findBooksThatAreLost(lostOrNot);
        return ResponseEntity.ok(lostOrNotList);
    }
}
