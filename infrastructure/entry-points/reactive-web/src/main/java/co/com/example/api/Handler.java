package co.com.example.api;

import co.com.example.model.Book;
import co.com.example.usecase.book.BookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Handler {

    private final BookUseCase bookUseCase;

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Book.class)
                .flatMap(bookUseCase::create)
                .flatMap(book -> ServerResponse.ok().bodyValue(book));
    }

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {

        return Mono.just(Objects.requireNonNull(serverRequest.pathVariable("id")))
                .flatMap(bookUseCase::get)
                .flatMap(book -> ServerResponse.ok().bodyValue(book));
    }
}
