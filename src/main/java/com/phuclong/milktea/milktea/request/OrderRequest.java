package com.phuclong.milktea.milktea.request;

import com.phuclong.milktea.milktea.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
