/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample.shipping.engine.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Luca Pellizzon
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

	@JsonProperty("createDate")
	public Date getCreateDate() {
		return _createDate;
	}

	@JsonProperty("currencyCode")
	public String getCurrencyCode() {
		return _currencyCode;
	}

	@JsonProperty("deliveryTermDescription")
	public String getDeliveryTermDescription() {
		return _deliveryTermDescription;
	}

	@JsonProperty("deliveryTermId")
	public long getDeliveryTermId() {
		return _deliveryTermId;
	}

	@JsonProperty("deliveryTermName")
	public String getDeliveryTermName() {
		return _deliveryTermName;
	}

	@JsonProperty("externalReferenceCode")
	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	@JsonProperty("id")
	public long getId() {
		return _id;
	}

	@JsonProperty("lastPriceUpdateDate")
	public Date getLastPriceUpdateDate() {
		return _lastPriceUpdateDate;
	}

	@JsonProperty("modifiedDate")
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@JsonProperty("orderDate")
	public Date getOrderDate() {
		return _orderDate;
	}

	@JsonProperty("orderStatus")
	public int getOrderStatus() {
		return _orderStatus;
	}

	@JsonProperty("orderTypeExternalReferenceCode")
	public String getOrderTypeExternalReferenceCode() {
		return _orderTypeExternalReferenceCode;
	}

	@JsonProperty("orderTypeId")
	public long getOrderTypeId() {
		return _orderTypeId;
	}

	@JsonProperty("purchaseOrderNumber")
	public String getPurchaseOrderNumber() {
		return _purchaseOrderNumber;
	}

	@JsonProperty("requestedDeliveryDate")
	public Date getRequestedDeliveryDate() {
		return _requestedDeliveryDate;
	}

	@JsonProperty("shippingAddressId")
	public long getShippingAddressId() {
		return _shippingAddressId;
	}

	@JsonProperty("createDate")
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JsonProperty("currencyCode")
	public void setCurrencyCode(String currencyCode) {
		_currencyCode = currencyCode;
	}

	@JsonProperty("deliveryTermDescription")
	public void setDeliveryTermDescription(String deliveryTermDescription) {
		_deliveryTermDescription = deliveryTermDescription;
	}

	@JsonProperty("deliveryTermId")
	public void setDeliveryTermId(long deliveryTermId) {
		_deliveryTermId = deliveryTermId;
	}

	@JsonProperty("deliveryTermName")
	public void setDeliveryTermName(String deliveryTermName) {
		_deliveryTermName = deliveryTermName;
	}

	@JsonProperty("externalReferenceCode")
	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	@JsonProperty("id")
	public void setId(long id) {
		_id = id;
	}

	@JsonProperty("lastPriceUpdateDate")
	public void setLastPriceUpdateDate(Date lastPriceUpdateDate) {
		_lastPriceUpdateDate = lastPriceUpdateDate;
	}

	@JsonProperty("modifiedDate")
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@JsonProperty("orderDate")
	public void setOrderDate(Date orderDate) {
		_orderDate = orderDate;
	}

	@JsonProperty("orderStatus")
	public void setOrderStatus(int orderStatus) {
		_orderStatus = orderStatus;
	}

	@JsonProperty("orderTypeExternalReferenceCode")
	public void setOrderTypeExternalReferenceCode(
		String orderTypeExternalReferenceCode) {

		_orderTypeExternalReferenceCode = orderTypeExternalReferenceCode;
	}

	@JsonProperty("orderTypeId")
	public void setOrderTypeId(long orderTypeId) {
		_orderTypeId = orderTypeId;
	}

	@JsonProperty("purchaseOrderNumber")
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		_purchaseOrderNumber = purchaseOrderNumber;
	}

	@JsonProperty("requestedDeliveryDate")
	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		_requestedDeliveryDate = requestedDeliveryDate;
	}

	@JsonProperty("shippingAddressId")
	public void setShippingAddressId(long shippingAddressId) {
		_shippingAddressId = shippingAddressId;
	}

	@JsonProperty("createDate")
	private Date _createDate;

	@JsonProperty("currencyCode")
	private String _currencyCode;

	@JsonProperty("deliveryTermDescription")
	private String _deliveryTermDescription;

	@JsonProperty("deliveryTermId")
	private long _deliveryTermId;

	@JsonProperty("deliveryTermName")
	private String _deliveryTermName;

	@JsonProperty("externalReferenceCode")
	private String _externalReferenceCode;

	@JsonProperty("id")
	private long _id;

	@JsonProperty("lastPriceUpdateDate")
	private Date _lastPriceUpdateDate;

	@JsonProperty("modifiedDate")
	private Date _modifiedDate;

	@JsonProperty("orderDate")
	private Date _orderDate;

	@JsonProperty("orderStatus")
	private int _orderStatus;

	@JsonProperty("orderTypeExternalReferenceCode")
	private String _orderTypeExternalReferenceCode;

	@JsonProperty("orderTypeId")
	private long _orderTypeId;

	@JsonProperty("purchaseOrderNumber")
	private String _purchaseOrderNumber;

	@JsonProperty("requestedDeliveryDate")
	private Date _requestedDeliveryDate;

	@JsonProperty("shippingAddressId")
	private long _shippingAddressId;

}