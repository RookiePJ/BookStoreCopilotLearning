package pjr.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pjr.bookstore.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

