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

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Felipe Veloso
 */
public class SaaSUsageStrategy extends BaseUsageStrategy {

	public SaaSUsageStrategy(
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
		_extensionsCapacityCPUMax += additionalCPUAndRAMMax;
		_extensionsCapacityRAMMax += additionalCPUAndRAMMax;
		_monthlyActiveLoggedInUsersMax = monthlyActiveLoggedInUsersMax;
		_storageCapacityMax += additionalStorageCapacityDocumentLibraryMax;

		if (usageJSONObject != null) {
			_anonymousPageViewsUsed = usageJSONObject.optBigDecimal(
				"totalAnonymousPageViewsCount", BigDecimal.ZERO);
			_extensionsCapacityCPUUsed = usageJSONObject.optBigDecimal(
				"totalClientExtensionsCapacityCPUCount", BigDecimal.ZERO);
			_extensionsCapacityRAMUsedGigaBytes = usageJSONObject.optBigDecimal(
				"totalClientExtensionsCapacityRAM", BigDecimal.ZERO);
			_monthlyActiveLoggedInUsersUsed = usageJSONObject.optBigDecimal(
				"totalMonthlyActiveLoggedInUsersCount", BigDecimal.ZERO);
			_sitesUsed = usageJSONObject.optBigDecimal(
				"totalSitesCount", BigDecimal.ZERO);
			_storageCapacityUsedGigaBytes = usageJSONObject.optBigDecimal(
				"totalStorageCapacityDocumentLibrary", BigDecimal.ZERO);
		}
		else {
			_anonymousPageViewsUsed = BigDecimal.ZERO;
			_extensionsCapacityCPUUsed = BigDecimal.ZERO;
			_extensionsCapacityRAMUsedGigaBytes = BigDecimal.ZERO;
			_monthlyActiveLoggedInUsersUsed = BigDecimal.ZERO;
			_sitesUsed = BigDecimal.ZERO;
			_storageCapacityUsedGigaBytes = BigDecimal.ZERO;
		}
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"anonymousPageViews",
			createUsageJSONObject(
				_anonymousPageViewsUsed, _anonymousPageViewsMax,
				StringPool.BLANK)
		).put(
			"clientExtensionsCapacityCPU",
			createUsageJSONObject(
				_format(_extensionsCapacityCPUUsed), _extensionsCapacityCPUMax,
				StringPool.BLANK)
		).put(
			"clientExtensionsCapacityRAM",
			createUsageJSONObject(
				_format(_extensionsCapacityRAMUsedGigaBytes),
				_extensionsCapacityRAMMax, UNIT_GIB)
		).put(
			"monthlyActiveLoggedInUsers",
			createUsageJSONObject(
				_monthlyActiveLoggedInUsersUsed, _monthlyActiveLoggedInUsersMax,
				StringPool.BLANK)
		).put(
			"sites",
			createUsageJSONObject(_sitesUsed, _sitesMax, StringPool.BLANK)
		).put(
			"storageCapacityDocumentLibrary",
			createUsageJSONObject(
				_format(_storageCapacityUsedGigaBytes), _storageCapacityMax,
				UNIT_GIB)
		);

		return jsonObject;
	}

	private BigDecimal _format(BigDecimal bigDecimal) {
		if (bigDecimal != null) {
			return bigDecimal.setScale(2, RoundingMode.DOWN);
		}

		return BigDecimal.ZERO;
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

	private void _initLiferaySaasPlan(Product product) {
		if (product == null) {
			return;
		}

		Map<String, String> properties = product.getProperties();

		_extensionsCapacityCPUMax = GetterUtil.getInteger(
			properties.get("vcpu"));

		String clientExtensionsCapacityRAMMaxPropertyValue = properties.get(
			"ram");

		clientExtensionsCapacityRAMMaxPropertyValue =
			StringUtil.removeSubstring(
				clientExtensionsCapacityRAMMaxPropertyValue, " GB");

		_extensionsCapacityRAMMax = GetterUtil.getInteger(
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

		_storageCapacityMax = GetterUtil.getInteger(
			storageCapacityDocumentLibraryMaxPropertyValue);
	}

	private final long _anonymousPageViewsMax;
	private final BigDecimal _anonymousPageViewsUsed;
	private int _extensionsCapacityCPUMax;
	private final BigDecimal _extensionsCapacityCPUUsed;
	private int _extensionsCapacityRAMMax;
	private final BigDecimal _extensionsCapacityRAMUsedGigaBytes;
	private final long _monthlyActiveLoggedInUsersMax;
	private final BigDecimal _monthlyActiveLoggedInUsersUsed;
	private int _sitesMax;
	private final BigDecimal _sitesUsed;
	private int _storageCapacityMax;
	private final BigDecimal _storageCapacityUsedGigaBytes;

}