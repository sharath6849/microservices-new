package com.sharath.inventory_service.service;

import com.sharath.inventory_service.dto.InventoryResponse;
import com.sharath.inventory_service.model.Inventory;
import com.sharath.inventory_service.repository.InventoryRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRespository inventoryRespository;

    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Checking Inventory");
        return inventoryRespository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .inStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();

    }
}
