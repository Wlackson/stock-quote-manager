package com.inatel.stockquotemanager.data;

import com.inatel.stockquotemanager.model.Quote;

import java.util.Date;

public class QuoteResponseDto {

    private String id;
    private Date date;
    private Double value;

    public static QuoteResponseDto converter(Quote q) {

        var quote = new QuoteResponseDto();
        quote.setId(q.getQuote_stock_id());
        quote.setDate(q.getQuote_date());
        quote.setValue(q.getQuote_value());

        return quote;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
