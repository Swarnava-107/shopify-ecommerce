package org.dev.ecomm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;
    private Double price;
    private String status;
    private Date orderDate;
    private String userName;
    private String userEmail;
    private List<OrderItemDto> orderItemDtoList;
}
