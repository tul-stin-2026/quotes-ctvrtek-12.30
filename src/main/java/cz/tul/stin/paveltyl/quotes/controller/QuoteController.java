package cz.tul.stin.paveltyl.quotes.controller;

import cz.tul.stin.paveltyl.quotes.model.ExternalQuote;
import cz.tul.stin.paveltyl.quotes.model.Quote;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {
    private final List<Quote> quotes = new ArrayList<>();
    private final RestTemplate restTemplate;

    public QuoteController(RestTemplate restTemplate) {
        quotes.add(new Quote(
                1L,
                "Komu se neleni, tomu se zeleni!",
                "Neznama babicka"));
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Quote> getQuotes() {
        return quotes;
    }

    @PostMapping
    public Quote addQuote(@RequestBody Quote quote) {
        quote.setId((long) (quotes.size() + 1));

        quotes.add(quote);

        return quote;
    }

    @GetMapping("/{id}")
    public Quote getQuote(@PathVariable Long id) {
        return quotes.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    @GetMapping("/random")
    public Quote getRandomQuote() {
        String url = "https://zenquotes.io/api/random";

        ExternalQuote[] response =
                restTemplate.getForObject(url, ExternalQuote[].class);

        if (response == null || response.length == 0) {
            throw new RuntimeException("API nic nevratilo!");
        }

        ExternalQuote external = response[0];

        return new Quote(
                null,
                external.getQ(),
                external.getA()
        );
    }
}
