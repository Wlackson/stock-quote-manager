package com.inatel.stockquotemanager.service;

import com.inatel.stockquotemanager.data.QuoteMapper;
import com.inatel.stockquotemanager.data.QuoteResponseDto;
import com.inatel.stockquotemanager.model.Quote;
import com.inatel.stockquotemanager.repository.QuoteRepository;
import org.springframework.stereotype.Service;
import wiremock.net.minidev.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final CacheService cacheService;
    private final RestClientService restClientService;

    public QuoteService(QuoteRepository quoteRepository, CacheService cacheService, RestClientService restClientService) {
        this.quoteRepository = quoteRepository;
        this.cacheService = cacheService;
        this.restClientService = restClientService;
    }

    public List<JSONObject> getOne(String id) {

        var quotes = quoteRepository.getOneById(id);

        return prepareReturnData(quotes);
    }

    public List<JSONObject> getAll() {

        var quotes = quoteRepository.getAll();

        var quoteList = quotes.stream().map(QuoteResponseDto::converter)
                .collect(Collectors.toList());

        System.out.println(quoteList);

        return prepareReturnData(quotes);

    }

    public boolean save(QuoteMapper quotes) {

        if (!cacheService.verifyCacheKeyExists(quotes.getId())) {

            return false;

        }

        Map<String, String> quote = quotes.getQuotes();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for (String key : quote.keySet()) {

            try {

                Quote quoteIn = new Quote();
                quoteIn.setQuote_stock_id(quotes.getId());
                quoteIn.setQuote_date(formatter.parse(key));
                quoteIn.setQuote_value(Double.parseDouble(quote.get(key)));

                quoteRepository.save(quoteIn);

            } catch (Exception ignored) {

                return false;

            }

        }

        return true;
    }

    public void rebuildCache() {

        cacheService.clearCache()
                .thenRun(() -> {

                    restClientService.getAllStocks().thenApply(cachedData -> {

                        try {

                            cacheService.populateCache(cachedData);

                        } catch (Exception ignored) {
                        }

                        return null;
                    });

                });

    }

    private List<JSONObject> prepareReturnData(List<Quote> quotes) {

        List<JSONObject> entities = new ArrayList<>();
        JSONObject stock = new JSONObject();
        JSONObject quote = new JSONObject();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        if (quotes.size() == 1) {

            for (var n : quotes) {
                String date = simpleDateFormat.format(n.getQuote_date());
                quote.put(date, n.getQuote_value().toString());
                stock.put("id", n.getQuote_stock_id());
                stock.put("quotes", new JSONObject(quote));
                entities.add(new JSONObject(stock));
            }

        } else {

            String ctrl = "";
            int last = 1;

            for (var n : quotes) {

                String date = simpleDateFormat.format(n.getQuote_date());

                if (stock.isEmpty()) {
                    stock.put("id", n.getQuote_stock_id());
                }

                if (ctrl.equals("")) {
                    ctrl = n.getQuote_stock_id();
                    stock.put("id", n.getQuote_stock_id());
                }

                if (!Objects.equals(ctrl, n.getQuote_stock_id())) {

                    stock.put("quotes", new JSONObject(quote));
                    entities.add(new JSONObject(stock));
                    stock.clear();
                    quote.clear();
                    ctrl = n.getQuote_stock_id();
                    stock.put("id", n.getQuote_stock_id());

                }

                quote.put(date, n.getQuote_value().toString());

                if (quotes.size() == last) {

                    stock.put("quotes", new JSONObject(quote));
                    entities.add(new JSONObject(stock));

                }

                last++;

            }

        }

        return entities;
    }

}
