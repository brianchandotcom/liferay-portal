/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.util;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.marketplace.model.PublisherAssetLink;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Keven Leone
 * @author Eduardo Diniz
 */
public class MarketplaceUtil {

	public static File addArtifactMetadata(
			File file, String fileName, Map<String, Properties> propertiesMap)
		throws IOException {

		Path tempDirectoryPath = Files.createTempDirectory("marketplace-temp-");

		Path path = tempDirectoryPath.resolve(fileName);

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				Files.newOutputStream(path));
			ZipFile zipFile = new ZipFile(file)) {

			_cloneZipFile(zipFile, zipOutputStream);

			_addPropertiesToZipFile(propertiesMap, zipOutputStream);
		}

		return path.toFile();
	}

	public static JSONArray createCloudProvisioningJSONArray(
		Page<OrderItem> orderItemPage) {

		JSONArray jsonArray = new JSONArray();

		for (OrderItem orderItem : orderItemPage.getItems()) {
			jsonArray.put(
				new JSONObject(
				).put(
					"deployments", new JSONArray()
				).put(
					"orderItemId", orderItem.getId()
				).put(
					"quantity",
					orderItem.getQuantity(
					).intValue()
				).put(
					"shippedQuantity", 0
				).put(
					"sku", orderItem.getSku()
				));
		}

		return jsonArray;
	}

	public static Properties createMarketplaceProperties(
		Product product, PublisherAssetLink publisherAssetLink) {

		Properties properties = new Properties();

		properties.setProperty("license-version", "1.0.0");
		properties.setProperty("product-id", String.valueOf(product.getId()));
		properties.setProperty(
			"product-name", _getDefaultLocale(product.getName()));
		properties.setProperty("product-version-id", "1");
		properties.setProperty(
			"publisher-asset-version", publisherAssetLink.getVersion());

		return properties;
	}

	public static Properties createProductProperties(
		Product product, PublisherAssetLink publisherAssetLink) {

		Properties properties = new Properties();

		properties.setProperty("bundles", "");
		properties.setProperty(
			"category", Arrays.toString(product.getCategories()));
		properties.setProperty("context-names", "");
		properties.setProperty(
			"description", _getDefaultLocale(product.getDescription()));
		properties.setProperty("icon-url", product.getThumbnail());
		properties.setProperty(
			"remote-app-id", String.valueOf(product.getId()));
		properties.setProperty("required", "false");
		properties.setProperty("restart-required", "false");
		properties.setProperty("title", _getDefaultLocale(product.getName()));
		properties.setProperty("version", publisherAssetLink.getVersion());

		return properties;
	}

	public static String createTemporaryDeployment(
			Map<String, String> customFields, JSONArray jsonArray,
			JSONObject jsonObject, String projectId)
		throws Exception {

		UUID uuid = UUID.randomUUID();

		jsonObject.put(
			"deployments",
			jsonObject.getJSONArray(
				"deployments"
			).put(
				new JSONObject(
				).put(
					"id", uuid.toString()
				).put(
					"loading", true
				).put(
					"projectId", projectId
				)
			));

		customFields.put("cloud-provisioning", jsonArray.toString());

		return uuid.toString();
	}

	public static void deleteDeployment(
		String deploymentId, JSONObject jsonObject) {

		JSONArray deploymentsJSONArray = jsonObject.getJSONArray("deployments");

		for (int i = 0; i < deploymentsJSONArray.length(); i++) {
			JSONObject deploymentJSONObject =
				deploymentsJSONArray.getJSONObject(i);

			if (Objects.equals(
					deploymentJSONObject.getString("id"), deploymentId)) {

				deploymentsJSONArray.remove(i);
			}
		}
	}

	public static void deleteTempFile(
		File file, boolean deleteParentDirectory) {

		try {
			if (file != null) {
				Files.deleteIfExists(file.toPath());

				if (deleteParentDirectory) {
					Files.deleteIfExists(
						file.toPath(
						).getParent());
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	public static Map<String, Properties> getArtifactProperties(
		Product product, Map<String, String> productSpecificationsMap,
		PublisherAssetLink publisherAssetLink) {

		return HashMapBuilder.<String, Properties>put(
			"liferay-marketplace.properties",
			() -> createProductProperties(product, publisherAssetLink)
		).put(
			"META-INF/marketplace.properties",
			() -> {
				if (Objects.equals(
						productSpecificationsMap.get("price-model"), "Paid")) {

					return createMarketplaceProperties(
						product, publisherAssetLink);
				}

				return null;
			}
		).build();
	}

	public static JSONObject getCloudProvisioningJSONObject(
		JSONArray jsonArray, long orderItemId) {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			if (Objects.equals(
					jsonObject.getLong("orderItemId"), orderItemId)) {

				return jsonObject;
			}
		}

		return new JSONObject();
	}

	public static String getSkuOptionValue(String key, SkuOption[] skuOptions) {
		for (SkuOption skuOption : skuOptions) {
			if (!Objects.equals(key, skuOption.getKey())) {
				continue;
			}

			return StringUtil.upperCaseFirstLetter(skuOption.getValue());
		}

		return null;
	}

	public static String getSkuOptionValue(String key, String options) {
		JSONArray optionsJSONArray = new JSONArray(options);

		for (int i = 0; i < optionsJSONArray.length(); i++) {
			JSONObject jsonObject = optionsJSONArray.getJSONObject(i);

			if (!Objects.equals(key, jsonObject.getString("key"))) {
				continue;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("value");

			return jsonArray.getString(0);
		}

		return null;
	}

	private static void _addPropertiesToZipFile(
			Map<String, Properties> propertiesMap,
			ZipOutputStream zipOutputStream)
		throws IOException {

		for (Map.Entry<String, Properties> entry : propertiesMap.entrySet()) {
			String key = entry.getKey();

			int lastPathIndex = StringUtil.lastIndexOfAny(
				key, new char[] {'/'});

			if (lastPathIndex != -1) {
				zipOutputStream.putNextEntry(
					new ZipEntry(key.substring(0, lastPathIndex + 1)));
				zipOutputStream.closeEntry();
			}

			zipOutputStream.putNextEntry(new ZipEntry(key));

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			entry.getValue(
			).store(
				byteArrayOutputStream, "Added automatically by Marketplace"
			);

			zipOutputStream.write(byteArrayOutputStream.toByteArray());

			zipOutputStream.closeEntry();
		}
	}

	private static void _cloneZipFile(
			ZipFile zipFile, ZipOutputStream zipOutputStream)
		throws IOException {

		Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

		while (enumeration.hasMoreElements()) {
			ZipEntry zipEntry = enumeration.nextElement();

			zipOutputStream.putNextEntry(new ZipEntry(zipEntry.getName()));

			if (!zipEntry.isDirectory()) {
				try (InputStream inputStream = zipFile.getInputStream(
						zipEntry)) {

					inputStream.transferTo(zipOutputStream);
				}
			}

			zipOutputStream.closeEntry();
		}
	}

	private static String _getDefaultLocale(Map<String, String> localeMap) {
		return localeMap.get("en_US");
	}

	private static final Log _log = LogFactory.getLog(MarketplaceUtil.class);

}