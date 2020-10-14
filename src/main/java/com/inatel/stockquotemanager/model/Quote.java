package com.inatel.stockquotemanager.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quote_id;

    @Column(name = "quote_stock_id")
    private String quote_stock_id;

    @Column(name = "quote_date")
    private Date quote_date;

    @Column(name = "quote_value")
    private Double quote_value;

    public Quote() {
    }

    public Quote(String quote_stock_id, Date quote_date, Double quote_value) {
        this.quote_stock_id = quote_stock_id;
        this.quote_date = quote_date;
        this.quote_value = quote_value;
    }

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
