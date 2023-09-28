package controllers;

import com.fetch.service.dtos.PointsResponseDto;
import com.fetch.service.dtos.ReceiptRequestDto;
import com.fetch.service.processor.ReceiptProcessor;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller("${api-config.base-url:/fetch}")
@ExecuteOn(TaskExecutors.IO)
@Introspected
public class FetchController {

    @Inject
    ReceiptProcessor receiptProcessor;
    @Get(uri = "/receipts/{id}/points", produces = MediaType.APPLICATION_JSON)
    public Mono<PointsResponseDto> getPointsById(@PathVariable UUID id) {
        return Mono.fromCallable(() -> PointsResponseDto.builder().points(receiptProcessor.getReceipt(id).points()).build());
    }

    @Post(uri = "/receipts/process", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Mono<ReceiptProcessor.Id> getPointsById(@Body ReceiptRequestDto receiptRequestDto) {
        receiptRequestDto.validateRequestAndGenerateReceipt();
        return Mono.fromCallable(() -> receiptProcessor.processReceipts(receiptRequestDto));
    }
}
