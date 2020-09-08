package com.example.admin.repository;

import com.example.admin.model.entity.Category;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {
        Category category = Category.builder()
                .type("전자제품")
                .title("노트북")
                .build();

        Category newCategory = categoryRepository.save(category);

        Assert.assertNotNull(newCategory);
        System.out.println(newCategory);
    }

    @Test
    public void read() {
        Optional<Category> selectCategory = categoryRepository.findById(1L);

        Assert.assertNotNull(selectCategory);
        System.out.println(selectCategory);
    }
}
