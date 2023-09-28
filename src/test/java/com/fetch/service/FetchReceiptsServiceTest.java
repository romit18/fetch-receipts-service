package com.fetch.service;

import com.fetch.service.dtos.ItemRequestDto;
import com.fetch.service.dtos.ReceiptRequestDto;
import com.fetch.service.processor.ReceiptProcessor;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MicronautTest
class FetchReceiptsServiceTest {

  @Inject EmbeddedApplication<?> application;
  @Inject
  ReceiptProcessor receiptProcessor;

  @Test
  void testItWorks() {
    Assertions.assertTrue(application.isRunning());
  }

  @Test
  void checkForRules() {
    List<ItemRequestDto> itemRequestDtoList = new ArrayList<>(List.of(new ItemRequestDto("Mountain Dew 12PK", "6.49"), new ItemRequestDto("   Klarbrunn 12-PK 12 FL OZ  ", "12.00")));

    ReceiptRequestDto receiptRequestDto = ReceiptRequestDto.builder().items(itemRequestDtoList).purchaseDate("2022-01-01").purchaseTime("13:01").retailer("Target").total("35.35").build();

    assert receiptRequestDto.validateRequestAndGenerateReceipt();

    ReceiptProcessor.Id id = receiptProcessor.processReceipts(receiptRequestDto);
    assert id != null;

    assert receiptProcessor.getReceipt(id.id()).points() == 20;

  }
}
