package com.inatel.stockquotemanager.response;

import com.inatel.stockquotemanager.exception.ApiException;
import io.swagger.annotations.ApiModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApiModel(value = "Provides standardized response object")
public class ResponseHandler {

    public ResponseEntity standardizedResponse(String message, String httpStatus) {

        HttpStatus requestStatus;

        switch (httpStatus) {
            case "200": requestStatus = HttpStatus.OK; break;
            case "201": requestStatus = HttpStatus.CREATED; break;
            case "202": requestStatus = HttpStatus.ACCEPTED; break;
            case "203": requestStatus = HttpStatus.NON_AUTHORITATIVE_INFORMATION; break;
            case "204": requestStatus = HttpStatus.NO_CONTENT; break;
            case "205": requestStatus = HttpStatus.RESET_CONTENT; break;
            case "206": requestStatus = HttpStatus.PARTIAL_CONTENT; break;
            default: requestStatus = HttpStatus.I_AM_A_TEAPOT; break;

        }

        ApiException apiException = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                httpStatus,
                message
        );

        return new ResponseEntity<>(apiException, requestStatus);


    }

}
