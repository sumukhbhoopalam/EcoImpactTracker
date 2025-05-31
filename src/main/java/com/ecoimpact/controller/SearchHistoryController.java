package com.ecoimpact.controller;

import com.ecoimpact.model.SearchHistory;
import com.ecoimpact.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search-history")
public class SearchHistoryController {
    private final SearchHistoryService searchHistoryService;

    @Autowired
    public SearchHistoryController(SearchHistoryService searchHistoryService) {
        this.searchHistoryService = searchHistoryService;
    }

    @GetMapping
    public List<SearchHistory> getAllSearchHistory() {
        return searchHistoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchHistory> getSearchHistoryById(@PathVariable Long id) {
        return searchHistoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SearchHistory createSearchHistory(@RequestBody SearchHistory searchHistory) {
        return searchHistoryService.save(searchHistory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SearchHistory> updateSearchHistory(@PathVariable Long id, @RequestBody SearchHistory searchHistory) {
        return searchHistoryService.findById(id)
                .map(existing -> {
                    searchHistory.setSearchId(id);
                    return ResponseEntity.ok(searchHistoryService.save(searchHistory));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSearchHistory(@PathVariable Long id) {
        searchHistoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 