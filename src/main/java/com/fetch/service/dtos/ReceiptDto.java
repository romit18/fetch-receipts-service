package com.fetch.service.dtos;

import io.micronaut.core.annotation.Generated;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

//reason for creating a new dto is to ensure that the fields are in their correct data formats going ahead.
@Serdeable.Serializable
@Builder
public record ReceiptDto(@Generated UUID id, @NotNull String retailer, @NotNull LocalDate purchaseDate, @NotNull LocalTime purchaseTime, List<ItemDto> items, double total, int points) {
}
