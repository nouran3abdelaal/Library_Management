package com.example.demo.services;

import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    public Optional<Book> getBookByID(int id) {
        return bookRepository.findById(id);

    }

    public List<Book> addBooks(List<Book> Books) {
        return (List<Book>) bookRepository.saveAll(Books);

    }


    public Book editBook(Book book, int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPublicationYear(book.getPublicationYear());
            existingBook.setIsbn(book.getIsbn());

            return bookRepository.save(existingBook);
        } else {
            throw new RuntimeException("Book with id " + id + " not found");
        }
    }


    public void deleteBook(int id) {
        bookRepository.deleteById(id);

    }
}
