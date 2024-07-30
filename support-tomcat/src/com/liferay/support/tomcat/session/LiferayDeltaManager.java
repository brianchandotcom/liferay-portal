/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.support.tomcat.session;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.apache.catalina.Session;
import org.apache.catalina.ha.ClusterManager;
import org.apache.catalina.ha.session.DeltaManager;
import org.apache.catalina.ha.session.DeltaSession;

/**
 * @author Shuyang Zhou
 */
public class LiferayDeltaManager extends DeltaManager {

	public static void init(
		Function<InputStream, ObjectInputStream> objectInputStreamFunction,
		Function<OutputStream, ObjectOutputStream> objectOutputStreamFunction) {

		synchronized (LiferayDeltaManager.class) {
			_objectInputStreamFunctionFuture.complete(
				objectInputStreamFunction);
			_objectOutputStreamFunctionFuture.complete(
				objectOutputStreamFunction);

			for (Runnable runnable : _runnables) {
				runnable.run();
			}

			_runnables.clear();
		}
	}

	public static ObjectInputStream toObjectInputStream(
		InputStream inputStream) {

		try {
			Function<InputStream, ObjectInputStream> function =
				_objectInputStreamFunctionFuture.get();

			return function.apply(inputStream);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static ObjectOutputStream toObjectOutputStream(
		OutputStream outputStream) {

		try {
			Function<OutputStream, ObjectOutputStream> function =
				_objectOutputStreamFunctionFuture.get();

			return function.apply(outputStream);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public ClusterManager cloneFromTemplate() {
		LiferayDeltaManager liferayDeltaManager = new LiferayDeltaManager();

		clone(liferayDeltaManager);

		liferayDeltaManager.setExpireSessionsOnShutdown(
			isExpireSessionsOnShutdown());
		liferayDeltaManager.setNotifyContainerListenersOnReplication(
			isNotifyContainerListenersOnReplication());
		liferayDeltaManager.setNotifySessionListenersOnReplication(
			isNotifyContainerListenersOnReplication());
		liferayDeltaManager.setSendAllSessions(isSendAllSessions());
		liferayDeltaManager.setSendAllSessionsSize(getSendAllSessionsSize());
		liferayDeltaManager.setSendAllSessionsWaitTime(
			getSendAllSessionsWaitTime());
		liferayDeltaManager.setStateTimestampDrop(isStateTimestampDrop());
		liferayDeltaManager.setStateTransferTimeout(getStateTransferTimeout());

		return liferayDeltaManager;
	}

	@Override
	public Session createEmptySession() {
		return new LiferayDeltaSession(this);
	}

	@Override
	public void getAllClusterSessions() {
		synchronized (LiferayDeltaManager.class) {
			if (_objectInputStreamFunctionFuture.isDone()) {
				super.getAllClusterSessions();
			}
			else {
				_runnables.add(super::getAllClusterSessions);
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	protected DeltaSession getNewDeltaSession() {
		return new LiferayDeltaSession(this);
	}

	private static final CompletableFuture
		<Function<InputStream, ObjectInputStream>>
			_objectInputStreamFunctionFuture = new CompletableFuture<>();
	private static final CompletableFuture
		<Function<OutputStream, ObjectOutputStream>>
			_objectOutputStreamFunctionFuture = new CompletableFuture<>();
	private static final List<Runnable> _runnables = new ArrayList<>();

}