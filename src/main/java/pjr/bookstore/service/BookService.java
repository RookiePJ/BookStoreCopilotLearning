package pjr.bookstore.service;

import pjr.bookstore.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto create(final BookDto bookDto);
    BookDto update(final Long id, final BookDto bookDto);
    BookDto findById(final Long id);
    List<BookDto> findAll();
    void delete(final Long id);
}

