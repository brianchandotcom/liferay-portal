/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.support.tomcat.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.apache.catalina.Manager;
import org.apache.catalina.ha.session.DeltaRequest;
import org.apache.catalina.ha.session.DeltaSession;

/**
 * @author Shuyang Zhou
 */
public class LiferayDeltaSession extends DeltaSession {

	public LiferayDeltaSession() {
	}

	public LiferayDeltaSession(Manager manager) {
		super(manager);
	}

	@Override
	public void readObjectData(ObjectInputStream objectInputStream)
		throws ClassNotFoundException, IOException {

		int size = objectInputStream.readInt();

		byte[] data = new byte[size];

		objectInputStream.readFully(data);

		try (ObjectInput liferayObjectInput =
				LiferayDeltaManager.toObjectInputStream(
					new ByteArrayInputStream(data))) {

			super.doReadObject((ObjectInputStream)liferayObjectInput);
		}
	}

	@Override
	public void writeObjectData(ObjectOutputStream objectOutputStream)
		throws IOException {

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try (ObjectOutput liferayObjectOutput =
				LiferayDeltaManager.toObjectOutputStream(
					byteArrayOutputStream)) {

			super.doWriteObject((ObjectOutputStream)liferayObjectOutput);
		}

		byte[] data = byteArrayOutputStream.toByteArray();

		objectOutputStream.writeInt(data.length);
		objectOutputStream.write(data);
	}

	@Override
	protected DeltaRequest createRequest(
		String sessionId, boolean recordAllActions) {

		return new LiferayDeltaRequest(sessionId, recordAllActions);
	}

}