package com.cocktailpick.back.common;

import java.util.Map;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EntityMapper<K, V> {
	private final Map<K, V> map;

	public void put(K key, V value) {
		map.put(key, value);
	}

	public V get(K key) {
		V value = map.get(key);

		if (value == null) {
			throw new RuntimeException();
		}
		return value;
	}
}
