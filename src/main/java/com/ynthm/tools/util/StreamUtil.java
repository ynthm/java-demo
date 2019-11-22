package com.ynthm.tools.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class StreamUtil {

    private StreamUtil() {
    }

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValueReversed(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByValue()
                        .reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    public <K extends Comparable<? super K>, V> Map<K, V> sortByKeyReversed(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<K, V>comparingByKey()
                        .reversed()).forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
