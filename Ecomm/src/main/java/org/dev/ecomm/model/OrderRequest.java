package org.dev.ecomm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Map<Long, Integer> productQuantities;

    private Double totalPrice;
}
