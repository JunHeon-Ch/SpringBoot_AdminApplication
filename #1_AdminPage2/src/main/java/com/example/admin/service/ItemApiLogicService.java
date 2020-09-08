package com.example.admin.service;

import com.example.admin.model.entity.Item;
import com.example.admin.model.network.Header;
import com.example.admin.model.network.request.ItemApiRequest;
import com.example.admin.model.network.response.ItemApiResponse;
import com.example.admin.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        Item item = Item.builder()
                .title(itemApiRequest.getTitle())
                .price(itemApiRequest.getPrice())
                .name(itemApiRequest.getName())
                .status(itemApiRequest.getStatus())
                .brandName(itemApiRequest.getBrandName())
                .content(itemApiRequest.getContent())
                .registeredAt(itemApiRequest.getRegisteredAt())
                .unregisteredAt(itemApiRequest.getUnregisteredAt())
                .partner(partnerRepository.getOne(itemApiRequest.getPartnerId()))
                .build();

        Item newItem = baseRepository.save(item);

        return Header.OK(response(newItem));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest itemApiRequest = request.getData();

        return baseRepository.findById(itemApiRequest.getId())
                .map(item -> {
                    item
                            .setBrandName(itemApiRequest.getBrandName())
                            .setContent(itemApiRequest.getContent())
                            .setName(itemApiRequest.getName())
                            .setPrice(itemApiRequest.getPrice())
                            .setRegisteredAt(itemApiRequest.getRegisteredAt())
                            .setStatus(itemApiRequest.getStatus())
                            .setTitle(itemApiRequest.getTitle())
                            .setUnregisteredAt(itemApiRequest.getUnregisteredAt())
                            .setPartner(partnerRepository.getOne(itemApiRequest.getPartnerId()));

                    return item;
                })
                .map(item -> baseRepository.save(item))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(deleteItem -> {
                    baseRepository.delete(deleteItem);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public ItemApiResponse response(Item item) {
        ItemApiResponse itemApiResponse = ItemApiResponse.builder()
                .id(item.getId())
                .brandName(item.getBrandName())
                .content(item.getContent())
                .name(item.getName())
                .price(item.getPrice())
                .registeredAt(item.getRegisteredAt())
                .status(item.getStatus())
                .title(item.getTitle())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        return itemApiResponse;
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {
        Page<Item> items = baseRepository.findAll(pageable);

        List<ItemApiResponse> itemApiResponseList = items.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(itemApiResponseList);
    }
}
