/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Felipe Veloso
 */
public class ExperienceUsageStrategy extends BaseUsageStrategy {

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

			_databaseCapacityBytesUsed = usageJSONObject.optLong(
				"databaseStorage");
			_extensionsCapacityCPUUsed = usageJSONObject.optBigDecimal(
				"clientExtensionsCPU", BigDecimal.ZERO);
			_extensionsCapacityRAMBytesUsed = usageJSONObject.optLong(
				"clientExtensionsRAM");
			_logCapacityBytesUsed = usageJSONObject.optLong("logStorage");
			_networkingCapacityBytesUsed = usageJSONObject.optLong(
				"networkTraffic");
			_storageCapacityBytesUsed = usageJSONObject.optLong(
				"documentLibraryAndBackupStorage");
		}
		else {
			_databaseCapacityBytesUsed = 0;
			_extensionsCapacityCPUUsed = BigDecimal.ZERO;
			_extensionsCapacityRAMBytesUsed = 0;
			_logCapacityBytesUsed = 0;
			_networkingCapacityBytesUsed = 0;
			_storageCapacityBytesUsed = 0;
		}
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		return jsonObject.put(
			"clientExtensionsCPU",
			createUsageJSONObject(
				_extensionsCapacityCPUUsed, _extensionsCapacityCPUMax)
		).put(
			"clientExtensionsRAM",
			createUsageJSONObject(
				_convertToGigaBytes(_extensionsCapacityRAMBytesUsed),
				_extensionsCapacityRAMMax, UNIT_GIB)
		).put(
			"databaseStorage",
			createUsageJSONObject(
				_convertToGigaBytes(_databaseCapacityBytesUsed),
				_databaseCapacityMax, UNIT_GIB)
		).put(
			"documentLibraryAndBackupStorage",
			createUsageJSONObject(
				_convertToGigaBytes(_storageCapacityBytesUsed),
				_storageCapacityMax, UNIT_TIB)
		).put(
			"logStorage",
			createUsageJSONObject(
				_convertToGigaBytes(_logCapacityBytesUsed), _logCapacityMax,
				_logCapacityUnit)
		).put(
			"networkTraffic",
			createUsageJSONObject(
				_convertToGigaBytes(_networkingCapacityBytesUsed),
				_networkingCapacityMax, UNIT_TIB)
		);
	}

	private BigDecimal _convertToGigaBytes(long bytes) {
		return new BigDecimal(
			bytes
		).divide(
			_GIB_DIVISOR
		).setScale(
			2, RoundingMode.DOWN
		);
	}

	private static final BigDecimal _GIB_DIVISOR = new BigDecimal(
		1024L * 1024L * 1024L);

	private static final String _MACHINE_TYPE_HIGH = "high";

	private final long _databaseCapacityBytesUsed;
	private final int _databaseCapacityMax;
	private final int _extensionsCapacityCPUMax;
	private final BigDecimal _extensionsCapacityCPUUsed;
	private final long _extensionsCapacityRAMBytesUsed;
	private final int _extensionsCapacityRAMMax;
	private final long _logCapacityBytesUsed;
	private final long _logCapacityMax;
	private String _logCapacityUnit = UNIT_GIB;
	private final long _networkingCapacityBytesUsed;
	private final int _networkingCapacityMax;
	private final long _storageCapacityBytesUsed;
	private final int _storageCapacityMax;

}