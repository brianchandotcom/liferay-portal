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
public class Account {

	@JsonProperty("emailAddress")
	public String getEmailAddress() {
		return _emailAddress;
	}

	@JsonProperty("externalReferenceCode")
	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	@JsonProperty("id")
	public long getId() {
		return _id;
	}

	@JsonProperty("logoId")
	public long getLogoId() {
		return _logoId;
	}

	@JsonProperty("name")
	public String getName() {
		return _name;
	}

	@JsonProperty("root")
	public long getRoot() {
		return _root;
	}

	@JsonProperty("taxId")
	public long getTaxId() {
		return _taxId;
	}

	@JsonProperty("type")
	public int getType() {
		return _type;
	}

	@JsonProperty("emailAddress")
	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	@JsonProperty("externalReferenceCode")
	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	@JsonProperty("id")
	public void setId(long id) {
		_id = id;
	}

	@JsonProperty("logoId")
	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	@JsonProperty("name")
	public void setName(String name) {
		_name = name;
	}

	@JsonProperty("root")
	public void setRoot(long root) {
		_root = root;
	}

	@JsonProperty("taxId")
	public void setTaxId(long taxId) {
		_taxId = taxId;
	}

	@JsonProperty("type")
	public void setType(int type) {
		_type = type;
	}

	@JsonProperty("emailAddress")
	private String _emailAddress;

	@JsonProperty("externalReferenceCode")
	private String _externalReferenceCode;

	@JsonProperty("id")
	private long _id;

	@JsonProperty("logoId")
	private long _logoId;

	@JsonProperty("name")
	private String _name;

	@JsonProperty("root")
	private long _root;

	@JsonProperty("taxId")
	private long _taxId;

	@JsonProperty("type")
	private int _type;

}