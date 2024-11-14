/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.model;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class AccountUsage {

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	public void setAPVsMax(long apvsMax) {
		_apvsMax = apvsMax;
	}

	public void setAPVsUsed(long apvsUsed) {
		_apvsUsed = apvsUsed;
	}

	public void setExtensionsCapacityCpuMax(long extensionsCapacityCpuMax) {
		_extensionsCapacityCpuMax = extensionsCapacityCpuMax;
	}

	public void setExtensionsCapacityCpuUsed(long extensionsCapacityCpuUsed) {
		_extensionsCapacityCpuUsed = extensionsCapacityCpuUsed;
	}

	public void setExtensionsCapacityRamMax(long extensionsCapacityRamMax) {
		_extensionsCapacityRamMax = extensionsCapacityRamMax;
	}

	public void setExtensionsCapacityRamUsed(long extensionsCapacityRamUsed) {
		_extensionsCapacityRamUsed = extensionsCapacityRamUsed;
	}

	public void setMALUsMax(long malusMax) {
		_malusMax = malusMax;
	}

	public void setMALUsUsed(long malusUsed) {
		_malusUsed = malusUsed;
	}

	public void setSitesMax(long sitesMax) {
		_sitesMax = sitesMax;
	}

	public void setSitesUsed(long sitesUsed) {
		_sitesUsed = sitesUsed;
	}

	public void setStorageCapacityMax(long storageCapacityMax) {
		_storageCapacityMax = storageCapacityMax;
	}

	public void setStorageCapacityUsed(long storageCapacityUsed) {
		_storageCapacityUsed = storageCapacityUsed;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"apvs", _getUsageJSONObject(_apvsUsed, _apvsMax)
		).put(
			"extensionsCapacityCpu",
			_getUsageJSONObject(
				_extensionsCapacityCpuUsed, _extensionsCapacityCpuMax)
		).put(
			"extensionsCapacityRam",
			_getUsageJSONObject(
				_extensionsCapacityRamUsed, _extensionsCapacityRamMax)
		).put(
			"malus", _getUsageJSONObject(_malusUsed, _malusMax)
		).put(
			"sites", _getUsageJSONObject(_sitesUsed, _sitesMax)
		).put(
			"storageCapacity",
			_getUsageJSONObject(_storageCapacityUsed, _storageCapacityMax)
		);

		return jsonObject;
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

	private long _accountId;
	private long _apvsMax;
	private long _apvsUsed;
	private long _extensionsCapacityCpuMax;
	private long _extensionsCapacityCpuUsed;
	private long _extensionsCapacityRamMax;
	private long _extensionsCapacityRamUsed;
	private long _malusMax;
	private long _malusUsed;
	private long _sitesMax;
	private long _sitesUsed;
	private long _storageCapacityMax;
	private long _storageCapacityUsed;

}