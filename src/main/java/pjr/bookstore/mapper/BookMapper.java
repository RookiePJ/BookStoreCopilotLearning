package pjr.bookstore.mapper;

import pjr.bookstore.domain.Book;
import pjr.bookstore.dto.BookDto;

public final class BookMapper {
    private BookMapper() {
        throw new UnsupportedOperationException("This class should never be instantiated");
    }

    public static BookDto toDto(final Book book) {
        if (book == null) {
            return null;
        }
        return BookDto.builder()
                .withId(book.getId())
                .withTitle(book.getTitle())
                .withAuthor(book.getAuthor())
                .withPublishedDate(book.getPublishedDate())
                .withPrice(book.getPrice())
                .build();
    }

    public static Book toEntity(final BookDto dto) {
        if (dto == null) {
            return null;
        }
        return Book.builder()
                .withId(dto.getId())
                .withTitle(dto.getTitle())
                .withAuthor(dto.getAuthor())
                .withPublishedDate(dto.getPublishedDate())
                .withPrice(dto.getPrice())
                .build();
    }
}

