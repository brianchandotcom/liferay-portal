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
import com.liferay.one.constants.SalesforceCatalogConstants;
import com.liferay.one.model.SalesforceProduct2;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author Kyle Bischof
 */
@Component
public class CommerceCatalogService extends BaseService {

	public void deactivateProduct(String product2Id) throws Exception {
		ProductResource productResource = _productResource();

		if (!_productExists(productResource, product2Id)) {
			return;
		}

		Product product = new Product();

		product.setActive(() -> Boolean.FALSE);
		product.setExternalReferenceCode(() -> product2Id);

		productResource.patchProductByExternalReferenceCode(
			product2Id, product);
	}

	public Long getSkuId(String product2Id) throws Exception {
		Long skuId = _skuIds.get(product2Id);

		if (skuId != null) {
			return skuId;
		}

		SkuResource skuResource = _skuResource();

		try {
			Sku sku = skuResource.getSkuByExternalReferenceCode(product2Id);

			skuId = sku.getId();

			_skuIds.put(product2Id, skuId);

			return skuId;
		}
		catch (Problem.ProblemException problemException) {
			if (_isNotFound(problemException)) {
				return null;
			}

			throw problemException;
		}
	}

	public void upsertProduct(SalesforceProduct2 salesforceProduct2)
		throws Exception {

		ProductResource productResource = _productResource();

		productResource.putProductByExternalReferenceCode(
			salesforceProduct2.getId(), _toProduct(salesforceProduct2));
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

	private boolean _productExists(
			ProductResource productResource, String externalReferenceCode)
		throws Exception {

		try {
			productResource.getProductByExternalReferenceCode(
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

	private ProductResource _productResource() {
		return ProductResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private SkuResource _skuResource() {
		return SkuResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private Map<String, String> _toDefaultLocaleMap(String value) {
		return Collections.singletonMap(
			SalesforceCatalogConstants.DEFAULT_LANGUAGE_ID, value);
	}

	private Product _toProduct(SalesforceProduct2 salesforceProduct2) {
		Product product = new Product();

		Sku sku = new Sku();

		sku.setExternalReferenceCode(salesforceProduct2::getId);
		sku.setPublished(() -> Boolean.TRUE);
		sku.setSku(salesforceProduct2::getId);

		product.setActive(() -> Boolean.TRUE);
		product.setCatalogExternalReferenceCode(
			() -> SalesforceCatalogConstants.SALESFORCE_CATALOG);
		product.setDescription(
			() -> _toDefaultLocaleMap(salesforceProduct2.getDescription()));
		product.setExternalReferenceCode(salesforceProduct2::getId);
		product.setName(
			() -> _toDefaultLocaleMap(salesforceProduct2.getName()));
		product.setProductType(() -> SalesforceCatalogConstants.PRODUCT_TYPE);
		product.setSkus(() -> new Sku[] {sku});

		return product;
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	private final Map<String, Long> _skuIds = new ConcurrentHashMap<>();

}