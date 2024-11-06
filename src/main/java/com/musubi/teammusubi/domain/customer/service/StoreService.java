package com.musubi.teammusubi.domain.customer.service;

import com.musubi.teammusubi.common.entity.Store;
import com.musubi.teammusubi.common.enums.Category;
import com.musubi.teammusubi.domain.customer.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public List<Store> readAllStores(int page, int size, Optional<String> category, Optional<String> search) {

        if(category.isPresent() && Arrays.stream(Category.values()).noneMatch(c -> c.toString().equals(category.get()))) {
            throw new RuntimeException("(Dummy exception)Category not found");
        }

        Category categoryEnum = null;
        if (category.isPresent()) {
            categoryEnum = Category.valueOf(category.get());
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Store> stores = storeRepository.findAllByParams(categoryEnum, search.orElse(null), pageable);
        return stores.stream().toList();
    }

    public Store readStore(long id) {
        return storeRepository.findByIdSafeWithMenus(id);
    }

}
