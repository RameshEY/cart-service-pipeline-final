package com.bbs.cart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Cart {

    private String cartId;
    private String userType;
    private String userId;
    private Date createdDate;
    private Date lastUpdatedTime;
    private String itemCount;
}
