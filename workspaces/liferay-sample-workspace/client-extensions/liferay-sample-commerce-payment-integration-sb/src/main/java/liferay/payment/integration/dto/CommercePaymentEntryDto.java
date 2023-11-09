/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package liferay.payment.integration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

/**
 * @author Crescenzo Rega
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommercePaymentEntryDto {

	@JsonProperty("amount")
	public Double getAmount() {
		return _amount;
	}

	@JsonProperty("callbackURL")
	public String getCallbackURL() {
		return _callbackURL;
	}

	@JsonProperty("cancelURL")
	public String getCancelURL() {
		return _cancelURL;
	}

	@JsonProperty("classNameId")
	public String getClassNameId() {
		return _classNameId;
	}

	@JsonProperty("classPK")
	public String getClassPK() {
		return _classPK;
	}

	@JsonProperty("commerceChannelId")
	public String getCommerceChannelId() {
		return _commerceChannelId;
	}

	@JsonProperty("commercePaymentEntryId")
	public String getCommercePaymentEntryId() {
		return _commercePaymentEntryId;
	}

	@JsonProperty("companyId")
	public String getCompanyId() {
		return _companyId;
	}

	@JsonProperty("createDate")
	public Long getCreateDate() {
		return _createDate;
	}

	@JsonProperty("currencyCode")
	public String getCurrencyCode() {
		return _currencyCode;
	}

	@JsonProperty("errorMessages")
	public String getErrorMessages() {
		return _errorMessages;
	}

	@JsonProperty("languageId")
	public String getLanguageId() {
		return _languageId;
	}

	@JsonProperty("modifiedDate")
	public Long getModifiedDate() {
		return _modifiedDate;
	}

	@JsonProperty("mvccVersion")
	public String getMvccVersion() {
		return _mvccVersion;
	}

	@JsonProperty("paymentIntegrationKey")
	public String getPaymentIntegrationKey() {
		return _paymentIntegrationKey;
	}

	@JsonProperty("paymentIntegrationType")
	public Integer getPaymentIntegrationType() {
		return _paymentIntegrationType;
	}

	@JsonProperty("paymentStatus")
	public Integer getPaymentStatus() {
		return _paymentStatus;
	}

	@JsonProperty("redirectURL")
	public String getRedirectURL() {
		return _redirectURL;
	}

	@JsonProperty("transactionCode")
	public String getTransactionCode() {
		return _transactionCode;
	}

	@JsonProperty("userId")
	public String getUserId() {
		return _userId;
	}

	@JsonProperty("userName")
	public String getUserName() {
		return _userName;
	}

	@JsonProperty("amount")
	public void setAmount(Double amount) {
		_amount = amount;
	}

	@JsonProperty("callbackURL")
	public void setCallbackURL(String callbackURL) {
		_callbackURL = callbackURL;
	}

	@JsonProperty("cancelURL")
	public void setCancelURL(String cancelURL) {
		_cancelURL = cancelURL;
	}

	@JsonProperty("classNameId")
	public void setClassNameId(String classNameId) {
		_classNameId = classNameId;
	}

	@JsonProperty("classPK")
	public void setClassPK(String classPK) {
		_classPK = classPK;
	}

	@JsonProperty("commerceChannelId")
	public void setCommerceChannelId(String commerceChannelId) {
		_commerceChannelId = commerceChannelId;
	}

	@JsonProperty("commercePaymentEntryId")
	public void setCommercePaymentEntryId(String commercePaymentEntryId) {
		_commercePaymentEntryId = commercePaymentEntryId;
	}

	@JsonProperty("companyId")
	public void setCompanyId(String companyId) {
		_companyId = companyId;
	}

	@JsonProperty("createDate")
	public void setCreateDate(Long createDate) {
		_createDate = createDate;
	}

	@JsonProperty("currencyCode")
	public void setCurrencyCode(String currencyCode) {
		_currencyCode = currencyCode;
	}

	@JsonProperty("errorMessages")
	public void setErrorMessages(String errorMessages) {
		_errorMessages = errorMessages;
	}

	@JsonProperty("languageId")
	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	@JsonProperty("modifiedDate")
	public void setModifiedDate(Long modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@JsonProperty("mvccVersion")
	public void setMvccVersion(String mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JsonProperty("paymentIntegrationKey")
	public void setPaymentIntegrationKey(String paymentIntegrationKey) {
		_paymentIntegrationKey = paymentIntegrationKey;
	}

	@JsonProperty("paymentIntegrationType")
	public void setPaymentIntegrationType(Integer paymentIntegrationType) {
		_paymentIntegrationType = paymentIntegrationType;
	}

	@JsonProperty("paymentStatus")
	public void setPaymentStatus(Integer paymentStatus) {
		_paymentStatus = paymentStatus;
	}

	@JsonProperty("redirectURL")
	public void setRedirectURL(String redirectURL) {
		_redirectURL = redirectURL;
	}

	@JsonProperty("transactionCode")
	public void setTransactionCode(String transactionCode) {
		_transactionCode = transactionCode;
	}

	@JsonProperty("userId")
	public void setUserId(String userId) {
		_userId = userId;
	}

	@JsonProperty("userName")
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public String toString() {
		return new StringJoiner(
			", ", CommercePaymentEntryDto.class.getSimpleName() + "[", "]"
		).add(
			"amount=" + _amount
		).add(
			"callbackURL='" + _callbackURL + "'"
		).add(
			"cancelURL='" + _cancelURL + "'"
		).add(
			"classNameId='" + _classNameId + "'"
		).add(
			"classPK='" + _classPK + "'"
		).add(
			"commerceChannelId='" + _commerceChannelId + "'"
		).add(
			"commercePaymentEntryId='" + _commercePaymentEntryId + "'"
		).add(
			"companyId='" + _companyId + "'"
		).add(
			"createDate=" + _createDate
		).add(
			"currencyCode='" + _currencyCode + "'"
		).add(
			"errorMessages='" + _errorMessages + "'"
		).add(
			"languageId='" + _languageId + "'"
		).add(
			"modifiedDate=" + _modifiedDate
		).add(
			"mvccVersion='" + _mvccVersion + "'"
		).add(
			"paymentIntegrationKey='" + _paymentIntegrationKey + "'"
		).add(
			"paymentIntegrationType=" + _paymentIntegrationType
		).add(
			"paymentStatus=" + _paymentStatus
		).add(
			"redirectURL='" + _redirectURL + "'"
		).add(
			"transactionCode='" + _transactionCode + "'"
		).add(
			"userId='" + _userId + "'"
		).add(
			"userName='" + _userName + "'"
		).toString();
	}

	@JsonProperty("amount")
	private Double _amount;

	@JsonProperty("callbackURL")
	private String _callbackURL;

	@JsonProperty("cancelURL")
	private String _cancelURL;

	@JsonProperty("classNameId")
	private String _classNameId;

	@JsonProperty("classPK")
	private String _classPK;

	@JsonProperty("commerceChannelId")
	private String _commerceChannelId;

	@JsonProperty("commercePaymentEntryId")
	private String _commercePaymentEntryId;

	@JsonProperty("companyId")
	private String _companyId;

	@JsonProperty("createDate")
	private Long _createDate;

	@JsonProperty("currencyCode")
	private String _currencyCode;

	@JsonProperty("errorMessages")
	private String _errorMessages;

	@JsonProperty("languageId")
	private String _languageId;

	@JsonProperty("modifiedDate")
	private Long _modifiedDate;

	@JsonProperty("mvccVersion")
	private String _mvccVersion;

	@JsonProperty("paymentIntegrationKey")
	private String _paymentIntegrationKey;

	@JsonProperty("paymentIntegrationType")
	private Integer _paymentIntegrationType;

	@JsonProperty("paymentStatus")
	private Integer _paymentStatus;

	@JsonProperty("redirectURL")
	private String _redirectURL;

	@JsonProperty("transactionCode")
	private String _transactionCode;

	@JsonProperty("userId")
	private String _userId;

	@JsonProperty("userName")
	private String _userName;

}