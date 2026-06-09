/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.client.problem.Problem;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.ProductResource;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.SkuResource;
import com.liferay.one.constants.CommerceCatalogConstants;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Kyle Bischof
 */
@Component
public class CommerceCatalogService extends BaseService {

	public void deactivateProduct(String externalReferenceCode)
		throws Exception {

		ProductResource productResource = _buildProductResource();

		Product existingProduct = _fetchProduct(
			productResource, externalReferenceCode);

		if (existingProduct == null) {
			return;
		}

		Product product = new Product();

		product.setActive(() -> Boolean.FALSE);
		product.setExternalReferenceCode(() -> externalReferenceCode);

		productResource.patchProductByExternalReferenceCode(
			externalReferenceCode, product);
	}

	public Long fetchSkuId(String skuExternalReferenceCode) throws Exception {
		SkuResource skuResource = SkuResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();

		try {
			Sku sku = skuResource.getSkuByExternalReferenceCode(
				skuExternalReferenceCode);

			return sku.getId();
		}
		catch (Problem.ProblemException problemException) {
			if (_isNotFound(problemException)) {
				return null;
			}

			throw problemException;
		}
	}

	public void upsertProduct(
			String externalReferenceCode, String name, String description)
		throws Exception {

		ProductResource productResource = _buildProductResource();

		productResource.putProductByExternalReferenceCode(
			externalReferenceCode,
			_toProduct(externalReferenceCode, name, description));
	}

	private ProductResource _buildProductResource() {
		return ProductResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private Product _fetchProduct(
			ProductResource productResource, String externalReferenceCode)
		throws Exception {

		try {
			return productResource.getProductByExternalReferenceCode(
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

	private Map<String, String> _toDefaultLocaleMap(String value) {
		return Collections.singletonMap(
			CommerceCatalogConstants.DEFAULT_LANGUAGE_ID, value);
	}

	private Product _toProduct(
		String externalReferenceCode, String name, String description) {

		Product product = new Product();

		Sku sku = new Sku();

		sku.setExternalReferenceCode(() -> externalReferenceCode);
		sku.setPublished(() -> Boolean.TRUE);
		sku.setSku(() -> externalReferenceCode);

		product.setActive(() -> Boolean.TRUE);
		product.setCatalogExternalReferenceCode(
			() -> CommerceCatalogConstants.SALESFORCE_CATALOG);
		product.setDescription(() -> _toDefaultLocaleMap(description));
		product.setExternalReferenceCode(() -> externalReferenceCode);
		product.setName(() -> _toDefaultLocaleMap(name));
		product.setProductType(
			() -> CommerceCatalogConstants.PRODUCT_TYPE_SIMPLE);
		product.setSkus(() -> new Sku[] {sku});

		return product;
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}