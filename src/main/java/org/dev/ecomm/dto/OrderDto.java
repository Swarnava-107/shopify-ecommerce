package org.dev.ecomm.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private Long orderId;
    private Double price;
    private String status;
    private Date orderDate;
    private String userName;
    private String userEmail;
    private List<OrderItemDto> orderItemDtoList;
}
