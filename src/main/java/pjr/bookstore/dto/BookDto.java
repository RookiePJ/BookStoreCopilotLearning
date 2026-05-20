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
public class BookDto {
    private Long id;

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "author must not be blank")
    private String author;

    private LocalDate publishedDate;

    @NotNull(message = "price must not be null")
    private BigDecimal price;
}

