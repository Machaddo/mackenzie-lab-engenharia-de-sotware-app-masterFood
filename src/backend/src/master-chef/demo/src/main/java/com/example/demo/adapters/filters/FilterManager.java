package com.example.demo.adapters.filters;


import org.springframework.stereotype.Service;

import java.util.List;

public interface FilterManager {
    boolean applyFilters(List<Filter> filters) throws Exception;
}
