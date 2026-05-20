package pjr.bookstore.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(final Long id) {
        super("Book not found with id: " + id);
    }
}

