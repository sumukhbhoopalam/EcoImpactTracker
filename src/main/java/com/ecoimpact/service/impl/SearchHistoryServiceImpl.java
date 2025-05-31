package com.ecoimpact.service.impl;

import com.ecoimpact.model.SearchHistory;
import com.ecoimpact.repository.SearchHistoryRepository;
import com.ecoimpact.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    @Autowired
    public SearchHistoryServiceImpl(SearchHistoryRepository searchHistoryRepository) {
        this.searchHistoryRepository = searchHistoryRepository;
    }

    @Override
    public List<SearchHistory> findAll() {
        return searchHistoryRepository.findAll();
    }

    @Override
    public Optional<SearchHistory> findById(Long id) {
        return searchHistoryRepository.findById(id);
    }

    @Override
    public SearchHistory save(SearchHistory searchHistory) {
        return searchHistoryRepository.save(searchHistory);
    }

    @Override
    public void deleteById(Long id) {
        searchHistoryRepository.deleteById(id);
    }
} 