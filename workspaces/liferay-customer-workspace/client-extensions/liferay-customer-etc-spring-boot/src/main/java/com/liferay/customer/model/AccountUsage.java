/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import com.liferay.customer.constants.ProductConstants;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class AccountUsage {

	public AccountUsage(
		List<ProductPurchase> productPurchases, JSONObject usageJSONObject) {

		int additionalCPUAndRAMMax = 0;
		int additionalStorageCapacityDocumentLibraryMax = 0;
		long anonymousPageViewsMax = 0;
		Product liferaySaasPlanProduct = null;
		long monthlyActiveLoggedInUsersMax = 0;

		for (ProductPurchase productPurchase : productPurchases) {
			Product product = productPurchase.getProduct();

			String name = product.getName();

			if (name.equals(
					ProductConstants.
						NAME_ADDITIONAL_EXTENSION_CAPACITY_1GB_1VCPU)) {

				if (productPurchase.getQuantity() > additionalCPUAndRAMMax) {
					additionalCPUAndRAMMax = productPurchase.getQuantity();
				}
			}
			else if (name.equals(
						ProductConstants.NAME_ADDITIONAL_STORAGE_100GB) ||
					 name.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_100GB_EXTRA_STORAGE_DOCUMENT_LIBRARY)) {

				if ((productPurchase.getQuantity() * 100) >
						additionalStorageCapacityDocumentLibraryMax) {

					additionalStorageCapacityDocumentLibraryMax =
						productPurchase.getQuantity() * 100;
				}
			}
			else if (name.equals(
						ProductConstants.NAME_LIFERAY_SAAS_BUSINESS_PLAN)) {

				if (liferaySaasPlanProduct == null) {
					liferaySaasPlanProduct = product;
				}
				else {
					String liferaySaasPlanName =
						liferaySaasPlanProduct.getName();

					if (liferaySaasPlanName.equals(
							ProductConstants.NAME_LIFERAY_SAAS_PRO_PLAN)) {

						liferaySaasPlanProduct = product;
					}
				}
			}
			else if (name.equals(
						ProductConstants.NAME_LIFERAY_SAAS_CUSTOM_APVS)) {

				if (productPurchase.getQuantity() > anonymousPageViewsMax) {
					anonymousPageViewsMax = productPurchase.getQuantity();
				}
			}
			else if (name.equals(
						ProductConstants.NAME_LIFERAY_SAAS_CUSTOM_MALUS)) {

				if (productPurchase.getQuantity() >
						monthlyActiveLoggedInUsersMax) {

					monthlyActiveLoggedInUsersMax =
						productPurchase.getQuantity();
				}
			}
			else if (name.equals(
						ProductConstants.NAME_LIFERAY_SAAS_ENTERPRISE_PLAN)) {

				liferaySaasPlanProduct = product;
			}
			else if (name.startsWith(
						ProductConstants.
							NAME_LIFERAY_SAAS_ENTITLEMENTS_PREFIX) &&
					 name.endsWith("APVs")) {

				long curAnonymousPageViewsMax = _getAnonymousPageViewsMax(name);

				if (curAnonymousPageViewsMax > anonymousPageViewsMax) {
					anonymousPageViewsMax = curAnonymousPageViewsMax;
				}
			}
			else if (name.startsWith(
						ProductConstants.
							NAME_LIFERAY_SAAS_ENTITLEMENTS_PREFIX) &&
					 name.endsWith("MALUs")) {

				long curMonthlyActiveLoggedInUsersMax =
					_getMonthlyActiveLoggedInUsersMax(name);

				if (curMonthlyActiveLoggedInUsersMax >
						monthlyActiveLoggedInUsersMax) {

					monthlyActiveLoggedInUsersMax =
						curMonthlyActiveLoggedInUsersMax;
				}
			}
			else if (name.equals(ProductConstants.NAME_LIFERAY_SAAS_PRO_PLAN)) {
				if (liferaySaasPlanProduct == null) {
					liferaySaasPlanProduct = product;
				}
			}
		}

		_initLiferaySaasPlan(liferaySaasPlanProduct);

		_anonymousPageViewsMax = anonymousPageViewsMax;
		_clientExtensionsCapacityCPUMax += additionalCPUAndRAMMax;
		_clientExtensionsCapacityRAMMax += additionalCPUAndRAMMax;
		_monthlyActiveLoggedInUsersMax = monthlyActiveLoggedInUsersMax;
		_storageCapacityDocumentLibraryMax +=
			additionalStorageCapacityDocumentLibraryMax;

		_initUsage(usageJSONObject);
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"anonymousPageViews",
			_getUsageJSONObject(_anonymousPageViewsUsed, _anonymousPageViewsMax)
		).put(
			"clientExtensionsCapacityCPU",
			_getUsageJSONObject(
				_clientExtensionsCapacityCPUUsed,
				_clientExtensionsCapacityCPUMax)
		).put(
			"clientExtensionsCapacityRAM",
			_getUsageJSONObject(
				_clientExtensionsCapacityRAMUsed,
				_clientExtensionsCapacityRAMMax)
		).put(
			"monthlyActiveLoggedInUsers",
			_getUsageJSONObject(
				_monthlyActiveLoggedInUsersUsed, _monthlyActiveLoggedInUsersMax)
		).put(
			"sites", _getUsageJSONObject(_sitesUsed, _sitesMax)
		).put(
			"storageCapacityDocumentLibrary",
			_getUsageJSONObject(
				_storageCapacityDocumentLibraryUsed,
				_storageCapacityDocumentLibraryMax)
		);

		return jsonObject;
	}

	private long _getAnonymousPageViewsMax(String name) {
		String anonymousPageViewsMaxString = name.substring(
			ProductConstants.NAME_LIFERAY_SAAS_ENTITLEMENTS_PREFIX.length());

		anonymousPageViewsMaxString = StringUtil.removeSubstrings(
			anonymousPageViewsMaxString, StringPool.COMMA, " APVs");

		return GetterUtil.getLong(anonymousPageViewsMaxString);
	}

	private long _getMonthlyActiveLoggedInUsersMax(String name) {
		String monthlyActiveLoggedInUsersMaxString = name.substring(
			ProductConstants.NAME_LIFERAY_SAAS_ENTITLEMENTS_PREFIX.length());

		monthlyActiveLoggedInUsersMaxString = StringUtil.removeSubstrings(
			monthlyActiveLoggedInUsersMaxString, StringPool.COMMA, " MALUs");

		return GetterUtil.getLong(monthlyActiveLoggedInUsersMaxString);
	}

	private JSONObject _getUsageJSONObject(long usedCount, long maxCount) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"maxCount", maxCount
		).put(
			"usedCount", usedCount
		);

		return jsonObject;
	}

	private void _initLiferaySaasPlan(Product product) {
		if (product == null) {
			return;
		}

		Map<String, String> properties = product.getProperties();

		_clientExtensionsCapacityCPUMax = GetterUtil.getInteger(
			properties.get("vcpu"));

		String clientExtensionsCapacityRAMMaxPropertyValue = properties.get(
			"ram");

		clientExtensionsCapacityRAMMaxPropertyValue =
			StringUtil.removeSubstring(
				clientExtensionsCapacityRAMMaxPropertyValue, " GB");

		_clientExtensionsCapacityRAMMax = GetterUtil.getInteger(
			clientExtensionsCapacityRAMMaxPropertyValue);

		String sitesPropertyValue = properties.get("sites");

		if (sitesPropertyValue.equals("Unlimited")) {
			_sitesMax = -1;
		}
		else {
			_sitesMax = GetterUtil.getInteger(sitesPropertyValue);
		}

		String storageCapacityDocumentLibraryMaxPropertyValue = properties.get(
			"document-library-size");

		storageCapacityDocumentLibraryMaxPropertyValue =
			StringUtil.removeSubstring(
				storageCapacityDocumentLibraryMaxPropertyValue, " GB");

		_storageCapacityDocumentLibraryMax = GetterUtil.getInteger(
			storageCapacityDocumentLibraryMaxPropertyValue);
	}

	private void _initUsage(JSONObject jsonObject) {
		_anonymousPageViewsUsed = jsonObject.getLong(
			"totalAnonymousPageViewsCount");
		_clientExtensionsCapacityCPUUsed = jsonObject.getInt(
			"totalClientExtensionsCapacityCPUCount");
		_clientExtensionsCapacityRAMUsed = jsonObject.getInt(
			"totalClientExtensionsCapacityRAM");
		_monthlyActiveLoggedInUsersUsed = jsonObject.getLong(
			"totalMonthlyActiveLoggedInUsersCount");
		_sitesUsed = jsonObject.getInt("totalSitesCount");
		_storageCapacityDocumentLibraryUsed = jsonObject.getInt(
			"totalStorageCapacityDocumentLibrary");
	}

	private final long _anonymousPageViewsMax;
	private long _anonymousPageViewsUsed;
	private int _clientExtensionsCapacityCPUMax;
	private int _clientExtensionsCapacityCPUUsed;
	private int _clientExtensionsCapacityRAMMax;
	private int _clientExtensionsCapacityRAMUsed;
	private final long _monthlyActiveLoggedInUsersMax;
	private long _monthlyActiveLoggedInUsersUsed;
	private int _sitesMax;
	private int _sitesUsed;
	private int _storageCapacityDocumentLibraryMax;
	private int _storageCapacityDocumentLibraryUsed;

}