package com.cocktailpick.common.util;

import java.util.Map;

import com.cocktailpick.common.exceptions.EntityNotFoundException;
import com.cocktailpick.common.exceptions.ErrorCode;

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
			throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND);
		}
		return value;
	}
}
