//package pjr.bookstore.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import pjr.bookstore.dto.BookDto;
//import pjr.bookstore.service.BookService;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
///**
// * Configuration to load test/sample book data on application startup.
// * This data is useful for development and testing purposes.
// *
// * This bean only runs when the 'dev' or 'prod' profiles are active.
// * It is skipped during unit/integration tests to avoid conflicts.
// */
//@Configuration
//@Profile({"dev", "prod", "default"})
//@Slf4j
//public class DataLoaderConfig {
//
//    @Bean
//    public CommandLineRunner loadTestData(final BookService bookService) {
//        return args -> {
//            log.info("[BookStore] Loading initial book data");
//
//            bookService.create(BookDto.builder()
//                    .withTitle("1984")
//                    .withAuthor("George Orwell")
//                    .withPublishedDate(LocalDate.of(1949, 6, 8))
//                    .withPrice(new BigDecimal("9.99"))
//                    .build());
//
//            bookService.create(BookDto.builder()
//                    .withTitle("Animal Farm")
//                    .withAuthor("George Orwell")
//                    .withPublishedDate(LocalDate.of(1945, 8, 17))
//                    .withPrice(new BigDecimal("7.99"))
//                    .build());
//
//            bookService.create(BookDto.builder()
//                    .withTitle("To Kill a Mockingbird")
//                    .withAuthor("Harper Lee")
//                    .withPublishedDate(LocalDate.of(1960, 7, 11))
//                    .withPrice(new BigDecimal("10.99"))
//                    .build());
//
//            bookService.create(BookDto.builder()
//                    .withTitle("The Great Gatsby")
//                    .withAuthor("F. Scott Fitzgerald")
//                    .withPublishedDate(LocalDate.of(1925, 4, 10))
//                    .withPrice(new BigDecimal("8.99"))
//                    .build());
//
//            bookService.create(BookDto.builder()
//                    .withTitle("Pride and Prejudice")
//                    .withAuthor("Jane Austen")
//                    .withPublishedDate(LocalDate.of(1813, 1, 28))
//                    .withPrice(new BigDecimal("6.99"))
//                    .build());
//
//            log.info("[BookStore] Initial book data loaded successfully");
//        };
//    }
//}
