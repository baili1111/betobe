package com.sikiedu.betobe.utils;

import java.util.Objects;

/**
 * @author zhu
 * @date 2021/5/22 16:25:10
 * @description
 */
public class TFEntry<K extends Object, V extends Double> {

	private K key;
	private V value;

	@Override
	public boolean equals(Object obj) {
		return this.key.equals(((TFEntry)obj).getKey());
	}

	public TFEntry(K key, V value) {
		super();
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
