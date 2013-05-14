/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util.bridges.jsf.common;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

/**
 * <p>
 * This class serves as a bridge between the JSF Expression Language (EL) and
 * Liferay's Language.properties resource bundle.
 * </p>
 *
 * @author Neil Griffin
 */
public class LanguageManagedBean implements Map<String, String> {

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Entry<String, String>> entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String get(Object key) {
		String value = null;

		if (key != null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();

			Locale locale = facesContext.getViewRoot().getLocale();

			if (locale == null) {
				locale = facesContext.getApplication().getDefaultLocale();
			}

			value = LanguageUtil.get(locale, key.toString());

			if (_log.isDebugEnabled()) {
				_log.debug(
					"{locale=" + locale + ", key=" + key + ", value=" + value);
			}
		}

		return value;
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}

	public String put(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> map) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<String> values() {
		throw new UnsupportedOperationException();
	}

	private static Log _log = LogFactoryUtil.getLog(LanguageManagedBean.class);

}