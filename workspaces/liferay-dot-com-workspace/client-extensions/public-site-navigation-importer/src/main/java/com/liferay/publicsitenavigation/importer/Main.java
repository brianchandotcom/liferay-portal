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

package com.liferay.publicsitenavigation.importer;

import com.liferay.headless.admin.user.client.dto.v1_0.Site;
import com.liferay.headless.admin.user.client.resource.v1_0.SiteResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.net.URI;
import java.net.URL;

import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
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
		System.out.println("Starting public site navigation import");

		Main main = new Main(
			System.getenv("LIFERAY_OAUTH_CLIENT_ID"),
			System.getenv("LIFERAY_OAUTH_CLIENT_SECRET"),
			new URL(System.getenv("LIFERAY_URL")));

		main.uploadToLiferay(System.getenv("LIFERAY_GROUP_FRIENDLY_URL_PATH"));

		System.out.println("Ending public site navigation import");
	}

	public Main(
			String liferayOAuthClientId, String liferayOAuthClientSecret,
			URL liferayURL)
		throws Exception {

		_liferayOAuthClientId = liferayOAuthClientId;
		_liferayOAuthClientSecret = liferayOAuthClientSecret;
		_liferayURL = liferayURL;

		_getTargetOAuthAuthorization();
		_initResourceBuilders(_liferayOAuthAuthorization);
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
				"Unable to fetch classname: " + statusLine.getStatusCode() +
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
						"Unable to update DDM template: " +
							responseJSONObject.getString("error"));
				}

				return responseJSONObject;
			}

			throw new Exception(
				"Unable to update DDM template: " + statusLine.getStatusCode() +
					statusLine.getReasonPhrase());
		}
	}

	public void uploadToLiferay(String liferayGroupFriendlyUrlPath)
		throws Exception {

		Site site = _siteResource.getSiteByFriendlyUrlPath(
			liferayGroupFriendlyUrlPath);

		long groupId = site.getId();

		_importFragments(groupId, new File("/resources/fragments.zip"));

		_addOrUpdateDDMTemplates(groupId);
	}

	private void _addOrUpdateDDMTemplates(long groupId) throws Exception {
		System.out.println("Checking for DDM templates");

		List<Path> paths = new ArrayList<>();

		Files.walkFileTree(
			Path.of("/resources"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path path, BasicFileAttributes basicFileAttributes) {

					if (basicFileAttributes.isRegularFile() &&
						Objects.equals(
							String.valueOf(path.getFileName()),
							"ddm-template.json")) {

						paths.add(path);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		for (Path path : paths) {
			System.out.println("Found DDM template " + path);

			JSONObject jsonObject = new JSONObject(
				new String(Files.readAllBytes(path)));

			long resourceClassNameId = getClassNameId(
				jsonObject.getString("resourceClassName"));

			long ddmStructureId = 0;

			if (jsonObject.has("ddmStructureKey")) {
				String ddmStructureKey = jsonObject.getString(
					"ddmStructureKey");

				JSONObject ddmStructureJSONObject = fetchStructure(
					groupId, resourceClassNameId, ddmStructureKey);

				ddmStructureId = ddmStructureJSONObject.getLong("structureId");
			}

			long classNameId = getClassNameId(
				jsonObject.getString("className"));

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

	private JSONObject _importFragments(long groupId, File fragmentsZipFile)
		throws Exception {

		System.out.println("Importing fragments for groupId " + groupId);

		HttpPost httpPost = new HttpPost(
			_liferayURL + "/c/portal/fragment/import_fragment_entries");

		FileBody fileBody = new FileBody(
			fragmentsZipFile, ContentType.DEFAULT_BINARY);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("file", fileBody);
		builder.addPart(
			"groupId",
			new StringBody(
				String.valueOf(groupId), ContentType.MULTIPART_FORM_DATA));
		builder.addPart(
			"auth", new StringBody("oauth", ContentType.MULTIPART_FORM_DATA));

		httpPost.setEntity(builder.build());

		httpPost.setHeader("Authorization", _getTargetOAuthAuthorization());

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

			throw new Exception(
				"Unable to import fragments: " + statusLine.getStatusCode() +
					statusLine.getReasonPhrase());
		}
	}

	private void _initResourceBuilders(String authorization) throws Exception {
		SiteResource.Builder siteResourceBuilder = SiteResource.builder();

		_siteResource = siteResourceBuilder.header(
			"Authorization", authorization
		).endpoint(
			_liferayURL.getHost(), _liferayURL.getPort(),
			_liferayURL.getProtocol()
		).build();
	}

	private String _liferayOAuthAuthorization;
	private final String _liferayOAuthClientId;
	private final String _liferayOAuthClientSecret;
	private long _liferayOAuthExpirationTimeMillis;
	private final URL _liferayURL;
	private SiteResource _siteResource;

}