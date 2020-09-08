package com.example.admin.repository;

import com.example.admin.model.entity.Partner;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class PartnerRepositoryTest {

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    public void create() {
        Partner partner = Partner.builder()
                .name("junheon")
                .status("REGISTERED")
                .address("경기도 수원시")
//                .categoryId(1L)
                .build();

        Partner newPartner = partnerRepository.save(partner);

        Assert.assertNotNull(newPartner);
        System.out.println(newPartner);
    }

    @Test
    public void read() {
        Optional<Partner> selectPartner = partnerRepository.findById(1L);

        Assert.assertNotNull(selectPartner);
        System.out.println(selectPartner);
    }
}
