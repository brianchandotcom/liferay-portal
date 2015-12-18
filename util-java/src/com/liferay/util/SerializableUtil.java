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

package com.liferay.util;

import com.liferay.portal.kernel.io.ProtectedObjectInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.ProtectedClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Alexander Chow
 * @author Igor Spasic
 */
public class SerializableUtil {

	public static Object clone(Object object) {
		return deserialize(serialize(object));
	}

	public static Object deserialize(byte[] bytes) {
		ObjectInputStream objectInputStream = null;

		try {
			objectInputStream = new ProtectedObjectInputStream(
				new UnsyncByteArrayInputStream(bytes));

			return objectInputStream.readObject();
		}
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		finally {
			StreamUtil.cleanUp(objectInputStream);
		}
	}

	public static Object deserialize(byte[] bytes, ClassLoader classLoader) {
		ObjectInputStream objectInputStream = null;

		try {
			objectInputStream = new ProtectedClassLoaderObjectInputStream(
				new UnsyncByteArrayInputStream(bytes), classLoader);

			return objectInputStream.readObject();
		}
		catch (ClassNotFoundException cnfe) {
			throw new RuntimeException(cnfe);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		finally {
			StreamUtil.cleanUp(objectInputStream);
		}
	}

	public static byte[] serialize(Object object) {
		ObjectOutputStream objectOutputStream = null;

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try {
			objectOutputStream = new ObjectOutputStream(
				unsyncByteArrayOutputStream);

			objectOutputStream.writeObject(object);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		finally {
			StreamUtil.cleanUp(objectOutputStream);
		}

		return unsyncByteArrayOutputStream.toByteArray();
	}

}