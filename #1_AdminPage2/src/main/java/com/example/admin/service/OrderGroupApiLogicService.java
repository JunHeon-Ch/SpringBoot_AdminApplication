package com.example.admin.service;

import com.example.admin.model.entity.OrderGroup;
import com.example.admin.model.network.Header;
import com.example.admin.model.network.request.OrderGroupApiRequest;
import com.example.admin.model.network.response.OrderGroupApiResponse;
import com.example.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest orderGroupApiRequest = request.getData();

        OrderGroup orderGroup = OrderGroup.builder()
                .status(orderGroupApiRequest.getStatus())
                .orderType(orderGroupApiRequest.getOrderType())
                .revAddress(orderGroupApiRequest.getRevAddress())
                .revName(orderGroupApiRequest.getRevName())
                .paymentType(orderGroupApiRequest.getPaymentType())
                .totalPrice(orderGroupApiRequest.getTotalPrice())
                .totalQuantity(orderGroupApiRequest.getTotalQuantity())
                .orderAt(orderGroupApiRequest.getOrderAt())
                .arrivalDate(orderGroupApiRequest.getArrivalDate())
                .user(userRepository.getOne(orderGroupApiRequest.getUserId()))
                .build();

        OrderGroup newOrderGroup = baseRepository.save(orderGroup);

        return Header.OK(response(newOrderGroup));
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest orderGroupApiRequest = request.getData();

        return baseRepository.findById(orderGroupApiRequest.getId())
                .map(orderGroup -> {
                    orderGroup
                            .setStatus(orderGroupApiRequest.getStatus())
                            .setOrderType(orderGroupApiRequest.getOrderType())
                            .setRevAddress(orderGroupApiRequest.getRevAddress())
                            .setRevName(orderGroupApiRequest.getRevName())
                            .setTotalPrice(orderGroupApiRequest.getTotalPrice())
                            .setTotalQuantity(orderGroupApiRequest.getTotalQuantity())
                            .setOrderAt(orderGroupApiRequest.getOrderAt())
                            .setArrivalDate(orderGroupApiRequest.getArrivalDate())
                            .setUser(userRepository.getOne(orderGroupApiRequest.getUserId()));

                    return orderGroup;
                })
                .map(orderGroup -> baseRepository.save(orderGroup))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(deleteOrderGroup -> {
                    baseRepository.delete(deleteOrderGroup);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public OrderGroupApiResponse response(OrderGroup orderGroup) {
        OrderGroupApiResponse orderGroupApiResponse = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .orderAt(orderGroup.getOrderAt())
                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId())
                .build();

        return orderGroupApiResponse;
    }

    @Override
    public Header<List<OrderGroupApiResponse>> search(Pageable pageable) {
        Page<OrderGroup> orderGroups = baseRepository.findAll(pageable);

        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroups.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(orderGroupApiResponseList);
    }
}
