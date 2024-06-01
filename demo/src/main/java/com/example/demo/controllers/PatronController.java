package com.example.demo.controllers;


import com.example.demo.models.BorrowingRecord;
import com.example.demo.models.Patron;
import com.example.demo.services.PatronService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patron")
public class PatronController {
    private final PatronService patronService;

    @RequestMapping
    public List<Patron> getAllPatrons() {
        return  patronService.getAllPatrons();
    }

    @RequestMapping("/{id}")
    public Optional<Patron> getPatronByID(@PathVariable int id) {
        return  patronService.getPatronByID(id);
    }

 
    @PostMapping
    public List<Patron> addPatron(@Valid @RequestBody List<Patron> Patrons) {
        return patronService.addPatrons(Patrons);
    }


    @PutMapping( value = "/{id}")
    public Patron editPatron(@Valid @RequestBody Patron Patrons, @PathVariable int id) {
        return patronService.editPatron(Patrons,id);
    }
    @DeleteMapping( value = "/{id}")
    public void deletePatron( @PathVariable int id) {
        patronService.deletePatron(id);
    }


    @PutMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable int bookId, @PathVariable int patronId) {
        BorrowingRecord record = patronService.borrowBook(bookId, patronId);
        return ResponseEntity.ok(record);
    }
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(@PathVariable int bookId, @PathVariable int patronId) {
        BorrowingRecord record = patronService.returnBook(bookId, patronId);
        return ResponseEntity.ok(record);
    }

}
