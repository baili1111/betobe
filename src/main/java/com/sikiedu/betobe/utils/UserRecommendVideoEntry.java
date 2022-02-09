package com.sikiedu.betobe.utils;

/**
 * @author zhu
 * @date 2021/5/24 12:54:11
 * @description
 */
public class UserRecommendVideoEntry<K, V> {

	private K key;
	private V value;


	public UserRecommendVideoEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
}
