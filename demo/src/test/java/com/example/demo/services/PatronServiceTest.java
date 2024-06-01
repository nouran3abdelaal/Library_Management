package com.example.demo.services;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Book;
import com.example.demo.models.BorrowingRecord;
import com.example.demo.models.Patron;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.BorrowingRecordRepository;
import com.example.demo.repositories.PatronRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @InjectMocks
    private PatronService patronService;
    private EasyRandom easyRandom;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configure EasyRandom with parameters
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L) // Set seed for consistent random data generation (optional)
                .collectionSizeRange(1, 5) // Set range for collection sizes
                .scanClasspathForConcreteTypes(true); // Allow EasyRandom to scan classpath for concrete types
        easyRandom = new EasyRandom(parameters);
    }

    @Test
    void testAddPatron() {
        Patron patron = new Patron(1, "John", "john@example.com", "1234567890");
        when(patronRepository.save(patron)).thenReturn(patron);

        Patron result = patronService.addPatron(patron);

        assertNotNull(result);
        assertEquals("John", result.getName());
    }

    @Test
    void testEditPatron() {
        Patron patron = new Patron(1, "John", "john@example.com", "1234567890");
        when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
        when(patronRepository.save(patron)).thenReturn(patron);

        Patron updatedPatron = new Patron(1, "John Doe", "johndoe@example.com", "9876543210");
        Patron result = patronService.editPatron(updatedPatron, 1);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("johndoe@example.com", result.getEmail());
    }

    @Test
    void testDeletePatron() {
        when(patronRepository.existsById(any())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> patronService.deletePatron(1));
    }

    @Test
    void testBorrowBook() {
        Book book = new Book(1, "Sample Book", "John Doe", "2022", "1234567890");
        Patron patron = new Patron(1, "John", "john@example.com", "1234567890");
        BorrowingRecord borrowingRecord = new BorrowingRecord(1, book, patron, new Date(), null);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturningDateIsNull(1, 1)).thenReturn(Optional.empty());
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = patronService.borrowBook(1, 1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(book, result.getBook());
        assertEquals(patron, result.getPatron());
    }

    @Test
    void testReturnBook() {
        BorrowingRecord borrowingRecord = new BorrowingRecord(1, null, null, new Date(), null);

        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturningDateIsNull(1, 1)).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        BorrowingRecord result = patronService.returnBook(1, 1);

        assertNotNull(result);
        assertNotNull(result.getReturningDate());
    }

    @Test
    void getAllPatrons() {
        List<Patron> expectedPatron = easyRandom.objects(Patron.class, 5).toList();
        when(patronRepository.findAll()).thenReturn(expectedPatron);
        List<Patron> patrons = patronService.getAllPatrons();
        assertEquals(expectedPatron, patrons);
    }

    @Test
    void getPatronByID() {
        Patron patron = easyRandom.nextObject(Patron.class);
        int id = patron.getId();
        when(patronRepository.findById(id)).thenReturn(Optional.of(patron));
        Optional<Patron> actualBook = patronService.getPatronByID(id);
        assertEquals(patron, actualBook.orElse(null));
    }

    @Test
    void addPatron() {
        List<Patron> patrons = easyRandom.objects(Patron.class, 5).toList();

        // Mock repository to return the added books
        when(patronRepository.saveAll(patrons)).thenReturn(patrons);

        // Act
        List<Patron> addPatrons = patronService.addPatrons(patrons);

        // Assert
        assertEquals(patrons, addPatrons);
    }
}
