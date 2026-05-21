package pjr.bookstore.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookDtoTest {

    @Test
    void compareTo_byAuthor_shouldSortAscending() {
        // given
        BookDto orwell = BookDto.builder()
                .withId(1L)
                .withTitle("1984")
                .withAuthor("George Orwell")
                .withPublishedDate(LocalDate.of(1949, 6, 8))
                .withPrice(new BigDecimal("9.99"))
                .build();

        BookDto austen = BookDto.builder()
                .withId(2L)
                .withTitle("Pride and Prejudice")
                .withAuthor("Jane Austen")
                .withPublishedDate(LocalDate.of(1813, 1, 28))
                .withPrice(new BigDecimal("6.99"))
                .build();

        BookDto fitzgerald = BookDto.builder()
                .withId(3L)
                .withTitle("The Great Gatsby")
                .withAuthor("F. Scott Fitzgerald")
                .withPublishedDate(LocalDate.of(1925, 4, 10))
                .withPrice(new BigDecimal("8.99"))
                .build();

        // when - create unsorted list
        List<BookDto> books = new ArrayList<>();
        books.add(orwell);
        books.add(austen);
        books.add(fitzgerald);

        books.sort(BookDto::compareTo);

        // then - should be sorted by author ascending (alphabetically)
        // F. Scott Fitzgerald < George Orwell < Jane Austen
        assertEquals("F. Scott Fitzgerald", books.get(0).getAuthor());
        assertEquals("George Orwell", books.get(1).getAuthor());
        assertEquals("Jane Austen", books.get(2).getAuthor());
    }

    @Test
    void compareTo_sameAuthorDifferentTitles_shouldSortByTitle() {
        // given
        BookDto book1 = BookDto.builder()
                .withId(1L)
                .withTitle("Zebra Book")
                .withAuthor("George Orwell")
                .withPrice(new BigDecimal("9.99"))
                .build();

        BookDto book2 = BookDto.builder()
                .withId(2L)
                .withTitle("Animal Farm")
                .withAuthor("George Orwell")
                .withPrice(new BigDecimal("7.99"))
                .build();

        // when
        int comparison = book1.compareTo(book2);

        // then - book1 should come after book2 (positive value)
        assertTrue(comparison > 0);
        assertEquals("Animal Farm", book2.getTitle());
        assertEquals("Zebra Book", book1.getTitle());
    }

    @Test
    void compareTo_identical_shouldReturnZero() {
        // given
        BookDto book1 = BookDto.builder()
                .withId(1L)
                .withTitle("1984")
                .withAuthor("George Orwell")
                .withPrice(new BigDecimal("9.99"))
                .build();

        BookDto book2 = BookDto.builder()
                .withId(2L)
                .withTitle("1984")
                .withAuthor("George Orwell")
                .withPrice(new BigDecimal("10.00"))
                .build();

        // when
        int comparison = book1.compareTo(book2);

        // then - same author and title should return 0
        assertEquals(0, comparison);
    }

    @Test
    void compareTo_nullOther_shouldReturnPositive() {
        // given
        BookDto book = BookDto.builder()
                .withId(1L)
                .withTitle("1984")
                .withAuthor("George Orwell")
                .withPrice(new BigDecimal("9.99"))
                .build();

        // when
        int comparison = book.compareTo(null);

        // then - comparing to null should return positive
        assertTrue(comparison > 0);
    }
}
