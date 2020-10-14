package com.inatel.stockquotemanager.data;

import java.util.Date;

public class QuoteRequestDto {

    private Long quote_id;
    private String quote_stock_id;
    private Date quote_date;
    private Double quote_value;

    public Long getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(Long quote_id) {
        this.quote_id = quote_id;
    }

    public String getQuote_stock_id() {
        return quote_stock_id;
    }

    public void setQuote_stock_id(String quote_stock_id) {
        this.quote_stock_id = quote_stock_id;
    }

    public Date getQuote_date() {
        return quote_date;
    }

    public void setQuote_date(Date quote_date) {
        this.quote_date = quote_date;
    }

    public Double getQuote_value() {
        return quote_value;
    }

    public void setQuote_value(Double quote_value) {
        this.quote_value = quote_value;
    }
}
