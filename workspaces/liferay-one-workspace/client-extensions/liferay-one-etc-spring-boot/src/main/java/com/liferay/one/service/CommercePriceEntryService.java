/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.client.problem.Problem;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceEntryResource;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Kyle Bischof
 */
@Component
public class CommercePriceEntryService extends OneBaseService {

	public void addOrUpdatePriceEntry(
			boolean active, String externalReferenceCode, double price,
			String priceListExternalReferenceCode, long priceListId, long skuId)
		throws Exception {

		PriceEntryResource priceEntryResource = _buildPriceEntryResource();

		PriceEntry existingPriceEntry = _fetchPriceEntry(
			externalReferenceCode, priceEntryResource);

		PriceEntry priceEntry = new PriceEntry();

		priceEntry.setActive(() -> active);
		priceEntry.setExternalReferenceCode(() -> externalReferenceCode);
		priceEntry.setPrice(() -> price);
		priceEntry.setPriceListId(() -> priceListId);
		priceEntry.setSkuId(() -> skuId);

		if (existingPriceEntry != null) {
			priceEntryResource.patchPriceEntryByExternalReferenceCode(
				externalReferenceCode, priceEntry);
		}
		else {
			priceEntryResource.postPriceListByExternalReferenceCodePriceEntry(
				priceListExternalReferenceCode, priceEntry);
		}
	}

	public void deletePriceEntry(String externalReferenceCode)
		throws Exception {

		PriceEntryResource priceEntryResource = _buildPriceEntryResource();

		PriceEntry priceEntry = _fetchPriceEntry(
			externalReferenceCode, priceEntryResource);

		if (priceEntry == null) {
			return;
		}

		priceEntryResource.deletePriceEntryByExternalReferenceCode(
			externalReferenceCode);
	}

	private PriceEntryResource _buildPriceEntryResource() {
		return PriceEntryResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization()
		).build();
	}

	private PriceEntry _fetchPriceEntry(
			String externalReferenceCode, PriceEntryResource priceEntryResource)
		throws Exception {

		try {
			return priceEntryResource.getPriceEntryByExternalReferenceCode(
				externalReferenceCode);
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			if ((problem != null) && isNotFound(problem.getStatus())) {
				return null;
			}

			throw problemException;
		}
	}

}