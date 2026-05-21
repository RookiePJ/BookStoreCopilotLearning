package pjr.bookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pjr.bookstore.domain.Book;
import pjr.bookstore.dto.BookDto;
import pjr.bookstore.repository.BookRepository;
import pjr.bookstore.service.impl.BookServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class BookServiceSortTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void findAllSorted_shouldReturnBooksOrderedByAuthor() {
        // given
        Book orwell = Book.builder()
                .withId(1L)
                .withTitle("1984")
                .withAuthor("George Orwell")
                .withPublishedDate(LocalDate.of(1949, 6, 8))
                .withPrice(new BigDecimal("9.99"))
                .build();

        Book austen = Book.builder()
                .withId(2L)
                .withTitle("Pride and Prejudice")
                .withAuthor("Jane Austen")
                .withPublishedDate(LocalDate.of(1813, 1, 28))
                .withPrice(new BigDecimal("6.99"))
                .build();

        Book fitzgerald = Book.builder()
                .withId(3L)
                .withTitle("The Great Gatsby")
                .withAuthor("F. Scott Fitzgerald")
                .withPublishedDate(LocalDate.of(1925, 4, 10))
                .withPrice(new BigDecimal("8.99"))
                .build();

        // Note: repository returns unsorted, service should sort
        given(bookRepository.findAll()).willReturn(Arrays.asList(orwell, austen, fitzgerald));

        // when
        List<BookDto> sortedBooks = bookService.findAllSorted();

        // then - should be sorted by author ascending
        assertThat(sortedBooks).hasSize(3);
        assertThat(sortedBooks.get(0).getAuthor()).isEqualTo("F. Scott Fitzgerald");
        assertThat(sortedBooks.get(1).getAuthor()).isEqualTo("George Orwell");
        assertThat(sortedBooks.get(2).getAuthor()).isEqualTo("Jane Austen");

        then(bookRepository).should().findAll();
    }

    @Test
    void findAllSorted_sameAuthorDifferentTitles_shouldSortByTitle() {
        // given
        Book zebra = Book.builder()
                .withId(1L)
                .withTitle("Zebra Animal Farm")
                .withAuthor("George Orwell")
                .withPrice(new BigDecimal("9.99"))
                .build();

        Book animal = Book.builder()
                .withId(2L)
                .withTitle("Animal Farm")
                .withAuthor("George Orwell")
                .withPrice(new BigDecimal("7.99"))
                .build();

        given(bookRepository.findAll()).willReturn(Arrays.asList(zebra, animal));

        // when
        List<BookDto> sortedBooks = bookService.findAllSorted();

        // then - same author, sorted by title
        assertThat(sortedBooks).hasSize(2);
        assertThat(sortedBooks.get(0).getTitle()).isEqualTo("Animal Farm");
        assertThat(sortedBooks.get(1).getTitle()).isEqualTo("Zebra Animal Farm");
    }

    @Test
    void findAllSorted_empty_shouldReturnEmpty() {
        // given
        given(bookRepository.findAll()).willReturn(Arrays.asList());

        // when
        List<BookDto> sortedBooks = bookService.findAllSorted();

        // then
        assertThat(sortedBooks).isEmpty();
    }
}

