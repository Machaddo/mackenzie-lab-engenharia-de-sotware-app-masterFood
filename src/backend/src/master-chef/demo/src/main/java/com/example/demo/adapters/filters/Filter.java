package com.example.demo.adapters.filters;

import java.util.Map;

public interface Filter {
    
    Map<String, Object> getSchema();
    String getFilter();

}
