package com.inatel.stockquotemanager.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(QuoteController.class)
class QuoteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getQuoteByID() throws Exception {

        /*
        RequestBuilder request = MockMvcRequestBuilders.get("/test1");
        MvcResult result = mvc.perform(request).andReturn();
        String str = "{\"message\":\"Unable to find quote id: test1!\",\"status\":\"404\",\"timestamp\":\"2020-10-14T22:39:40.130316633Z\"}";
        assertEquals("ok", result.getResponse().getContentAsString());
        */
    }

    @Test
    void findAll() {
    }

    @Test
    void insertQuote() {
    }

    @Test
    void rebuildCache() {
    }
}