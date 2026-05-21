package pjr.bookstore.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class BookDto implements Comparable<BookDto> {
    private Long id;

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "author must not be blank")
    private String author;

    private LocalDate publishedDate;

    @NotNull(message = "price must not be null")
    private BigDecimal price;

    @Override
    public int compareTo(final BookDto other) {
        if (other == null) {
            return 1;
        }
        // Primary sort: by author (ascending)
        final int authorComparison = this.author.compareTo(other.author);
        if (authorComparison != 0) {
            return authorComparison;
        }
        // Secondary sort: by title if authors are equal (ascending)
        return this.title.compareTo(other.title);
    }
}
