/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.resource.v1_0.test.util;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

import java.nio.charset.StandardCharsets;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Leslie Wong
 */
public class ContactsEngineHttpServer implements AutoCloseable {

	public ContactsEngineHttpServer(ContactsEngineClient contactsEngineClient)
		throws IOException {

		_contactsEngineClient = contactsEngineClient;

		_httpServer = HttpServer.create(
			new InetSocketAddress("127.0.0.1", 0), 0);

		InetSocketAddress inetSocketAddress = _httpServer.getAddress();

		_url = "http://127.0.0.1:" + inetSocketAddress.getPort();

		_httpServer.createContext(
			"/",
			httpExchange -> {
				URI uri = httpExchange.getRequestURI();

				String path = uri.getPath();

				if (Objects.equals(path, "/")) {
					_writeJSON(
						HttpURLConnection.HTTP_OK, _getLinksJSON(),
						httpExchange);

					return;
				}

				_location = uri.toString();

				Response response = _responses.get(path);

				if (response == null) {
					_writeJSON(
						HttpURLConnection.HTTP_NOT_FOUND, "{}", httpExchange);
				}
				else {
					_writeJSON(
						response._statusCode, response._json, httpExchange);
				}
			});

		_httpServer.start();

		_contactsEngineClient.setEngineURL(_url);
	}

	public void addJSON(String path, int statusCode, String json) {
		_responses.put(path, new Response(json, statusCode));
	}

	public void addJSON(String path, String json) {
		addJSON(path, HttpURLConnection.HTTP_OK, json);
	}

	public void addLink(String rel, String path) {
		_links.put(rel, path);
	}

	@Override
	public void close() {
		_contactsEngineClient.setEngineURL(null);

		_httpServer.stop(0);
	}

	public String getLocation() {
		return _location;
	}

	private String _getLinksJSON() {
		JSONObject linksJSONObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, String> entry : _links.entrySet()) {
			linksJSONObject.put(
				entry.getKey(), JSONUtil.put("href", _url + entry.getValue()));
		}

		return JSONUtil.put(
			"_links", linksJSONObject
		).toString();
	}

	private void _writeJSON(
			int statusCode, String json, HttpExchange httpExchange)
		throws IOException {

		Headers headers = httpExchange.getResponseHeaders();

		headers.set("Content-Type", "application/hal+json");

		byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

		httpExchange.sendResponseHeaders(statusCode, bytes.length);

		try (OutputStream outputStream = httpExchange.getResponseBody()) {
			outputStream.write(bytes);
		}
	}

	private final ContactsEngineClient _contactsEngineClient;
	private final HttpServer _httpServer;
	private final Map<String, String> _links = new ConcurrentHashMap<>();
	private volatile String _location;
	private final Map<String, Response> _responses = new ConcurrentHashMap<>();
	private final String _url;

	private static class Response {

		private Response(String json, int statusCode) {
			_json = json;
			_statusCode = statusCode;
		}

		private final String _json;
		private final int _statusCode;

	}

}