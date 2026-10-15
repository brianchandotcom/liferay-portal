/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.http.client;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * @author Leslie Wong
 */
public class ClientHttpRequestFactoryUtilTest {

	@Before
	public void setUp() throws Exception {
		_serverSocket = new ServerSocket(0);

		Thread thread = new Thread(this::_acceptWithoutResponding);

		thread.setDaemon(true);

		thread.start();
	}

	@After
	public void tearDown() throws Exception {
		_httpComponentsClientHttpRequestFactory.destroy();

		_serverSocket.close();

		for (Socket socket : _sockets) {
			socket.close();
		}
	}

	@Test
	public void testCreateHttpComponentsClientHttpRequestFactoryWithResponseTimeout()
		throws Exception {

		ClientHttpRequest clientHttpRequest =
			_httpComponentsClientHttpRequestFactory.createRequest(
				URI.create("http://localhost:" + _serverSocket.getLocalPort()),
				HttpMethod.GET);

		long startTimeNanos = System.nanoTime();

		Assert.assertThrows(IOException.class, clientHttpRequest::execute);

		long elapsedTime = TimeUnit.NANOSECONDS.toMillis(
			System.nanoTime() - startTimeNanos);

		Assert.assertTrue(elapsedTime >= 500);
		Assert.assertTrue(elapsedTime < 1500);
	}

	private void _acceptWithoutResponding() {
		while (!_serverSocket.isClosed()) {
			try {
				Socket socket = _serverSocket.accept();

				synchronized (_sockets) {
					_sockets.add(socket);
				}
			}
			catch (IOException ioException) {
				return;
			}
		}
	}

	private final HttpComponentsClientHttpRequestFactory
		_httpComponentsClientHttpRequestFactory =
			ClientHttpRequestFactoryUtil.
				createHttpComponentsClientHttpRequestFactory(
					RandomTestUtil.randomInt(), RandomTestUtil.randomInt(),
					500);
	private ServerSocket _serverSocket;
	private final List<Socket> _sockets = new ArrayList<>();

}