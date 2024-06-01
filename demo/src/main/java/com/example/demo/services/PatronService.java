package com.example.demo.services;

import com.example.demo.exceptions.BookAlreadyBorrowedException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Book;
import com.example.demo.models.BorrowingRecord;
import com.example.demo.models.Patron;
import com.example.demo.repositories.PatronRepository;
import com.example.demo.repositories.BookRepository;
import com.example.demo.repositories.BorrowingRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatronService {
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    public List<Patron> getAllPatrons() {
        return (List<Patron>) patronRepository.findAll();
    }

    public Optional<Patron> getPatronByID(int id) {
        return patronRepository.findById(id);
    }

    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public List<Patron> addPatrons(List<Patron> patrons) {
        return (List<Patron>) patronRepository.saveAll(patrons);
    }

    public Patron editPatron(Patron patron, int id) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        if (optionalPatron.isPresent()) {
            Patron patronTemp = optionalPatron.get();
            patronTemp.setEmail(patron.getEmail());
            patronTemp.setName(patron.getName());
            patronTemp.setPhoneNumber(patron.getPhoneNumber());

            return patronRepository.save(patronTemp);
        } else {
            throw new RuntimeException("Parton with id " + id + " not found");
        }
    }
    public void deletePatron(int id) {
        if (!patronRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patron not found with ID: " + id);
        }
        patronRepository.deleteById(id);
    }

    public BorrowingRecord borrowBook(int bookId, int patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with ID: " + patronId));
        Optional<BorrowingRecord> isBookBorrowed = borrowingRecordRepository.findByBookIdAndPatronIdAndReturningDateIsNull(bookId, patronId);
        if (isBookBorrowed.isPresent()) {
            throw new BookAlreadyBorrowedException("The book is already borrowed by the patron");
        }
        BorrowingRecord record = BorrowingRecord.builder()
                .book(book)
                .patron(patron)
                .borrowingDate(new Date())
                .returningDate(null)
                .build();

        return borrowingRecordRepository.save(record);
    }

    public BorrowingRecord returnBook(int bookId, int patronId) {
        BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronIdAndReturningDateIsNull(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("No active borrowing record found for book ID: " + bookId + " and patron ID: " + patronId));
        record.setReturningDate(new Date());
        return borrowingRecordRepository.save(record);
    }
}
