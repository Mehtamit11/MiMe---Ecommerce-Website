package com.mime.service;

import java.util.Map;

public interface AnalyticsService {

    Map<String, Double> getSalesOverTime();

    Map<String, Integer> getTopProducts();

    Map<String, Double> getCategoryPerformance();
}
