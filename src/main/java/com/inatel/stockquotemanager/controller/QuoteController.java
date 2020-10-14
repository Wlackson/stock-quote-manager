package com.inatel.stockquotemanager.controller;

import com.inatel.stockquotemanager.data.QuoteMapper;
import com.inatel.stockquotemanager.exception.ApiRequestException;
import com.inatel.stockquotemanager.response.ResponseHandler;
import com.inatel.stockquotemanager.service.QuoteService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Class for handling read and write requests to the Quotes Management API
 * @author Wlackson Viera
 * @since 2020-10-14
 *
 */
@RestController
@RequestMapping("/api/v1/quotes")
@ApiModel(value = "Class for handling stock quotes CRUD operations")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    /**
     * Method for retrieving quotes by ID
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieves one quote records by ID.", notes = "Will return quote records that match the provided stock id.", response = QuoteController.class)
    public ResponseEntity getQuoteByID(@PathVariable("id") String id) {

        if (!id.isBlank() || (id.length() == 5)) {

            var data = quoteService.getOne(id);

            if (data.isEmpty()) {

                throw new ApiRequestException("Unable to find quote id: " + id + "!", new Throwable("404"));
            }

            return new ResponseEntity(data, HttpStatus.OK);

        }

        throw new ApiRequestException("Invalid id: " + id + "!", new Throwable("400"));

    }

    /**
     * Method for retrieving all quotes
     */
    @GetMapping
    @ApiOperation(value = "Retrieves all quote records.", notes = "Will return all quote records.", response = QuoteController.class)
    public ResponseEntity findAll() {

        var data = quoteService.getAll();

        if (data.isEmpty()) {

            throw new ApiRequestException("No records available at this time!", new Throwable("404"));
        }

        return new ResponseEntity(data, HttpStatus.OK);

    }

    /**
     * Method for inserting a new quote in the database
     */
    @PostMapping()
    @ApiOperation(value = "Creates a new quote record.", notes = "Will register a new quote.", response = QuoteController.class)
    public ResponseEntity insertQuote(@Valid @RequestBody QuoteMapper quotes, Errors error) {

        if (!error.hasErrors()) {

            var result = quoteService.save(quotes);

            if (result) {

                return new ResponseHandler().standardizedResponse("New quote record successfully created!", "201");

            }

        }

        throw new ApiRequestException("Unable to process your request!", new Throwable("400"));

    }


    /**
     * Accepts notications from stock-manager
     */
    @GetMapping("/stockcache")
    @ApiOperation(value = "Receives notification about stock changes.", notes = "Will get notificatiion from Stock Manager API service that a new stock ID was recorded.", response = QuoteController.class)
    @Async
    public void rebuildCache() {

        quoteService.rebuildCache();

    }

}

