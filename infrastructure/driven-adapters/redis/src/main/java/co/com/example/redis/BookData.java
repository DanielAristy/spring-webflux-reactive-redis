package co.com.example.redis;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookData {

    @NotNull
    private String id;

    private String title;

    private int page;

    private String isbn;

    private String description;

    private double price;

    private String publicationDate;

    private String language;
}
