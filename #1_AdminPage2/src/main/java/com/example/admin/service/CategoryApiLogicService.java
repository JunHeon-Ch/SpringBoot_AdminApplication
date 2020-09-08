package com.example.admin.service;

import com.example.admin.model.entity.Category;
import com.example.admin.model.network.Header;
import com.example.admin.model.network.request.CategoryApiRequest;
import com.example.admin.model.network.response.CategoryApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryApiLogicService extends BaseService<CategoryApiRequest, CategoryApiResponse, Category> {

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {
        CategoryApiRequest categoryApiRequest = request.getData();

        Category category = Category.builder()
                .title(categoryApiRequest.getTitle())
                .type(categoryApiRequest.getType())
                .build();

        Category newCategory = baseRepository.save(category);

        return Header.OK(response(newCategory));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {
        CategoryApiRequest categoryApiRequest = request.getData();

        return baseRepository.findById(categoryApiRequest.getId())
                .map(category -> {
                    category
                            .setTitle(categoryApiRequest.getTitle())
                            .setType(categoryApiRequest.getType());

                    return category;
                })
                .map(category -> baseRepository.save(category))
                .map(this::response)
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id)
                .map(deleteCategory -> {
                    baseRepository.delete(deleteCategory);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public CategoryApiResponse response(Category category) {
        CategoryApiResponse categoryApiResponse = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .type(category.getType())
                .build();

        return categoryApiResponse;
    }

    @Override
    public Header<List<CategoryApiResponse>> search(Pageable pageable) {
        Page<Category> categories = baseRepository.findAll(pageable);

        List<CategoryApiResponse> categoryApiResponseList = categories.stream()
                .map(this::response)
                .collect(Collectors.toList());

        return Header.OK(categoryApiResponseList);
    }
}
