package com.careerly.utils;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 集合工具类。
 */
public class CollectionUtils {

    public static <E> List<E> newNonNullValueList(int size) {
        return new NonNullValueList<E>(size);
    }

    public static <E> List<E> newNonNullValueList() {
        return newNonNullValueList(16);
    }

    public static <K, V> Map<K, V> newNonNullValueMap(int size) {
        return new NonNullValueMap<K, V>(size);
    }

    public static <K, V> Map<K, V> newNonNullValueMap() {
        return newNonNullValueMap(16);
    }

    public static <K, V> Map<K, V> convertListToMap(List<V> list, Function<V, K> f) {
        if (org.springframework.util.CollectionUtils.isEmpty(list))
            return Maps.newHashMap();
        Map<K, V> m = new HashMap<K, V>((int) (list.size() / 0.75f + 1));
        for (V v : list) {
            m.put(f.apply(v), v);
        }
        return m;
    }

    private static class NonNullValueList<E> extends ArrayList<E> {
        private static final long serialVersionUID = 1L;

        public NonNullValueList(int size) {
            super(size);
        }

        public boolean add(E e) {
            if (e == null)
                return false;
            return super.add(e);
        }
    }

    private static class NonNullValueMap<K, V> extends HashMap<K, V> {

        private static final long serialVersionUID = 1L;

        public NonNullValueMap(int size) {
            super(size);
        }

        public V put(K key, V value) {
            if (value == null)
                return value;
            return super.put(key, value);
        }
    }
}
