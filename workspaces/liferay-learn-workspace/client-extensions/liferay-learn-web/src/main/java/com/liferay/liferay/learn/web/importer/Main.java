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

package com.liferay.liferay.learn.web.importer;

import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.client.resource.v2_0.DataDefinitionResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.net.URI;
import java.net.URL;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;

/**
 * @author Allen Ziegenfus
 */
public class Main {

	public static void main(String[] arguments) throws Exception {
		System.out.println("Starting import");

		Main main = new Main(
			System.getenv("LIFERAY_OAUTH_CLIENT_ID"),
			System.getenv("LIFERAY_OAUTH_CLIENT_SECRET"),
			new URL(System.getenv("LIFERAY_URL")));

		main.uploadToLiferay(
			GetterUtil.getLong(System.getenv("LIFERAY_GROUP_ID")));

		System.out.println("Ending import");
	}

	public Main(
			String liferayOAuthClientId, String liferayOAuthClientSecret,
			URL liferayURL)
		throws Exception {

		_liferayOAuthClientId = liferayOAuthClientId;
		_liferayOAuthClientSecret = liferayOAuthClientSecret;

		_liferayURL = liferayURL;

		System.out.println("Importing into Liferay instance at " + _liferayURL);

		_getTargetOAuthAuthorization();
		_initResourceBuilders();
	}

