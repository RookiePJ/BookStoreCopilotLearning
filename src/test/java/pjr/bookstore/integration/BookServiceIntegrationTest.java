package pjr.bookstore.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pjr.bookstore.dto.BookDto;
import pjr.bookstore.exception.BookNotFoundException;
import pjr.bookstore.repository.BookRepository;
import pjr.bookstore.service.BookService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    private BookDto sampleDto;

    @BeforeEach
    void setUp() {
        sampleDto = BookDto.builder()
                .withTitle("Integration Test Book")
                .withAuthor("Integration Author")
                .withPublishedDate(LocalDate.of(2020,1,1))
                .withPrice(new BigDecimal("15.00"))
                .build();
    }

    @Test
    void contextLoads_andBeansAreWired() {
        assertThat(bookService).isNotNull();
        assertThat(bookRepository).isNotNull();
    }

    @Test
    void create_find_update_delete_flow() {
        // create
        BookDto created = bookService.create(sampleDto);
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();

        Long id = created.getId();

        // find
        BookDto fetched = bookService.findById(id);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getTitle()).isEqualTo(sampleDto.getTitle());

        // update
        BookDto update = BookDto.builder()
                .withTitle("Updated Title")
                .withAuthor(fetched.getAuthor())
                .withPublishedDate(fetched.getPublishedDate())
                .withPrice(new BigDecimal("20.00"))
                .build();

        BookDto afterUpdate = bookService.update(id, update);
        assertThat(afterUpdate.getTitle()).isEqualTo("Updated Title");
        assertThat(afterUpdate.getPrice()).isEqualByComparingTo(new BigDecimal("20.00"));

        // delete
        bookService.delete(id);
        assertThat(bookRepository.existsById(id)).isFalse();

        // find after delete should throw
        assertThrows(BookNotFoundException.class, () -> bookService.findById(id));
    }
}
