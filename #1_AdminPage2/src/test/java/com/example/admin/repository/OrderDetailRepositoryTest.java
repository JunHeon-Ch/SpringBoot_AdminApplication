package com.example.admin.repository;

import com.example.admin.model.entity.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void create() {
        OrderDetail orderDetail = OrderDetail.builder()
                .status("REGISTERED")
//                .itemId(1L)
//                .orderGroupId(1L)
                .build();

        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);

        Assert.assertNotNull(newOrderDetail);
        System.out.println("newOrderDetail = " + newOrderDetail);
    }

    @Test
    public void read() {
        Optional<OrderDetail> selectOrderDetail = orderDetailRepository.findById(1L);

        Assert.assertNotNull(selectOrderDetail);
        System.out.println("selectOrderDetail = " + selectOrderDetail);
    }
}
