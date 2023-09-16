package co.com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Book {

    private String id;

    private String title;

    private int page;

    private String isbn;

    private String description;

    private double price;

    private String publicationDate;

    private String language;
}
