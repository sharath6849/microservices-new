package com.sharath.inventory_service.service;

import com.sharath.inventory_service.model.Inventory;
import com.sharath.inventory_service.repository.InventoryRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRespository inventoryRespository;

    public boolean isInStock(String skuCode) {

        return inventoryRespository.findBySkuCode(skuCode).isPresent();
    }

}
