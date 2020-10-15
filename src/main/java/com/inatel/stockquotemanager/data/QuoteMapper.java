package com.inatel.stockquotemanager.data;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.inatel.stockquotemanager.model.Quote;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

public class QuoteMapper {

    @NotNull(message = "Id cannot be empty")
    @NotBlank(message = "Id cannot be empty")
    @Size(min = 5, max = 5, message = "Id must be 5 characters in length")
    private String id;

    @NotNull(message = "Quote data cannot be empty")
    @NotBlank(message = "Quote data cannot be empty")
    @Size(min = 4, max = 10, message = "Quote value must be between 0.00 and 999.999.00")
    private Map<String, String> quotes = new HashMap<>();

    public static QuoteResponseDto converter(Quote q) {

        var quote = new QuoteResponseDto();
        quote.setId(q.getQuote_stock_id());
        quote.setDate(q.getQuote_date());
        quote.setValue(q.getQuote_value());

        return quote;

    }

    public QuoteMapper() {
    }

    public QuoteMapper(String id, Map<String, String> quotes) {
        this.id = id;
        this.quotes = quotes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map<String, String> getQuotes() {
        return quotes;
    }

    @JsonAnySetter
    public void setQuotes(Map<String, String> quotes) {
        this.quotes = quotes;
    }
}
