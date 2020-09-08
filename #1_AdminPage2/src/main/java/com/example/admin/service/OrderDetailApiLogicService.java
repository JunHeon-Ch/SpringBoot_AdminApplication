package com.example.admin.service;

import com.example.admin.model.entity.OrderDetail;
import com.example.admin.model.network.Header;
import com.example.admin.model.network.request.OrderDetailApiRequest;
import com.example.admin.model.network.response.OrderDetailApiResponse;
import com.example.admin.repository.ItemRepository;
import com.example.admin.repository.OrderDetailRepository;
import com.example.admin.repository.OrderGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailApiLogicService extends BaseService<OrderDetailApiRequest, OrderDetailApiResponse, OrderDetail> {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderGroupRepository orderGroupRepository;

    @Override
    public Header<OrderDetailApiResponse> create(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest orderDetailApiRequest = request.getData();

        OrderDetail orderDetail = OrderDetail.builder()
                .status(orderDetailApiRequest.getStatus())
                .arrivalDate(orderDetailApiRequest.getArrivalDate())
                .Quantity(orderDetailApiRequest.getQuantity())
                .totalPrice(orderDetailApiRequest.getTotalPrice())
                .item(itemRepository.getOne(orderDetailApiRequest.getItemId()))
                .orderGroup(orderGroupRepository.getOne(orderDetailApiRequest.getOrderGroupId()))
                .build();

        OrderDetail newOrderDetail = baseRepository.save(orderDetail);

        return Header.OK(response(newOrderDetail));
    }

    @Override
    public Header<OrderDetailApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<OrderDetailApiResponse> update(Header<OrderDetailApiRequest> request) {
        OrderDetailApiRequest orderDetailApiRequest = request.getData();

        return baseRepository.findById(orderDetailApiRequest.getId())
                .map(orderDetail -> {
                    orderDetail
                            .setArrivalDate(orderDetailApiRequest.getArrivalDate())
                            .setQuantity(orderDetailApiRequest.getQuantity())
                            .setStatus(orderDetailApiRequest.getStatus())
                            .setTotalPrice(orderDetailApiRequest.getTotalPrice())
                            .setItem(itemRepository.getOne(orderDetailApiRequest.getItemId()))
                            .setOrderGroup(orderGroupRepository.getOne(orderDetailApiRequest.getOrderGroupId()));

                    return orderDetail;
                })
                .map(orderDetail -> baseRepository.save(orderDetail))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(deleteOrderDetail -> {
                    baseRepository.delete(deleteOrderDetail);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public OrderDetailApiResponse response(OrderDetail orderDetail) {
        OrderDetailApiResponse orderDetailApiResponse = OrderDetailApiResponse.builder()
                .id(orderDetail.getId())
                .arrivalDate(orderDetail.getArrivalDate())
                .quantity(orderDetail.getQuantity())
                .status(orderDetail.getStatus())
                .totalPrice(orderDetail.getTotalPrice())
                .itemId(orderDetail.getItem().getId())
                .orderGroupId(orderDetail.getOrderGroup().getId())
                .build();

        return orderDetailApiResponse;
    }

    @Override
    public Header<List<OrderDetailApiResponse>> search(Pageable pageable) {
        Page<OrderDetail> orderDetails = baseRepository.findAll(pageable);

        List<OrderDetailApiResponse> orderDetailApiResponseList = orderDetails.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(orderDetailApiResponseList);
    }
}
