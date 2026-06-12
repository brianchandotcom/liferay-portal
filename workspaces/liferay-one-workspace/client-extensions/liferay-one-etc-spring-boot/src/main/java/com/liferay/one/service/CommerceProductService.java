/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.client.problem.Problem;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.ProductResource;
import com.liferay.one.constants.CommerceCatalogConstants;
import com.liferay.one.constants.CommerceProductConstants;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Kyle Bischof
 */
@Component
public class CommerceProductService extends OneBaseService {

	public void addOrUpdateProduct(
			String description, String externalReferenceCode, String name)
		throws Exception {

		ProductResource productResource = _buildProductResource();

		Product product = new Product();

		product.setActive(() -> Boolean.TRUE);
		product.setCatalogExternalReferenceCode(
			() ->
				CommerceCatalogConstants.
					EXTERNAL_REFERENCE_CODE_SALESFORCE_CATALOG);
		product.setDescription(
			() -> Collections.singletonMap("en_US", description));
		product.setExternalReferenceCode(() -> externalReferenceCode);
		product.setName(() -> Collections.singletonMap("en_US", name));
		product.setProductType(() -> CommerceProductConstants.TYPE_SIMPLE);

		Sku sku = new Sku();

		sku.setExternalReferenceCode(() -> externalReferenceCode);
		sku.setPublished(() -> Boolean.TRUE);
		sku.setSku(() -> externalReferenceCode);

		product.setSkus(() -> new Sku[] {sku});

		productResource.putProductByExternalReferenceCode(
			externalReferenceCode, product);
	}

	public void deactivateProduct(String externalReferenceCode)
		throws Exception {

		ProductResource productResource = _buildProductResource();

		Product existingProduct = _fetchProduct(
			externalReferenceCode, productResource);

		if (existingProduct == null) {
			return;
		}

		Product product = new Product();

		product.setActive(() -> Boolean.FALSE);
		product.setExternalReferenceCode(() -> externalReferenceCode);

		productResource.patchProductByExternalReferenceCode(
			externalReferenceCode, product);
	}

	private ProductResource _buildProductResource() {
		return ProductResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization()
		).build();
	}

	private Product _fetchProduct(
			String externalReferenceCode, ProductResource productResource)
		throws Exception {

		try {
			return productResource.getProductByExternalReferenceCode(
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