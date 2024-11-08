/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.content.wizard.service;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot.service.BaseService;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import com.liferay.headless.delivery.client.dto.v1_0.BlogPostingImage;
import com.liferay.headless.delivery.client.resource.v1_0.BlogPostingImageResource;
import com.liferay.headless.site.client.dto.v1_0.Site;
import com.liferay.headless.site.client.resource.v1_0.SiteResource;

import java.io.File;

import java.net.URL;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class LiferayService extends BaseService {

	public String createChildWikiPage(String body, String parentWikiPageId) {
		return post(
			_getAuthorization(), body,
			"o/headless-delivery/v1.0/wiki-pages/" + parentWikiPageId +
				"/wiki-pages");
	}

	public String createKeyword(String body, String siteId) {
		return post(
			_getAuthorization(), body,
			"o/headless-admin-taxonomy/v1.0/sites/" + siteId + "/keywords");
	}

	public String createObjectDefinition(String body) {
		return post(
			_getAuthorization(), body,
			"/o/object-admin/v1.0/object-definitions");
	}

	public String createOrganization(String body) {
		return post(
			_getAuthorization(), body,
			"/o/headless-admin-user/v1.0/organizations");
	}

	public String createPage(long siteId, String sitePage) throws Exception {
		return post(
			_getAuthorization(), sitePage,
			"/o/headless-delivery/v1.0/sites/" + siteId + "/site-pages");
	}

	public Site createSite(Site site) throws Exception {
		SiteResource siteResource = _getSiteResource();

		return siteResource.postSite(site);
	}

	public TaxonomyCategory createTaxonomyCategory(
			String parentTaxonomyCategoryId, TaxonomyCategory taxonomyCategory)
		throws Exception {

		TaxonomyCategoryResource taxonomyCategoryResource =
			_getTaxonomyCategoryResource();

		return taxonomyCategoryResource.postTaxonomyCategoryTaxonomyCategory(
			parentTaxonomyCategoryId, taxonomyCategory);
	}

	public TaxonomyVocabulary createTaxonomyVocabulary(
			long siteId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception {

		TaxonomyVocabularyResource taxonomyVocabularyResource =
			_getTaxonomyVocabularyResource();

		return taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
			siteId, taxonomyVocabulary);
	}

	public TaxonomyCategory createTaxonomyVocabularyCategory(
			TaxonomyCategory taxonomyCategory, long vocabularyId)
		throws Exception {

		TaxonomyCategoryResource taxonomyCategoryResource =
			_getTaxonomyCategoryResource();

		return taxonomyCategoryResource.postTaxonomyVocabularyTaxonomyCategory(
			vocabularyId, taxonomyCategory);
	}

	public String createWikiNode(String body, String siteId) {
		return post(
			_getAuthorization(), body,
			"o/headless-delivery/v1.0/sites/" + siteId + "/wiki-nodes");
	}

	public String createWikiPage(String body, String nodeId) {
		return post(
			_getAuthorization(), body,
			"o/headless-delivery/v1.0/wiki-nodes/" + nodeId + "/wiki-pages");
	}

	public String getKeywords() {
		return null;
	}

	public String getTaxonomyCategoriesRanked() {
		return null;
	}

	public UserAccountResource getUserAccountResource() throws Exception {
		return UserAccountResource.builder(
		).endpoint(
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain)
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	public String myUserAccount() {
		return get(
			_getAuthorization(), "/o/headless-admin-user/v1.0/my-user-account");
	}

	public String patchContentWizardSettings(String body) {
		return patch(
			_getAuthorization(), body, "/o/c/k9l6aicontentwizardsettings");
	}

	public String postBlogImage(
			BlogPostingImage blogPostingImage, Map<String, File> map,
			long siteId)
		throws Exception {

		BlogPostingImageResource blogPostingImageResource =
			_getBlogPostingImageResource();

		blogPostingImageResource.postSiteBlogPostingImage(
			siteId, blogPostingImage, map);

		return null;
	}

	public String postContentWizardSettings(String body) {
		return post(
			_getAuthorization(), body, "/o/c/k9l6aicontentwizardsettings");
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-aicontentwizard-oauth-application-headless-server");
	}

	private BlogPostingImageResource _getBlogPostingImageResource()
		throws Exception {

		return BlogPostingImageResource.builder(
		).endpoint(
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain)
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private SiteResource _getSiteResource() throws Exception {
		return SiteResource.builder(
		).endpoint(
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain)
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private TaxonomyCategoryResource _getTaxonomyCategoryResource()
		throws Exception {

		return TaxonomyCategoryResource.builder(
		).endpoint(
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain)
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	private TaxonomyVocabularyResource _getTaxonomyVocabularyResource()
		throws Exception {

		return TaxonomyVocabularyResource.builder(
		).endpoint(
			new URL(lxcDXPServerProtocol + "://" + lxcDXPMainDomain)
		).header(
			HttpHeaders.AUTHORIZATION, _getAuthorization()
		).build();
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}