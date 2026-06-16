/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

/**
 * @author Shinn Lok
 */
public class FaroCache implements Cache {

	public FaroCache() {
		_store = new LinkedHashMap<Object, ValueWrapper>() {
		};
	}

	@Override
	public synchronized void clear() {
		_store.clear();
	}

	@Override
	public synchronized void evict(Object key) {
		_store.remove(key);
	}

	@Override
	public synchronized ValueWrapper get(Object key) {
		return _store.get(key);
	}

	@Override
	public <T> T get(Object key, Callable<T> callable) {
		return null;
	}

	@Override
	public <T> T get(Object key, Class<T> type) {
		return null;
	}

	@Override
	public String getName() {
		Class<? extends FaroCache> clazz = getClass();

		return clazz.getName();
	}

	@Override
	public Map<Object, ValueWrapper> getNativeCache() {
		return _store;
	}

	@Override
	public synchronized void put(Object key, Object value) {
		_store.put(key, new SimpleValueWrapper(value));
	}

	@Override
	public synchronized ValueWrapper putIfAbsent(Object key, Object value) {
		return _store.putIfAbsent(key, new SimpleValueWrapper(value));
	}

	private final LinkedHashMap<Object, ValueWrapper> _store;

}