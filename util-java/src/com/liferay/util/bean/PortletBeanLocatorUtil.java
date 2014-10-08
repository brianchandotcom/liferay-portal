/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.util.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletBeanLocatorUtil {

	public static BeanLocator getBeanLocator() {
		return _beanLocator;
	}

	public static Object locate(String name) throws BeanLocatorException {
		if (_beanLocator == null) {
			_log.error("BeanLocator is null");

			throw new BeanLocatorException("BeanLocator has not been set");
		}
		else {
			return _beanLocator.locate(name);
		}
	}

	public static void setBeanLocator(BeanLocator beanLocator) {
		if (_log.isDebugEnabled()) {
			if (beanLocator != null) {
				_log.debug("Setting BeanLocator " + beanLocator.hashCode());
			}
			else {
				_log.debug("Setting BeanLocator null");
			}
		}

		_beanLocator = beanLocator;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletBeanLocatorUtil.class);

	private static BeanLocator _beanLocator;

}