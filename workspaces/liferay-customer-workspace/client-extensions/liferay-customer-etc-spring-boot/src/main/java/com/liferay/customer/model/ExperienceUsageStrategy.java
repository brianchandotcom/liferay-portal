/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Felipe Veloso
 */
public class ExperienceUsageStrategy implements UsageStrategy {

	public ExperienceUsageStrategy(
		ProductPurchase productPurchase, JSONObject usageJSONObject) {

		Map<String, String> productPurchaseProperties =
			productPurchase.getProperties();

		String machineType = StringUtil.toLowerCase(
			GetterUtil.getString(productPurchaseProperties.get("machineType")));

		Product product = productPurchase.getProduct();

		Map<String, String> productProperties = product.getProperties();

		_databaseCapacityMax = GetterUtil.getInteger(
			StringUtil.removeSubstring(
				productProperties.get(machineType + "-database"), UNIT_GIB));

		_extensionsCapacityCPUMax = GetterUtil.getInteger(
			productProperties.get(machineType + "-extensions-vcpus"));

		_extensionsCapacityRAMMax = GetterUtil.getInteger(
			productProperties.get(machineType + "-extensions-ram"));

		if (machineType.equals(_MACHINE_TYPE_HIGH)) {
			_logCapacityUnit = UNIT_TIB;
		}

		_logCapacityMax = GetterUtil.getInteger(
			StringUtil.removeSubstring(
				productProperties.get(machineType + "-logs"),
				_logCapacityUnit));

		_networkingCapacityMax = GetterUtil.getInteger(
			StringUtil.removeSubstring(
				productProperties.get(machineType + "-traffic-networking"),
				UNIT_TIB));

		_storageCapacityMax = GetterUtil.getInteger(
			StringUtil.removeSubstring(
				productProperties.get(machineType + "-storage"), UNIT_TIB));

		if (usageJSONObject != null) {
			usageJSONObject = usageJSONObject.getJSONObject("usage");

			_databaseCapacityUsedBytes = usageJSONObject.optBigDecimal(
				"databaseStorage", BigDecimal.ZERO);

			_extensionsCapacityCPUUsed = usageJSONObject.optBigDecimal(
				"clientExtensionsCPU", BigDecimal.ZERO);

			_extensionsCapacityRAMUsedBytes = usageJSONObject.optBigDecimal(
				"clientExtensionsRAM", BigDecimal.ZERO);

			_logCapacityUsedBytes = usageJSONObject.optBigDecimal(
				"logStorage", BigDecimal.ZERO);

			_networkingCapacityUsedBytes = usageJSONObject.optBigDecimal(
				"networkTraffic", BigDecimal.ZERO);

			_storageCapacityUsedBytes = usageJSONObject.optBigDecimal(
				"documentLibraryAndBackupStorage", BigDecimal.ZERO);
		}
		else {
			_databaseCapacityUsedBytes = BigDecimal.ZERO;
			_extensionsCapacityCPUUsed = BigDecimal.ZERO;
			_extensionsCapacityRAMUsedBytes = BigDecimal.ZERO;
			_logCapacityUsedBytes = BigDecimal.ZERO;
			_networkingCapacityUsedBytes = BigDecimal.ZERO;
			_storageCapacityUsedBytes = BigDecimal.ZERO;
		}
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		return jsonObject.put(
			"clientExtensionsCPU",
			UsageStrategy.createUsageJSONObject(
				_extensionsCapacityCPUUsed, _extensionsCapacityCPUMax,
				StringPool.BLANK)
		).put(
			"clientExtensionsRAM",
			UsageStrategy.createUsageJSONObject(
				_convertToGigaBytes(_extensionsCapacityRAMUsedBytes),
				_extensionsCapacityRAMMax, UNIT_GIB)
		).put(
			"databaseStorage",
			UsageStrategy.createUsageJSONObject(
				_convertToGigaBytes(_databaseCapacityUsedBytes),
				_databaseCapacityMax, UNIT_GIB)
		).put(
			"documentLibraryAndBackupStorage",
			UsageStrategy.createUsageJSONObject(
				_convertToGigaBytes(_storageCapacityUsedBytes),
				_storageCapacityMax, UNIT_TIB)
		).put(
			"logStorage",
			UsageStrategy.createUsageJSONObject(
				_convertToGigaBytes(_logCapacityUsedBytes), _logCapacityMax,
				_logCapacityUnit)
		).put(
			"networkTraffic",
			UsageStrategy.createUsageJSONObject(
				_convertToGigaBytes(_networkingCapacityUsedBytes),
				_networkingCapacityMax, UNIT_TIB)
		);
	}

	private BigDecimal _convertToGigaBytes(BigDecimal bytes) {
		BigDecimal gigaBytes = bytes.divide(_GIB_DIVISOR);

		return gigaBytes.setScale(2, RoundingMode.DOWN);
	}

	private static final BigDecimal _GIB_DIVISOR = new BigDecimal(
		1024L * 1024L * 1024L);

	private static final String _MACHINE_TYPE_HIGH = "high";

	private final int _databaseCapacityMax;
	private final BigDecimal _databaseCapacityUsedBytes;
	private final int _extensionsCapacityCPUMax;
	private final BigDecimal _extensionsCapacityCPUUsed;
	private final int _extensionsCapacityRAMMax;
	private final BigDecimal _extensionsCapacityRAMUsedBytes;
	private final long _logCapacityMax;
	private String _logCapacityUnit = UNIT_GIB;
	private final BigDecimal _logCapacityUsedBytes;
	private final int _networkingCapacityMax;
	private final BigDecimal _networkingCapacityUsedBytes;
	private final int _storageCapacityMax;
	private final BigDecimal _storageCapacityUsedBytes;

}