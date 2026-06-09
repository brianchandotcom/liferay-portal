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

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Kyle Bischof
 */
@Component
public class CommercePricingService extends BaseService {

	public void deletePriceEntry(String externalReferenceCode)
		throws Exception {

		PriceEntryResource priceEntryResource = _buildPriceEntryResource();

		PriceEntry priceEntry = _fetchPriceEntry(
			priceEntryResource, externalReferenceCode);

		if (priceEntry == null) {
			return;
		}

		priceEntryResource.deletePriceEntryByExternalReferenceCode(
			externalReferenceCode);
	}

	public Long fetchPriceListId(String priceListExternalReferenceCode)
		throws Exception {

		PriceListResource priceListResource = PriceListResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();

		try {
			PriceList priceList =
				priceListResource.getPriceListByExternalReferenceCode(
					priceListExternalReferenceCode);

			return priceList.getId();
		}
		catch (Problem.ProblemException problemException) {
			if (_isNotFound(problemException)) {
				return null;
			}

			throw problemException;
		}
	}

	public void upsertPriceEntry(
			String externalReferenceCode, String priceListExternalReferenceCode,
			long priceListId, long skuId, double price, boolean active)
		throws Exception {

		PriceEntryResource priceEntryResource = _buildPriceEntryResource();

		PriceEntry existingPriceEntry = _fetchPriceEntry(
			priceEntryResource, externalReferenceCode);

		PriceEntry priceEntry = _toPriceEntry(
			externalReferenceCode, priceListId, skuId, price, active);

		if (existingPriceEntry != null) {
			priceEntryResource.patchPriceEntryByExternalReferenceCode(
				externalReferenceCode, priceEntry);
		}
		else {
			priceEntryResource.postPriceListByExternalReferenceCodePriceEntry(
				priceListExternalReferenceCode, priceEntry);
		}
	}

	private PriceEntryResource _buildPriceEntryResource() {
		return PriceEntryResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private PriceEntry _fetchPriceEntry(
			PriceEntryResource priceEntryResource, String externalReferenceCode)
		throws Exception {

		try {
			return priceEntryResource.getPriceEntryByExternalReferenceCode(
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

	private boolean _isNotFound(Problem.ProblemException problemException) {
		Problem problem = problemException.getProblem();

		if ((problem != null) &&
			Objects.equals(HttpStatus.NOT_FOUND.name(), problem.getStatus())) {

			return true;
		}

		return false;
	}

	private PriceEntry _toPriceEntry(
		String externalReferenceCode, long priceListId, long skuId,
		double price, boolean active) {

		PriceEntry priceEntry = new PriceEntry();

		priceEntry.setActive(() -> active);
		priceEntry.setExternalReferenceCode(() -> externalReferenceCode);
		priceEntry.setPrice(() -> price);
		priceEntry.setPriceListId(() -> priceListId);
		priceEntry.setSkuId(() -> skuId);

		return priceEntry;
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}