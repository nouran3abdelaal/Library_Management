package com.example.demo.repositories;

import com.example.demo.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {
//    Page<Movie> findAll(Pageable pageable);

}
