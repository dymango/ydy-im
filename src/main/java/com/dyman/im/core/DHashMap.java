package com.dyman.im.core;

import java.util.HashMap;

/**
 * @author dyman
 * @describe 数组+链表实现hashmap
 * @date 2020/4/14
 */
public class DHashMap<K, V> implements DMap<K, V> {

    Node<K,V>[] table;
    int threshold;
    int capacity;
    float loadFactor;
    int size;

    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public DHashMap() {
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        this.size = 0;
        table = new Node[DEFAULT_INITIAL_CAPACITY];
    }

    public DHashMap(int capacity, float loadFactor) {
        this.capacity = tableSizeFor(capacity);
        this.loadFactor = loadFactor;
        this.threshold = (int) (this.capacity * loadFactor);
        this.size = 0;
    }

    public static void main(String[] args) {
        int i = tableSizeFor(32);
        int j =1;
    }

    /**
     * 格式化数组最大长度，取最近的2的整数次幂，例如 tableSizeFor(10) = 16
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    static class Node<K, V> {

        final K key;
        V value;
        final int hash;
        Node<K,V> next;

        Node(K key, V value, int hash, Node next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public int getHash() {
            return hash;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public V setValue(V value) {
            return this.value = value;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }
    }

    @Override
    public int size() {
//        int size = 0;
//        for(int i = 0; i < table.length; i++) {
//            Node<K,V> node = table[i];
//            if(node == null) continue;
//            while(node != null) {
//                size++;
//                node = node.next;
//            }
//        }
        return size;
    }

    @Override
    public void put(K key, V value) {
        int cap = table.length;
        int hash = hash(key);
        int position = hash & (cap - 1);
        Node<K,V> tabNode = table[position];
        if(tabNode == null) {
            table[position] = new Node<>(key, value, hash, null);
        } else  {
            boolean replace = false;
            Node<K,V> end = tabNode;
            do {

                if(tabNode.key.equals(key)) {
                    tabNode.value = value;
                    replace = true;
                    break;
                }

                end = tabNode;
            } while ((tabNode = tabNode.next) != null);

            if(!replace) {
                end.setNext(new Node(key, value, hash, null));
                int i = 1;
            }
        }

        if(++size >= threshold) {
            resize();
        }
    }

    private Node<K, V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = table.length;
        int oldThreshold = threshold;
        int newCap;
        int newThreshold = threshold << 1;
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
            newThreshold = oldThreshold << 1;
            threshold = newThreshold;
            capacity = newCap;
        }

        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        for(int i = 0; i < oldCap; i ++) {
            Node<K,V> e;
            if((e = oldTab[i]) != null) {
                oldTab[i] = null;
                if(e.next == null) {
                    //单节点直接存
                    newTab[e.hash & (newCap - 1)] = e;
                } else {
                    //多节点链表要拆开存
                    Node<K,V> oldHead = null,oldTail = null;
                    Node<K,V> newHead = null,newTail = null;
                    Node<K,V> next;
                    do {
                        if((e.hash & oldCap) == 0) {
                            if(oldHead == null) {
                                oldHead = e;
                            } else {
                                oldTail.next = e;
                            }

                            oldTail = e;
                        } else {
                            if(newHead == null) {
                                newHead = e;
                            } else {
                                newTail.next = e;
                            }

                            newTail = e;
                        }

                        next = e.next;
                    } while((e = next) != null);

                    if(oldTail != null) {
                        oldTail.next = null;
                        newTab[i] = oldHead;
                    }

                    if(newTail != null) {
                        newTail.next = null;
                        newTab[i + oldCap] = newHead;
                    }
                }
            }
        }
        return oldTab;
    }

    @Override
    public V get(Object key) {
        int cap = table.length;
        int hash = hash(key);
        int position = hash % cap;
        Node<K, V> tabNode = table[position];
        if(tabNode == null) {
            return null;
        } else if(tabNode.key.equals(key)) {
            return tabNode.value;
        } else {
            do {
                if(tabNode.key.equals(key)) {
                    return tabNode.value;
                }

            } while ((tabNode = tabNode.next) != null);
        }

        return null;
    }

    int hash(Object key) {
        int h;
        return key == null ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
