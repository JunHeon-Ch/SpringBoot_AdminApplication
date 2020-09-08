package com.example.admin.repository;

import com.example.admin.model.entity.OrderGroup;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class OrderGroupRepositoryTest {

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Test
    public void create() {
        OrderGroup orderGroup = OrderGroup.builder()
                .status("REGISTERED")
//                .orderType("DELIVERING")
                .revName("최준헌")
                .revAddress("경기도 화성시")
                .paymentType("CARD")
                .totalPrice(BigDecimal.valueOf(120000))
                .totalQuantity(1)
//                .userId(1L)
                .build();

        OrderGroup newOrderGroup = orderGroupRepository.save(orderGroup);

        Assert.assertNotNull(newOrderGroup);
        System.out.println(newOrderGroup);
    }

    @Test
    public void read() {
        Optional<OrderGroup> selectOrderGroup = orderGroupRepository.findById(1L);

        Assert.assertNotNull(selectOrderGroup);

        selectOrderGroup.ifPresent(orderGroup ->
                System.out.println(orderGroup)
        );
    }
}
