package pjr.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pjr.bookstore.domain.Book;
import pjr.bookstore.dto.BookDto;
import pjr.bookstore.exception.BookNotFoundException;
import pjr.bookstore.mapper.BookMapper;
import pjr.bookstore.repository.BookRepository;
import pjr.bookstore.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDto create(final BookDto bookDto) {
        final Book book = BookMapper.toEntity(bookDto);
        final Book saved = bookRepository.save(book);
        log.debug("Created book with id={}", saved.getId());
        return BookMapper.toDto(saved);
    }

    @Override
    public BookDto update(final Long id, final BookDto bookDto) {
        final Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        // Update fields explicitly to avoid replacing other columns unintentionally
        existing.setTitle(bookDto.getTitle());
        existing.setAuthor(bookDto.getAuthor());
        existing.setPublishedDate(bookDto.getPublishedDate());
        existing.setPrice(bookDto.getPrice());

        final Book saved = bookRepository.save(existing);
        log.debug("Updated book with id={}", saved.getId());
        return BookMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(final Long id) {
        final Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return BookMapper.toDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAllSorted() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDto)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public void delete(final Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
        log.debug("Deleted book with id={}", id);
    }
}

