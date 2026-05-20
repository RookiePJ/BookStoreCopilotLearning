package pjr.bookstore.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pjr.bookstore.domain.Book;
import pjr.bookstore.dto.BookDto;
import pjr.bookstore.exception.BookNotFoundException;
import pjr.bookstore.mapper.BookMapper;
import pjr.bookstore.repository.BookRepository;
import pjr.bookstore.service.impl.BookServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book sampleBook;
    private BookDto sampleDto;

    @BeforeEach
    void setUp() {
        sampleBook = Book.builder()
                .withId(1L)
                .withTitle("1984")
                .withAuthor("George Orwell")
                .withPublishedDate(LocalDate.of(1949, 6, 8))
                .withPrice(new BigDecimal("9.99"))
                .build();

        sampleDto = BookDto.builder()
                .withTitle("1984")
                .withAuthor("George Orwell")
                .withPublishedDate(LocalDate.of(1949, 6, 8))
                .withPrice(new BigDecimal("9.99"))
                .build();
    }

    @Test
    void create_shouldSaveAndReturnDto() {
        // given
        Book toSave = BookMapper.toEntity(sampleDto);
        Book saved = Book.builder()
                .withId(2L)
                .withTitle(toSave.getTitle())
                .withAuthor(toSave.getAuthor())
                .withPublishedDate(toSave.getPublishedDate())
                .withPrice(toSave.getPrice())
                .build();

        given(bookRepository.save(any(Book.class))).willReturn(saved);

        // when
        BookDto result = bookService.create(sampleDto);

        // then
        assertThat(result).isNotNull();
        assertEquals(2L, result.getId());
        assertEquals("1984", result.getTitle());
        then(bookRepository).should().save(any(Book.class));
    }

    @Test
    void update_shouldUpdateAndReturnDto() {
        // given
        final Long id = 1L;
        Book existing = Book.builder()
                .withId(id)
                .withTitle("Old")
                .withAuthor("Someone")
                .withPublishedDate(LocalDate.of(2000,1,1))
                .withPrice(new BigDecimal("1.00"))
                .build();

        Book updatedSaved = Book.builder()
                .withId(id)
                .withTitle(sampleDto.getTitle())
                .withAuthor(sampleDto.getAuthor())
                .withPublishedDate(sampleDto.getPublishedDate())
                .withPrice(sampleDto.getPrice())
                .build();

        given(bookRepository.findById(id)).willReturn(Optional.of(existing));
        given(bookRepository.save(any(Book.class))).willReturn(updatedSaved);

        // when
        BookDto result = bookService.update(id, sampleDto);

        // then
        assertThat(result).isNotNull();
        assertEquals(id, result.getId());
        assertEquals("1984", result.getTitle());
        then(bookRepository).should().findById(id);
        then(bookRepository).should().save(any(Book.class));
    }

    @Test
    void update_notFound_shouldThrow() {
        // given
        final Long id = 99L;
        given(bookRepository.findById(id)).willReturn(Optional.empty());

        // when / then
        assertThrows(BookNotFoundException.class, () -> bookService.update(id, sampleDto));
        then(bookRepository).should().findById(id);
    }

    @Test
    void findById_shouldReturnDto() {
        final Long id = 1L;
        given(bookRepository.findById(id)).willReturn(Optional.of(sampleBook));

        BookDto result = bookService.findById(id);

        assertThat(result).isNotNull();
        assertEquals(id, result.getId());
        then(bookRepository).should().findById(id);
    }

    @Test
    void findById_notFound_shouldThrow() {
        final Long id = 77L;
        given(bookRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findById(id));
        then(bookRepository).should().findById(id);
    }

    @Test
    void findAll_shouldReturnList() {
        // given
        Book b1 = sampleBook;
        Book b2 = Book.builder()
                .withId(3L)
                .withTitle("Animal Farm")
                .withAuthor("George Orwell")
                .withPublishedDate(LocalDate.of(1945,8,17))
                .withPrice(new BigDecimal("5.99"))
                .build();

        given(bookRepository.findAll()).willReturn(Arrays.asList(b1, b2));

        // when
        List<BookDto> list = bookService.findAll();

        // then
        assertThat(list).hasSize(2);
        assertEquals("1984", list.get(0).getTitle());
        assertEquals("Animal Farm", list.get(1).getTitle());
        then(bookRepository).should().findAll();
    }

    @Test
    void delete_existing_shouldDelete() {
        final Long id = 1L;
        given(bookRepository.existsById(id)).willReturn(true);

        bookService.delete(id);

        verify(bookRepository).deleteById(eq(id));
    }

    @Test
    void delete_notFound_shouldThrow() {
        final Long id = 55L;
        given(bookRepository.existsById(id)).willReturn(false);

        assertThrows(BookNotFoundException.class, () -> bookService.delete(id));
        then(bookRepository).should().existsById(id);
    }
}

