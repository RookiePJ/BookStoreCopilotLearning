package pjr.bookstore.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pjr.bookstore.dto.BookDto;
import pjr.bookstore.service.BookService;

import java.net.URI;
import java.util.List;

/**
 * REST controller exposing CRUD operations for books.
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() {
        final List<BookDto> list = bookService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable("id") final Long id) {
        final BookDto dto = bookService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody @Valid final BookDto bookDto) {
        final BookDto created = bookService.create(bookDto);
        final URI location = URI.create(String.format("/api/books/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable("id") final Long id,
                                          @RequestBody @Valid final BookDto bookDto) {
        final BookDto updated = bookService.update(id, bookDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

