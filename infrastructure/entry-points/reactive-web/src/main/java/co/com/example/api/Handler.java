package co.com.example.api;

import co.com.example.model.Book;
import co.com.example.usecase.book.BookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Handler {

    private final BookUseCase bookUseCase;

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Book.class)
                .flatMap(bookUseCase::create)
                .flatMap(book -> ServerResponse.ok().bodyValue(book))
                .onErrorResume(throwable -> getResponseError(throwable.getMessage()));
    }

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {

        return Mono.just(Objects.requireNonNull(serverRequest.pathVariable("id")))
                .flatMap(bookUseCase::get)
                .flatMap(book -> ServerResponse.ok().bodyValue(book))
                .onErrorResume(throwable -> Mono.just(throwable.getMessage())
                        .flatMap(Handler::getResponseError)
                );
    }

    private static Mono<ServerResponse> getResponseError(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("id:", UUID.randomUUID().toString());
        error.put("response:", message);
        error.put("status:", "500");
        return ServerResponse.status(500).bodyValue(error);
    }

    public Mono<ServerResponse> listenGETALLUseCase(ServerRequest serverRequest) {
        return bookUseCase.getAll()
                .collectList().flatMap(books -> ServerResponse.ok().bodyValue(books))
                .onErrorResume(throwable -> getResponseError(throwable.getMessage()));
    }

    public Mono<ServerResponse> listenDELETEUseCase(ServerRequest serverRequest) {
        return Mono.just(Objects.requireNonNull(serverRequest.pathVariable("id")))
                .flatMap(bookUseCase::deleteById)
                .flatMap(value -> ServerResponse.ok().bodyValue(value))
                .onErrorResume(throwable -> getResponseError(throwable.getMessage()));

    }
}
