/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.model;

import java.util.List;

/**
 * @author Brooke Dalton
 */
public class InsightResult {

	public InsightResult(
		String category, String classification, String description,
		String fixHint, String name, List<String> pageURLs, String severity) {

		_category = category;
		_classification = classification;
		_description = description;
		_fixHint = fixHint;
		_name = name;
		_pageURLs = pageURLs;
		_severity = severity;
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