package com.example.demo.services;

import com.example.demo.models.Book;
import com.example.demo.repositories.BookRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L)
                .collectionSizeRange(1, 5)
                .scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);
    }

    @Test
    void testGetAllBooks() {
        List<Book> expectedBooks = easyRandom.objects(Book.class, 5).toList();
        when(bookRepository.findAll()).thenReturn(expectedBooks);
        List<Book> actualBooks = bookService.getAllBooks();
        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void testGetBookById() {
        Book expectedBook = easyRandom.nextObject(Book.class);
        int id = expectedBook.getId();
        when(bookRepository.findById(id)).thenReturn(Optional.of(expectedBook));
        Optional<Book> actualBook = bookService.getBookByID(id);
        assertEquals(expectedBook, actualBook.orElse(null));
    }

    @Test
    void testAddBooks() {
        List<Book> booksToAdd = easyRandom.objects(Book.class, 5).toList();
        when(bookRepository.saveAll(booksToAdd)).thenReturn(booksToAdd);
        List<Book> addedBooks = bookService.addBooks(booksToAdd);
        assertEquals(booksToAdd, addedBooks);
    }

    @Test
    void testEditBook() {
        Book bookToUpdate = easyRandom.nextObject(Book.class);
        int id = bookToUpdate.getId();
        when(bookRepository.save(any(Book.class))).thenReturn(bookToUpdate);
        when(bookRepository.findById(any())).thenReturn(Optional.of(bookToUpdate));
        Book editedBook = bookService.editBook(bookToUpdate, id);
        assertEquals(bookToUpdate, editedBook);
    }

    @Test
    void testDeleteBook() {
        Book bookToDelete = easyRandom.nextObject(Book.class);
        int id = bookToDelete.getId();
        bookService.deleteBook(id);
        verify(bookRepository).deleteById(id);
    }
}
