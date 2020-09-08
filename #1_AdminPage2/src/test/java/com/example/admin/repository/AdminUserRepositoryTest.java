package com.example.admin.repository;

import com.example.admin.model.entity.AdminUser;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AdminUserRepositoryTest {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    public void create() {
        AdminUser adminUser = AdminUser.builder()
                .account("AdminTest1")
                .password("1234")
                .status("REGISTERED")
                .role("PARTNER")
                .build();

        AdminUser newAdminUser = adminUserRepository.save(adminUser);

        Assert.assertNotNull(newAdminUser);
        System.out.println(newAdminUser);
    }

    @Test
    public void read() {
        Optional<AdminUser> selectAdminUser = adminUserRepository.findById(1L);

        Assert.assertNotNull(selectAdminUser);
        selectAdminUser.ifPresent(adminUser -> System.out.println(adminUser));
    }
}
