package com.dyman.im.core;

/**
 * @author dyman
 * @describe
 * @date 2020/4/14
 */
public interface DMap<K, V> {

    int size();

    void put(K key, V value);

    V get(K key);
}
