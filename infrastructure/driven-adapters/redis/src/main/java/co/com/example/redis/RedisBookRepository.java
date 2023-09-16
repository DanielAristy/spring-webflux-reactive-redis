package co.com.example.redis;

import co.com.example.model.Book;
import co.com.example.model.gateway.BookRepository;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RedisBookRepository implements BookRepository {

    private final ObjectMapper mapper;

    private final ReactiveRedisComponent reactiveRedisComponent;
    public static final String BOOK_KEY = "BK";

    @Override
    public Mono<Book> save(Book book) {
        return reactiveRedisComponent.set(BOOK_KEY, book.getId(), book).map(b -> book);
    }

    @Override
    public Mono<Book> get(String key) {
        return reactiveRedisComponent.get(BOOK_KEY, key).flatMap(d -> Mono.just(mapper.map(d, Book.class)));
    }

    @Override
    public Flux<Book> getAll() {
        return reactiveRedisComponent.get(BOOK_KEY).map(b -> mapper.map(b, Book.class))
                .collectList().flatMapMany(Flux::fromIterable);
    }

}
