package com.fetch.service.dtos;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable.Serializable
public record ItemDto(String shortDescription, double price) {
}
