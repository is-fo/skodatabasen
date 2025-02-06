package org.example.filter;

import org.example.data.ShoeDetailed;

import java.util.List;
import java.util.stream.Collectors;

public class InStockFilter {

    public List<ShoeDetailed> filter(List<ShoeDetailed> shoes) {
        return shoes.stream()
                .filter(s -> !s.inStock().equals(0))
                .collect(Collectors.toList());
    }
}
