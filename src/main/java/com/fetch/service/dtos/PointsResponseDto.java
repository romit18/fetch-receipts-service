package com.fetch.service.dtos;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable.Serializable
@Builder
public record PointsResponseDto(int points) {
}
