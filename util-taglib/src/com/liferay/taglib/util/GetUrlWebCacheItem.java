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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.webcache.WebCacheException;
import com.liferay.portal.kernel.webcache.WebCacheItem;

/**
 * @author Brian Wing Shun Chan
 */
public class GetUrlWebCacheItem implements WebCacheItem {

	public GetUrlWebCacheItem(String url, long refreshTime) {
		_url = url;
		_refreshTime = refreshTime;
	}

	@Override
	public Object convert(String key) throws WebCacheException {
		String url = _url;

		String content = null;

		try {
			content = HttpUtil.URLtoString(_url);
		}
		catch (Exception e) {
			throw new WebCacheException(url + " " + e.toString());
		}

		return content;
	}

	@Override
	public long getRefreshTime() {
		return _refreshTime;
	}

	private long _refreshTime;
	private String _url;

}