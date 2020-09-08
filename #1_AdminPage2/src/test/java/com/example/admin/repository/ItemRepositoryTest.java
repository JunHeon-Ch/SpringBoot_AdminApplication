package com.example.admin.repository;

import com.example.admin.model.entity.Item;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        Item item = Item.builder()
//                .status("REGISTERED")
                .name("노트북")
                .title("삼성 노트북")
                .price(BigDecimal.valueOf(900000))
//                .partnerId(1L)
                .build();

        Item newItem = itemRepository.save(item);

        Assert.assertNotNull(newItem);
        System.out.println(newItem);
    }

    @Test
    public void read() {
        Optional<Item> selectItem = itemRepository.findById(1L);

        Assert.assertNotNull(selectItem);
        System.out.println(selectItem);
    }
}
