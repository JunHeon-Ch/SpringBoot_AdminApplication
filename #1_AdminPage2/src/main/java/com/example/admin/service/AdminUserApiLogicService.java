package com.example.admin.service;

import com.example.admin.model.entity.AdminUser;
import com.example.admin.model.network.Header;
import com.example.admin.model.network.request.AdminUserApiRequest;
import com.example.admin.model.network.response.AdminUserApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserApiLogicService extends BaseService<AdminUserApiRequest, AdminUserApiResponse, AdminUser> {

    @Override
    public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest adminUserApiRequest = request.getData();

        AdminUser adminUser = AdminUser.builder()
                .role(adminUserApiRequest.getRole())
                .status(adminUserApiRequest.getStatus())
                .password(adminUserApiRequest.getPassword())
                .account(adminUserApiRequest.getAccount())
                .lastLoginAt(adminUserApiRequest.getLastLoginAt())
                .LoginFailCount(adminUserApiRequest.getLoginFailCount())
                .passwordUpdatedAt(adminUserApiRequest.getPasswordUpdatedAt())
                .registeredAt(adminUserApiRequest.getRegisteredAt())
                .unregisteredAt(adminUserApiRequest.getUnregisteredAt())
                .build();

        AdminUser newAdminUser = baseRepository.save(adminUser);

        return Header.OK(response(newAdminUser));
    }

    @Override
    public Header<AdminUserApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {
        AdminUserApiRequest adminUserApiRequest = request.getData();

        return baseRepository.findById(adminUserApiRequest.getId())
                .map(adminUser -> {
                    adminUser
                            .setAccount(adminUserApiRequest.getAccount())
                            .setLastLoginAt(adminUserApiRequest.getLastLoginAt())
                            .setStatus(adminUserApiRequest.getStatus())
                            .setLoginFailCount(adminUserApiRequest.getLoginFailCount())
                            .setPassword(adminUserApiRequest.getPassword())
                            .setPasswordUpdatedAt(adminUserApiRequest.getPasswordUpdatedAt())
                            .setRegisteredAt(adminUserApiRequest.getRegisteredAt())
                            .setRole(adminUserApiRequest.getRole())
                            .setUnregisteredAt(adminUserApiRequest.getUnregisteredAt());

                    return adminUser;
                })
                .map(adminUser -> baseRepository.save(adminUser))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(deleteAdminUser -> {
                    baseRepository.delete(deleteAdminUser);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public AdminUserApiResponse response(AdminUser adminUser) {
        AdminUserApiResponse adminUserApiResponse = AdminUserApiResponse.builder()
                .id(adminUser.getId())
                .account(adminUser.getAccount())
                .lastLoginAt(adminUser.getLastLoginAt())
                .loginFailCount(adminUser.getLoginFailCount())
                .password(adminUser.getPassword())
                .passwordUpdatedAt(adminUser.getPasswordUpdatedAt())
                .role(adminUser.getRole())
                .status(adminUser.getStatus())
                .registeredAt(adminUser.getRegisteredAt())
                .unregisteredAt(adminUser.getUnregisteredAt())
                .build();

        return adminUserApiResponse;
    }

    @Override
    public Header<List<AdminUserApiResponse>> search(Pageable pageable) {
        Page<AdminUser> adminUsers = baseRepository.findAll(pageable);

        List<AdminUserApiResponse> adminUserApiResponseList = adminUsers.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(adminUserApiResponseList);
    }
}
