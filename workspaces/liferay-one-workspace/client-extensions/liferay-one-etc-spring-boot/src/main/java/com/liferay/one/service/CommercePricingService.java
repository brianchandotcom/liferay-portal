/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.client.problem.Problem;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceEntryResource;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceListResource;
import com.liferay.one.constants.SalesforceCatalogConstants;
import com.liferay.one.model.SalesforcePricebookEntry;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Kyle Bischof
 */
@Component
public class CommercePricingService extends BaseService {

	public void deletePriceEntry(
			SalesforcePricebookEntry salesforcePricebookEntry)
		throws Exception {

		String priceEntryExternalReferenceCode =
			salesforcePricebookEntry.getId();

		PriceEntryResource priceEntryResource = _priceEntryResource();

		if (!_priceEntryExists(
				priceEntryResource, priceEntryExternalReferenceCode)) {

			return;
		}

		priceEntryResource.deletePriceEntryByExternalReferenceCode(
			priceEntryExternalReferenceCode);
	}

	public void upsertPriceEntry(
			SalesforcePricebookEntry salesforcePricebookEntry)
		throws Exception {

		String priceListExternalReferenceCode =
			SalesforceCatalogConstants.priceListErc(
				salesforcePricebookEntry.getCurrencyIsoCode());

		Long priceListId = _getPriceListId(priceListExternalReferenceCode);

		if (priceListId == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find price list " +
						priceListExternalReferenceCode +
							" for a skipped Salesforce price entry");
			}

			return;
		}

		String product2Id = salesforcePricebookEntry.getProduct2Id();

		Long skuId = _commerceCatalogService.getSkuId(product2Id);

		if (skuId == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find SKU for Salesforce product " + product2Id);
			}

			return;
		}

		PriceEntryResource priceEntryResource = _priceEntryResource();

		PriceEntry priceEntry = _toPriceEntry(
			salesforcePricebookEntry, priceListId, skuId);

		if (_priceEntryExists(
				priceEntryResource, priceEntry.getExternalReferenceCode())) {

			priceEntryResource.patchPriceEntryByExternalReferenceCode(
				priceEntry.getExternalReferenceCode(), priceEntry);
		}
		else {
			priceEntryResource.postPriceListByExternalReferenceCodePriceEntry(
				priceListExternalReferenceCode, priceEntry);
		}
	}

	private PriceList _fetchPriceList(String externalReferenceCode)
		throws Exception {

		PriceListResource priceListResource = _priceListResource();

		try {
			return priceListResource.getPriceListByExternalReferenceCode(
				externalReferenceCode);
		}
		catch (Problem.ProblemException problemException) {
			if (_isNotFound(problemException)) {
				return null;
			}

			throw problemException;
		}
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-one-etc-spring-boot-oahs");
	}

	private Long _getPriceListId(String externalReferenceCode)
		throws Exception {

		Long priceListId = _priceListIds.get(externalReferenceCode);

		if (priceListId != null) {
			return priceListId;
		}

		PriceList priceList = _fetchPriceList(externalReferenceCode);

		if (priceList == null) {
			return null;
		}

		priceListId = priceList.getId();

		_priceListIds.put(externalReferenceCode, priceListId);

		return priceListId;
	}

	private boolean _isNotFound(Problem.ProblemException problemException) {
		Problem problem = problemException.getProblem();

		if ((problem != null) &&
			Objects.equals(HttpStatus.NOT_FOUND.name(), problem.getStatus())) {

			return true;
		}

		return false;
	}

	private boolean _priceEntryExists(
			PriceEntryResource priceEntryResource, String externalReferenceCode)
		throws Exception {

		try {
			priceEntryResource.getPriceEntryByExternalReferenceCode(
				externalReferenceCode);

			return true;
		}
		catch (Problem.ProblemException problemException) {
			if (_isNotFound(problemException)) {
				return false;
			}

			throw problemException;
		}
	}

	private PriceEntryResource _priceEntryResource() {
		return PriceEntryResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private PriceListResource _priceListResource() {
		return PriceListResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private PriceEntry _toPriceEntry(
		SalesforcePricebookEntry salesforcePricebookEntry, long priceListId,
		long skuId) {

		PriceEntry priceEntry = new PriceEntry();

		priceEntry.setActive(salesforcePricebookEntry::isActive);
		priceEntry.setExternalReferenceCode(salesforcePricebookEntry::getId);
		priceEntry.setPrice(salesforcePricebookEntry::getUnitPrice);
		priceEntry.setPriceListId(() -> priceListId);
		priceEntry.setSkuExternalReferenceCode(
			salesforcePricebookEntry::getProduct2Id);
		priceEntry.setSkuId(() -> skuId);

		return priceEntry;
	}

	private static final Log _log = LogFactory.getLog(
		CommercePricingService.class);

	@Autowired
	private CommerceCatalogService _commerceCatalogService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	private final Map<String, Long> _priceListIds = new ConcurrentHashMap<>();

}