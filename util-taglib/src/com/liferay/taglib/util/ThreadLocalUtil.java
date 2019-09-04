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

package com.liferay.taglib.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.lang.reflect.Field;

import java.util.function.Function;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalUtil {

	public static <T> ThreadLocal<T> create(
		Class<?> declaringClass, String fieldName,
		Function<String, ThreadLocal<T>> function) {

		ClassLoader portalClassLoader = PortalClassLoaderUtil.getClassLoader();

		if (declaringClass.getClassLoader() == portalClassLoader) {
			return function.apply(declaringClass.getName() + "." + fieldName);
		}

		try {
			Class<?> portalDeclaringClass = portalClassLoader.loadClass(
				declaringClass.getName());

			Field field = portalDeclaringClass.getDeclaredField(fieldName);

			field.setAccessible(true);

			return (ThreadLocal<T>)field.get(null);
		}
		catch (ReflectiveOperationException roe) {
			return ReflectionUtil.throwException(roe);
		}
	}

}