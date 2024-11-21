/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Page;
import com.liferay.osb.koroneiki.phloem.rest.client.pagination.Pagination;
import com.liferay.osb.koroneiki.phloem.rest.client.resource.v1_0.ProductPurchaseResource;
import com.liferay.petra.string.StringPool;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class KoroneikiService {

	public List<ProductPurchase> searchProductPurchases(
			String filterString, int page, int pageSize, String sortString)
		throws Exception {

		ProductPurchaseResource productPurchaseResource =
			ProductPurchaseResource.builder(
			).header(
				"API_TOKEN", _koroneikiAuthToken
			).endpoint(
				new URL(_koroneikiURL)
			).build();

		Page<ProductPurchase> productPurchasesPage =
			productPurchaseResource.getProductPurchasesPage(
				StringPool.BLANK, filterString, Pagination.of(page, pageSize),
				sortString);

		if ((productPurchasesPage != null) &&
			(productPurchasesPage.getItems() != null)) {

			return new ArrayList<>(productPurchasesPage.getItems());
		}

		return Collections.emptyList();
	}

	@Value("${liferay.customer.koroneiki.auth.token}")
	private String _koroneikiAuthToken;

	@Value("${liferay.customer.koroneiki.url}")
	private String _koroneikiURL;

}