package com.jpabook.pracjpashop.repository;


import com.jpabook.pracjpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    private String memberNames;
    private OrderStatus orderStatus;
}
