package com.example.admin.repository;

import com.example.admin.model.entity.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create() {
        User user = new User();
        user.setAccount("TestUser1");
        user.setPassword("1234");
//        user.setStatus("REGISTERED");
        user.setPhoneNumber("010-1111-1111");

        User newUser = userRepository.save(user);

        Assert.assertNotNull(newUser);
        System.out.println(newUser);
    }

    @Test
    @Transactional
    public void read() {
        Optional<User> selectUser = userRepository.findById(1L);

        Assert.assertNotNull(selectUser);

        selectUser.ifPresent(user ->
                user.getOrderGroupList().forEach(orderGroup -> {
                    System.out.println("====================주문 내역====================");
                    System.out.println("수령인: " + orderGroup.getRevName());
                    System.out.println("수령지: " + orderGroup.getRevAddress());
                    System.out.println("총금액" + orderGroup.getTotalPrice());
                    System.out.println("총주문수량: " + orderGroup.getTotalQuantity());

                    System.out.println("====================상세 내역====================");
                    orderGroup.getOrderDetailList().forEach(orderDetail -> {
                        System.out.println("주문금액: " + orderDetail.getTotalPrice());
                        System.out.println("주문수량: " + orderDetail.getQuantity());
                    });


                }));
    }

    @Test
    public void update() {
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("TestUser2");
            User updateUser = userRepository.save(selectUser);
            System.out.println(updateUser);
        });
    }

    @Test
    @Transactional
    public void delete() {
        Optional<User> user = userRepository.findById(1L);

        Assert.assertTrue(user.isPresent());

        user.ifPresent(selectUser -> {
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(1L);

        Assert.assertFalse(deleteUser.isPresent());
    }
}
