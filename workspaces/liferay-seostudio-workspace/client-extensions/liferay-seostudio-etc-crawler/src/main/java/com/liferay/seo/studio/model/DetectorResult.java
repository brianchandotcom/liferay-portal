/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.model;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Brooke Dalton
 */
public class DetectorResult {

	public DetectorResult(JSONObject jsonObject, List<String> pageURLs) {
		_pageURLs = pageURLs;

		_category = jsonObject.optString("category", null);
		_classification = jsonObject.optString("classification", null);
		_description = jsonObject.optString("description", null);
		_fixHint = jsonObject.optString("fixHint", null);
		_name = jsonObject.optString("name", null);
		_severity = jsonObject.optString("severity", null);
	}

	public String getCategory() {
		return _category;
	}

	public String getClassification() {
		return _classification;
	}

	public String getDescription() {
		return _description;
	}

	public String getFixHint() {
		return _fixHint;
	}

	public String getName() {
		return _name;
	}

	public List<String> getPageURLs() {
		return _pageURLs;
	}

	public String getSeverity() {
		return _severity;
	}

	private final String _category;
	private final String _classification;
	private final String _description;
	private final String _fixHint;
	private final String _name;
	private final List<String> _pageURLs;
	private final String _severity;

}