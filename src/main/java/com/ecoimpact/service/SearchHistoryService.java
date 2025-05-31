package com.ecoimpact.service;

import com.ecoimpact.model.SearchHistory;
import java.util.List;
import java.util.Optional;

public interface SearchHistoryService {
    List<SearchHistory> findAll();
    Optional<SearchHistory> findById(Long id);
    SearchHistory save(SearchHistory searchHistory);
    void deleteById(Long id);
} 