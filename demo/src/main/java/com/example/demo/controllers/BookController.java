package com.example.demo.controllers;


import com.example.demo.models.Book;
import com.example.demo.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @RequestMapping
    public List<Book> getAllBooks() {
         return  bookService.getAllBooks();
    }

@RequestMapping("/{id}")
public Optional<Book> getBookByID(@PathVariable int id) {
    return  bookService.getBookByID(id);
}


    @PostMapping
    public List<Book> addBook(@Valid @RequestBody List<Book> books) {
        System.out.println(books);
        return bookService.addBooks(books);
    }


    @PutMapping( value = "/{id}")
    public Book editBook(@Valid @RequestBody Book books, @PathVariable int id) {
        return bookService.editBook(books,id);
    }
    @DeleteMapping( value = "/{id}")
    public void deleteBook( @PathVariable int id) {
         bookService.deleteBook(id);
    }



}
