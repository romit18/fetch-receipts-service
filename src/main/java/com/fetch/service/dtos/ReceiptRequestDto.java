package com.fetch.service.dtos;

import com.fetch.exceptions.FetchServiceBadRequestException;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Serdeable.Deserializable
@Builder
public record ReceiptRequestDto(@NotNull String retailer, @NotNull String purchaseDate, @NotNull String purchaseTime, List<ItemRequestDto> items, String total) {

    static final String TIME_PATTERN = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$";

    static final String DATE_PATTERN = "\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])*";

    public boolean validateRequestAndGenerateReceipt(){
        if(!purchaseTime.matches(TIME_PATTERN)){
            //these can be customized as per use case
            throw new FetchServiceBadRequestException("the valid format for time is HH:MM");
        }

        if(!purchaseDate.matches(DATE_PATTERN) || (purchaseDate.matches(DATE_PATTERN) && LocalDate.parse(purchaseDate).isAfter(LocalDate.now()))){
            throw new FetchServiceBadRequestException("the valid format for date is YYYY-MM-DD");
        }

        if(items.isEmpty()){
            throw new FetchServiceBadRequestException("Items cannot be empty.");
        }

        //we can add more validations like total = sum of all prices too. not reqd for the task though.
        return true;

    }
}
