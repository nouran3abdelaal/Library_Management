package com.example.demo.repositories;

import com.example.demo.models.BorrowingRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends CrudRepository<BorrowingRecord,Integer> {
    Optional<BorrowingRecord> findByBookIdAndPatronIdAndReturningDateIsNull(int bookId, int patronId);

}