	public JSONObject addDDMTemplate(
			long groupId, long classNameId, long resourceClassNameId,
			long classPK, String name, String templateKey, String script,
			boolean cacheable)
		throws Exception {

		HttpPost httpPost = new HttpPost(
			_liferayURL + "/api/jsonws/ddm.ddmtemplate");

		httpPost.setHeader("Authorization", _getTargetOAuthAuthorization());
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("User-Agent", Main.class.getName());

		JSONObject dataJSONObject = new JSONObject();

		dataJSONObject.put("id", 123);
		dataJSONObject.put("jsonrpc", "2.0");
		dataJSONObject.put("method", "add-template");
		dataJSONObject.put(
			"params",
			HashMapBuilder.<String, Object>put(
				"cacheable", cacheable
			).put(
				"classNameId", classNameId
			).put(
				"classPK", classPK
			).put(
				"descriptionMap",
				new JSONObject(
					HashMapBuilder.put(
						"en-US", name
					).build()
				).toString()
			).put(
				"groupId", groupId
			).put(
				"language", "ftl"
			).put(
				"mode", StringPool.BLANK
			).put(
				"nameMap",
				new JSONObject(
					HashMapBuilder.put(
						"en-US", name
					).build()
				).toString()
			).put(
				"resourceClassNameId", resourceClassNameId
			).put(
				"script", script
			).put(
				"smallImage", false
			).put(
				"smallImageFile", StringPool.BLANK
			).put(
				"smallImageURL", StringPool.BLANK
			).put(
				"templateKey", String.valueOf(templateKey)
			).put(
				"type", "display"
			).build());

		httpPost.setEntity(new StringEntity(dataJSONObject.toString()));

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject responseJSONObject = new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));

				if (responseJSONObject.has("error")) {
					throw new Exception(
						"Unable to add DDM template: " +
							responseJSONObject.get("error"));
				}

				return responseJSONObject;
			}

			throw new Exception(
				"Unable to add DDM template: " + statusLine.getStatusCode() +
					statusLine.getReasonPhrase());
		}
	}

	public JSONObject fetchStructure(
			long groupId, long classNameId, String structureKey)
		throws Exception {

		HttpGet httpGet = new HttpGet(
			_liferayURL + "/api/jsonws/ddm.ddmstructure/fetch-structure");

		httpGet.setHeader("Authorization", _getTargetOAuthAuthorization());
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		httpGet.setHeader("User-Agent", Main.class.getName());

		URI uri = new URIBuilder(
			httpGet.getURI()
		).addParameter(
			"groupId", String.valueOf(groupId)
		).addParameter(
			"classNameId", String.valueOf(classNameId)
		).addParameter(
			"structureKey", structureKey
		).build();

		httpGet.setURI(uri);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpGet);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));
			}

			throw new Exception(
				"Unable to fetch DDM structure: " + statusLine.getStatusCode() +
					statusLine.getReasonPhrase());
		}
	}

	public JSONObject fetchTemplate(
			long groupId, long classNameId, String templateKey)
		throws Exception {

		HttpGet httpGet = new HttpGet(
			_liferayURL + "/api/jsonws/ddm.ddmtemplate/fetch-template");

		httpGet.setHeader("Authorization", _getTargetOAuthAuthorization());
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		httpGet.setHeader("User-Agent", Main.class.getName());

		URI uri = new URIBuilder(
			httpGet.getURI()
		).addParameter(
			"groupId", String.valueOf(groupId)
		).addParameter(
			"classNameId", String.valueOf(classNameId)
		).addParameter(
			"templateKey", templateKey
		).build();

		httpGet.setURI(uri);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpGet);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));
			}

			throw new Exception(
				"Unable to fetch DDM template: " + statusLine.getStatusCode() +
					statusLine.getReasonPhrase());
		}
	}

	public long getClassNameId(String className) throws Exception {
		HttpGet httpGet = new HttpGet(
			_liferayURL + "/api/jsonws/classname/fetch-class-name");

		httpGet.setHeader("Authorization", _getTargetOAuthAuthorization());
		httpGet.setHeader("Accept", "application/json");
		httpGet.setHeader("Content-Type", "application/json");
		httpGet.setHeader("User-Agent", Main.class.getName());

		URI uri = new URIBuilder(
			httpGet.getURI()
		).addParameter(
			"value", className
		).build();

		httpGet.setURI(uri);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpGet);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject classNameJSONObject = new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));

				return classNameJSONObject.getLong("classNameId");
			}

			throw new Exception(
				"Unable to fetch class name: " + statusLine.getStatusCode() +
					statusLine.getReasonPhrase());
		}
	}

	public JSONObject updateDDMTemplate(
			long classPK, String name, long templateId, String script,
			boolean cacheable)
		throws Exception {

		HttpPost httpPost = new HttpPost(
			_liferayURL + "/api/jsonws/ddm.ddmtemplate");

		httpPost.setHeader("Authorization", _getTargetOAuthAuthorization());
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("User-Agent", Main.class.getName());

		JSONObject dataJSONObject = new JSONObject();

		dataJSONObject.put("id", 123);
		dataJSONObject.put("jsonrpc", "2.0");
		dataJSONObject.put("method", "update-template");
		dataJSONObject.put(
			"params",
			HashMapBuilder.<String, Object>put(
				"cacheable", cacheable
			).put(
				"classPK", classPK
			).put(
				"descriptionMap",
				new JSONObject(
					HashMapBuilder.put(
						"en-US", name
					).build()
				).toString()
			).put(
				"language", "ftl"
			).put(
				"mode", StringPool.BLANK
			).put(
				"nameMap",
				new JSONObject(
					HashMapBuilder.put(
						"en-US", name
					).build()
				).toString()
			).put(
				"script", script
			).put(
				"templateId", templateId
			).put(
				"type", "display"
			).build());

		httpPost.setEntity(new StringEntity(dataJSONObject.toString()));

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				JSONObject responseJSONObject = new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));

				if (responseJSONObject.has("error")) {
					throw new Exception(
						"UNable to update DDM template: " +
							responseJSONObject.getString("error"));
				}

				return responseJSONObject;
			}

			throw new Exception(
				"Unable to update DDM template: " + statusLine.getStatusCode() +
					statusLine.getReasonPhrase());
		}
	}

	public void uploadToLiferay(long groupId) throws Exception {
		if (groupId == 0) {
			System.out.println("A valid groupId must be specified.");
			System.exit(1);
		}

		_addOrUpdateDDMStructures(groupId);
		_addOrUpdateDDMTemplates(groupId);
	}

	private void _addOrUpdateDDMStructures(long groupId) throws Exception {
		System.out.println("Checking for DDM structures");

		List<Path> paths;

		try (Stream<Path> walk = Files.walk(
				Path.of("/resources/ddm-structures"))) {

			paths = walk.filter(
				Files::isRegularFile
			).collect(
				Collectors.toList()
			);
		}

		for (Path path : paths) {
			System.out.println("Found DDM structure " + path);

			JSONObject jsonObject = new JSONObject(
				new String(Files.readAllBytes(path)));

			String contentType = jsonObject.getString("contentType");
			String dataDefinitionKey = jsonObject.getString(
				"dataDefinitionKey");

			DataDefinition dataDefinition = DataDefinition.toDTO(
				jsonObject.toString());

			try {
				DataDefinition siteDataDefinition =
					_dataDefinitionResource.
						getSiteDataDefinitionByContentTypeByDataDefinitionKey(
							groupId, contentType, dataDefinitionKey);

				_dataDefinitionResource.putDataDefinition(
					siteDataDefinition.getId(), dataDefinition);
			}
			catch (Exception exception) {
				System.out.println(
					"Unable to find data definition: " +
						exception.getMessage());

				System.out.println(
					"Adding new data definition with key " + dataDefinitionKey);

				_dataDefinitionResource.postSiteDataDefinitionByContentType(
					groupId, contentType, dataDefinition);
			}
		}
	}

	private void _addOrUpdateDDMTemplates(long groupId) throws Exception {
		System.out.println("Checking for DDM templates");

		List<Path> paths;

		try (Stream<Path> walk = Files.walk(Path.of("/resources"))) {
			paths = walk.filter(
				Files::isRegularFile
			).filter(
				file -> Objects.equals(
					file.getFileName(
					).toString(),
					"ddm-template.json")
			).collect(
				Collectors.toList()
			);
		}

		for (Path path : paths) {
			System.out.println("Found DDM template " + path);

			JSONObject jsonObject = new JSONObject(
				new String(Files.readAllBytes(path)));

			long resourceClassNameId = getClassNameId(
				"com.liferay.journal.model.JournalArticle");

			if (jsonObject.has("resourceClassName")) {
				resourceClassNameId = getClassNameId(
					jsonObject.getString("resourceClassName"));
			}

			long ddmStructureId = 0;

			if (jsonObject.has("ddmStructureKey")) {
				String ddmStructureKey = jsonObject.getString(
					"ddmStructureKey");

				JSONObject ddmStructureJSONObject = fetchStructure(
					groupId, resourceClassNameId, ddmStructureKey);

				ddmStructureId = ddmStructureJSONObject.getLong("structureId");
			}

			long classNameId = getClassNameId(
				"com.liferay.dynamic.data.mapping.model.DDMStructure");

			if (jsonObject.has("className")) {
				classNameId = getClassNameId(jsonObject.getString("className"));
			}

			Path templatePath = Path.of(
				String.valueOf(path.getParent()), "ddm-template.ftl");

			String script = new String(Files.readAllBytes(templatePath));

			JSONObject templateJSONObject = fetchTemplate(
				groupId, classNameId, jsonObject.getString("ddmTemplateKey"));

			if (templateJSONObject.isEmpty()) {
				addDDMTemplate(
					groupId, classNameId, resourceClassNameId, ddmStructureId,
					jsonObject.getString("name"),
					jsonObject.getString("ddmTemplateKey"), script, false);
			}
			else {
				updateDDMTemplate(
					ddmStructureId, jsonObject.getString("name"),
					templateJSONObject.getInt("templateId"), script, false);
			}
		}
	}

	private JSONObject _getOAuthAuthorizationJSONObject(
			URL liferayURL, String liferayOAuthClientId,
			String liferayOAuthClientSecret)
		throws Exception {

		System.out.println("Obtaining OAuth token");

		HttpPost httpPost = new HttpPost(liferayURL + "/o/oauth2/token");

		httpPost.setEntity(
			new UrlEncodedFormEntity(
				Arrays.asList(
					new BasicNameValuePair("client_id", liferayOAuthClientId),
					new BasicNameValuePair(
						"client_secret", liferayOAuthClientSecret),
					new BasicNameValuePair(
						"grant_type", "client_credentials"))));
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return new JSONObject(
					EntityUtils.toString(
						closeableHttpResponse.getEntity(),
						Charset.defaultCharset()));
			}

			throw new Exception("Unable to get OAuth authorization");
		}
	}

	private String _getTargetOAuthAuthorization() throws Exception {
		long expirationDelta =
			_liferayOAuthExpirationTimeMillis - System.currentTimeMillis();

		if (Validator.isNotNull(_liferayOAuthAuthorization) &&
			((expirationDelta - 10000) > 0)) {

			return _liferayOAuthAuthorization;
		}

		JSONObject targetOAuthorizationJSONObject =
			_getOAuthAuthorizationJSONObject(
				_liferayURL, _liferayOAuthClientId, _liferayOAuthClientSecret);

		_liferayOAuthExpirationTimeMillis =
			System.currentTimeMillis() +
				(targetOAuthorizationJSONObject.getLong("expires_in") * 1000);

		_liferayOAuthAuthorization =
			targetOAuthorizationJSONObject.getString("token_type") + " " +
				targetOAuthorizationJSONObject.getString("access_token");

		return _liferayOAuthAuthorization;
	}

	private void _initResourceBuilders() {
		DataDefinitionResource.Builder dataDefinitionResourceBuilder =
			DataDefinitionResource.builder();

		_dataDefinitionResource = dataDefinitionResourceBuilder.header(
			"Authorization", _liferayOAuthAuthorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();
	}

	private DataDefinitionResource _dataDefinitionResource;
	private String _liferayOAuthAuthorization;
	private final String _liferayOAuthClientId;
	private final String _liferayOAuthClientSecret;
	private long _liferayOAuthExpirationTimeMillis;
	private final URL _liferayURL;

}