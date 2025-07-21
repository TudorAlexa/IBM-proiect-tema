package com.practica.library.repository;

import com.practica.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositoryJPA extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.lost = false")
    List<Book> findBooksThatAreNotLost();

    @Query("SELECT b FROM Book b WHERE b.lost = true")
    List<Book> findBooksThatAreLost();
}
