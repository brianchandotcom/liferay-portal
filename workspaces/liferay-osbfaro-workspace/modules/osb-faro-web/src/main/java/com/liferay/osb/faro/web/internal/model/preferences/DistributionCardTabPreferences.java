/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.preferences;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class DistributionCardTabPreferences {

	public String getContext() {
		return _context;
	}

	public String getId() {
		return _id;
	}

	public int getNumberOfBins() {
		return _numberOfBins;
	}

	public String getPropertyId() {
		return _propertyId;
	}

	public String getPropertyType() {
		return _propertyType;
	}

	public String getTitle() {
		return _title;
	}

	public void setContext(String context) {
		_context = context;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setNumberOfBins(int numberOfBins) {
		_numberOfBins = numberOfBins;
	}

	public void setPropertyId(String propertyId) {
		_propertyId = propertyId;
	}

	public void setPropertyType(String propertyType) {
		_propertyType = propertyType;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _context;
	private String _id;
	private int _numberOfBins;
	private String _propertyId;
	private String _propertyType;
	private String _title;

}