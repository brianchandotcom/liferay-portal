/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.util;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.reflect.Field;

import java.util.function.Function;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalUtil {

	public static <T> ThreadLocal<T> create(
		Class<?> declaringClass, String fieldName,
		Function<String, ThreadLocal<T>> function) {

		ClassLoader shieldedContainerClassLoader =
			ClassLoaderPool.getClassLoader("ShieldedContainerClassLoader");

		if (declaringClass.getClassLoader() == shieldedContainerClassLoader) {
			return function.apply(declaringClass.getName() + "." + fieldName);
		}

		try {
			Class<?> portalDeclaringClass =
				shieldedContainerClassLoader.loadClass(
					declaringClass.getName());

			Field field = portalDeclaringClass.getDeclaredField(fieldName);

			field.setAccessible(true);

			return (ThreadLocal<T>)field.get(null);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			return ReflectionUtil.throwException(reflectiveOperationException);
		}
	}

}