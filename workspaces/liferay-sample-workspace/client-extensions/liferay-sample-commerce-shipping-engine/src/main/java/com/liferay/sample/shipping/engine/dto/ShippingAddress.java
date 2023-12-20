/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample.shipping.engine.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Luca Pellizzon
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingAddress {

	@JsonProperty("city")
	public String getCity() {
		return _city;
	}

	@JsonProperty("countryISOCode")
	public String getCountryISOCode() {
		return _countryISOCode;
	}

	@JsonProperty("description")
	public String getDescription() {
		return _description;
	}

	@JsonProperty("externalReferenceCode")
	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	@JsonProperty("id")
	public long getId() {
		return _id;
	}

	@JsonProperty("latitude")
	public double getLatitude() {
		return _latitude;
	}

	@JsonProperty("longitude")
	public double getLongitude() {
		return _longitude;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	@JsonProperty("phoneNumber")
	public String getPhoneNumber() {
		return _phoneNumber;
	}

	@JsonProperty("regionISOCode")
	public String getRegionISOCode() {
		return _regionISOCode;
	}

	@JsonProperty("street1")
	public String getStreet1() {
		return _street1;
	}

	@JsonProperty("street2")
	public String getStreet2() {
		return _street2;
	}

	@JsonProperty("street3")
	public String getStreet3() {
		return _street3;
	}

	@JsonProperty("zip")
	public String getZip() {
		return _zip;
	}

	@JsonProperty("city")
	public void setCity(String city) {
		_city = city;
	}

	@JsonProperty("countryISOCode")
	public void setCountryISOCode(String countryISOCode) {
		_countryISOCode = countryISOCode;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		_description = description;
	}

	@JsonProperty("externalReferenceCode")
	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	@JsonProperty("id")
	public void setId(long id) {
		_id = id;
	}

	@JsonProperty("latitude")
	public void setLatitude(double latitude) {
		_latitude = latitude;
	}

	@JsonProperty("longitude")
	public void setLongitude(double longitude) {
		_longitude = longitude;
	}

	@JsonProperty("name")
	public void setName(String name) {
		_name = name;
	}

	@JsonProperty("phoneNumber")
	public void setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	@JsonProperty("regionISOCode")
	public void setRegionISOCode(String regionISOCode) {
		_regionISOCode = regionISOCode;
	}

	@JsonProperty("street1")
	public void setStreet1(String street1) {
		_street1 = street1;
	}

	@JsonProperty("street2")
	public void setStreet2(String street2) {
		_street2 = street2;
	}

	@JsonProperty("street3")
	public void setStreet3(String street3) {
		_street3 = street3;
	}

	@JsonProperty("zip")
	public void setZip(String zip) {
		_zip = zip;
	}

	@JsonProperty("city")
	private String _city;

	@JsonProperty("countryISOCode")
	private String _countryISOCode;

	@JsonProperty("description")
	private String _description;

	@JsonProperty("externalReferenceCode")
	private String _externalReferenceCode;

	@JsonProperty("id")
	private long _id;

	@JsonProperty("latitude")
	private double _latitude;

	@JsonProperty("longitude")
	private double _longitude;

	@JsonProperty("name")
	private String _name;

	@JsonProperty("phoneNumber")
	private String _phoneNumber;

	@JsonProperty("regionISOCode")
	private String _regionISOCode;

	@JsonProperty("street1")
	private String _street1;

	@JsonProperty("street2")
	private String _street2;

	@JsonProperty("street3")
	private String _street3;

	@JsonProperty("zip")
	private String _zip;

}