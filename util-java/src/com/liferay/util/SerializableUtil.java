/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util;

import com.liferay.portal.kernel.io.ProtectedObjectInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.ProtectedClassLoaderObjectInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Alexander Chow
 * @author Igor Spasic
 */
public class SerializableUtil {

	public static Object clone(Object object) {
		Class<?> clazz = object.getClass();

		return deserialize(serialize(object), clazz.getClassLoader());
	}

	public static Object deserialize(byte[] bytes) {
		try (ObjectInputStream objectInputStream =
				new ProtectedObjectInputStream(
					new UnsyncByteArrayInputStream(bytes))) {

			return objectInputStream.readObject();
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(classNotFoundException);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static Object deserialize(byte[] bytes, ClassLoader classLoader) {
		try (ObjectInputStream objectInputStream =
				new ProtectedClassLoaderObjectInputStream(
					new UnsyncByteArrayInputStream(bytes), classLoader)) {

			return objectInputStream.readObject();
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new RuntimeException(classNotFoundException);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	public static byte[] serialize(Object object) {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream)) {

			objectOutputStream.writeObject(object);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}

		return unsyncByteArrayOutputStream.toByteArray();
	}

}