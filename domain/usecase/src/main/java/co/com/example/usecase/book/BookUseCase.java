package co.com.example.usecase.book;

import co.com.example.model.Book;
import co.com.example.model.gateway.BookRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BookUseCase {

    private final BookRepository bookRepository;

    public Mono<Book> create(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Book> get(String key) {
        return bookRepository.get(key);
    }

    public Flux<Book> getAll() {
        return bookRepository.getAll();
    }
}
