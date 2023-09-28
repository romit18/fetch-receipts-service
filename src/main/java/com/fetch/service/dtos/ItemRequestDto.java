package com.fetch.service.dtos;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

@Introspected
@Serdeable.Deserializable
public record ItemRequestDto(String shortDescription, String price) {
}
