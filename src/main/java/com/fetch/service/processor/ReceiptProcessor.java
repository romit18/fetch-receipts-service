package com.fetch.service.processor;

import com.fetch.service.dtos.ItemDto;
import com.fetch.service.dtos.ReceiptDto;
import com.fetch.service.dtos.ReceiptRequestDto;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Singleton
public class ReceiptProcessor {

    final Map<UUID, ReceiptDto> allReceipts = new HashMap<>();

    @Serdeable.Serializable
    @Builder
    public record Id(UUID id){}

    public Id processReceipts(ReceiptRequestDto receiptRequestDto) {

        final int points = processPoints(receiptRequestDto);
        final List<ItemDto> itemList = receiptRequestDto.items().stream().map(item -> new ItemDto(item.shortDescription(), Double.parseDouble(item.price()))).toList();
        final UUID currentUUID = UUID.randomUUID();

        ReceiptDto receiptDto = ReceiptDto.builder()
                .items(itemList)
                .purchaseDate(LocalDate.parse(receiptRequestDto.purchaseDate()))
                .purchaseTime(LocalTime.parse(receiptRequestDto.purchaseTime()))
                .retailer(receiptRequestDto.retailer()).total(Double.parseDouble(receiptRequestDto.total())).id(currentUUID).points(points).build();

        allReceipts.put(currentUUID, receiptDto);
    return Id.builder().id(currentUUID).build();
    }

    private int processPoints(ReceiptRequestDto receiptDto){
        int points = 0;
        points += receiptDto.retailer().chars().mapToObj(currentChar -> Character.isLetterOrDigit((char) currentChar)).count();
        points += Double.parseDouble(receiptDto.total()) % 1 == 0 ? 50 : 0;
        points += Double.parseDouble(receiptDto.total()) % 0.25 == 0 ? 25 : 0;
        final int size = receiptDto.items().size();
        points += size % 2 == 0 ? (size*5)/2 : ((size - 1)*5)/2;
        //documentation says round of to nearest integer. for 2.4 should be 2 and not 3 as given in the README.md. however, to match the example, i can either use ceil if thats the case
        points += receiptDto.items().stream().map(currentItem -> currentItem.shortDescription().trim().length() % 3 == 0 ? (int)Math.ceil(Double.parseDouble(currentItem.price()) * 0.2) : 0).reduce(Integer::sum).get();
        points += LocalDate.parse(receiptDto.purchaseDate()).getDayOfMonth() % 2 != 0 ? 6 : 0;
        LocalTime timeOfReceipt = LocalTime.parse(receiptDto.purchaseTime());
        points += timeOfReceipt.isAfter(LocalTime.parse("14:00")) &&  timeOfReceipt.isBefore(LocalTime.parse("16:00")) ? 10 : 0;
        return points;
    }

    public ReceiptDto getReceipt(UUID id) {

        if(!allReceipts.containsKey(id)){
            throw new RuntimeException("This receipt does not exist.");
        }
        return allReceipts.get(id);
    }
}
