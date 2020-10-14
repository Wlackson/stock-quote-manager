package com.inatel.stockquotemanager.repository;

import com.inatel.stockquotemanager.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = "SELECT quote_id, quote_stock_id, quote_date, quote_value FROM bootdb.quotes ORDER BY quote_stock_id, DATE(quote_date) ASC;", nativeQuery = true)
    List<Quote> getAll();

    @Query(value = "SELECT * FROM bootdb.quotes WHERE quote_stock_id = :stockId ORDER BY quote_date ASC;", nativeQuery = true)
    List<Quote> getOneById(@Param("stockId") String stockId);

}