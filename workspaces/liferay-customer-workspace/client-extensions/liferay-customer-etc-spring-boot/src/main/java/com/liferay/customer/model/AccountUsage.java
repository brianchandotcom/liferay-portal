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

	public void setAnonymousPageViewsMax(long anonymousPageViewsMax) {
		_anonymousPageViewsMax = anonymousPageViewsMax;
	}

	public void setAnonymousPageViewsUsed(long anonymousPageViewsUsed) {
		_anonymousPageViewsUsed = anonymousPageViewsUsed;
	}

	public void setClientExtensionsCapacityCPUMax(
		long clientExtensionsCapacityCPUMax) {

		_clientExtensionsCapacityCPUMax = clientExtensionsCapacityCPUMax;
	}

	public void setClientExtensionsCapacityCPUUsed(
		long clientExtensionsCapacityCPUUsed) {

		_clientExtensionsCapacityCPUUsed = clientExtensionsCapacityCPUUsed;
	}

	public void setClientExtensionsCapacityRAMMax(
		long clientExtensionsCapacityRAMMax) {

		_clientExtensionsCapacityRAMMax = clientExtensionsCapacityRAMMax;
	}

	public void setClientExtensionsCapacityRAMUsed(
		long clientExtensionsCapacityRAMUsed) {

		_clientExtensionsCapacityRAMUsed = clientExtensionsCapacityRAMUsed;
	}

	public void setMonthlyActiveLoggedInUsersMax(
		long monthlyActiveLoggedInUsersMax) {

		_monthlyActiveLoggedInUsersMax = monthlyActiveLoggedInUsersMax;
	}

	public void setMonthlyActiveLoggedInUsersUsed(
		long monthlyActiveLoggedInUsersUsed) {

		monthlyActiveLoggedInUsersUsed = monthlyActiveLoggedInUsersUsed;
	}

	public void setSitesMax(long sitesMax) {
		_sitesMax = sitesMax;
	}

	public void setSitesUsed(long sitesUsed) {
		_sitesUsed = sitesUsed;
	}

	public void setStorageCapacityDocumentLibraryMax(
		long storageCapacityDocumentLibraryMax) {

		_storageCapacityDocumentLibraryMax = storageCapacityDocumentLibraryMax;
	}

	public void setStorageCapacityDocumentLibraryUsed(
		long storageCapacityDocumentLibraryUsed) {

		_storageCapacityDocumentLibraryUsed =
			storageCapacityDocumentLibraryUsed;
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
	private long _anonymousPageViewsMax;
	private long _anonymousPageViewsUsed;
	private long _clientExtensionsCapacityCPUMax;
	private long _clientExtensionsCapacityCPUUsed;
	private long _clientExtensionsCapacityRAMMax;
	private long _clientExtensionsCapacityRAMUsed;
	private long _monthlyActiveLoggedInUsersMax;
	private long _monthlyActiveLoggedInUsersUsed;
	private long _sitesMax;
	private long _sitesUsed;
	private long _storageCapacityDocumentLibraryMax;
	private long _storageCapacityDocumentLibraryUsed;

}