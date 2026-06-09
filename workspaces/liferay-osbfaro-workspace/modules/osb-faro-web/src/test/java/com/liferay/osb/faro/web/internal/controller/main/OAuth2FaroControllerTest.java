/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.oauth.client.LocalOAuthClient;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationService;
import com.liferay.osb.faro.web.internal.model.display.main.TokenDisplay;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Eudaldo Alonso
 */
public class OAuth2FaroControllerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(
			_oAuth2FaroController, "_jsonFactory", new JSONFactoryImpl());
		ReflectionTestUtils.setField(
			_oAuth2FaroController, "_localOAuthClient", _localOAuthClient);
		ReflectionTestUtils.setField(
			_oAuth2FaroController, "_oAuth2ApplicationLocalService",
			_oAuth2ApplicationLocalService);
		ReflectionTestUtils.setField(
			_oAuth2FaroController, "_oAuth2AuthorizationLocalService",
			_oAuth2AuthorizationLocalService);
		ReflectionTestUtils.setField(
			_oAuth2FaroController, "_oAuth2AuthorizationService",
			_oAuth2AuthorizationService);

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionChecker.getUser()
		).thenReturn(
			Mockito.mock(User.class)
		);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@Test
	public void testNewToken() throws Exception {
		OAuth2Application oAuth2Application = _mockOAuth2Application();

		Mockito.when(
			_localOAuthClient.requestTokens(oAuth2Application, 100L)
		).thenReturn(
			"{\"access_token\": \"abc\"}"
		);

		OAuth2Authorization oAuth2Authorization = _mockOAuth2Authorization(
			"abc");

		Mockito.when(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent("abc")
		).thenReturn(
			oAuth2Authorization
		);

		TokenDisplay tokenDisplay = _oAuth2FaroController.newToken(
			1, null, "demandbase", null);

		Assert.assertEquals("abc", tokenDisplay.getToken());
	}

	@Test(expected = PortalException.class)
	public void testNewTokenWhenAccessTokenIsNotCreated() throws Exception {
		OAuth2Application oAuth2Application = _mockOAuth2Application();

		Mockito.when(
			_localOAuthClient.requestTokens(oAuth2Application, 100L)
		).thenReturn(
			null
		);

		_oAuth2FaroController.newToken(1, null, "demandbase", null);
	}

	@Test(expected = PortalException.class)
	public void testNewTokenWhenOAuth2AuthorizationIsNotFound()
		throws Exception {

		OAuth2Application oAuth2Application = _mockOAuth2Application();

		Mockito.when(
			_localOAuthClient.requestTokens(oAuth2Application, 100L)
		).thenReturn(
			"{\"access_token\": \"abc\"}"
		);

		Mockito.when(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent("abc")
		).thenReturn(
			null
		);

		_oAuth2FaroController.newToken(1, null, "demandbase", null);
	}

	@Test
	public void testRevokeToken() throws Exception {
		String accessToken = "abc";

		OAuth2Authorization oAuth2Authorization = _mockOAuth2Authorization(
			accessToken);

		long oAuth2AuthorizationId = 123;

		Mockito.when(
			oAuth2Authorization.getOAuth2AuthorizationId()
		).thenReturn(
			oAuth2AuthorizationId
		);

		Mockito.when(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent(accessToken)
		).thenReturn(
			oAuth2Authorization
		);

		_oAuth2FaroController.revokeToken(1, accessToken);

		Mockito.verify(
			_oAuth2AuthorizationService
		).revokeOAuth2Authorization(
			oAuth2AuthorizationId
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRevokeTokenWhenTokenIsNotFound() throws Exception {
		Mockito.when(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent("nonexistent")
		).thenReturn(
			null
		);

		_oAuth2FaroController.revokeToken(1, "nonexistent");
	}

	private OAuth2Application _mockOAuth2Application() throws Exception {
		OAuth2Application oAuth2Application = Mockito.mock(
			OAuth2Application.class);

		Mockito.when(
			_oAuth2ApplicationLocalService.fetchOAuth2Application(
				Mockito.anyLong(), Mockito.eq("DEMANDBASE"))
		).thenReturn(
			oAuth2Application
		);

		Mockito.when(
			oAuth2Application.getClientCredentialUserId()
		).thenReturn(
			100L
		);

		Mockito.when(
			oAuth2Application.getOAuth2ApplicationId()
		).thenReturn(
			200L
		);

		Mockito.when(
			_oAuth2AuthorizationService.getApplicationOAuth2Authorizations(
				200L, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenReturn(
			Collections.emptyList()
		);

		return oAuth2Application;
	}

	private OAuth2Authorization _mockOAuth2Authorization(String accessToken) {
		OAuth2Authorization oAuth2Authorization = Mockito.mock(
			OAuth2Authorization.class);

		Mockito.when(
			oAuth2Authorization.getAccessTokenContent()
		).thenReturn(
			accessToken
		);

		Date date = new Date();

		Mockito.when(
			oAuth2Authorization.getAccessTokenCreateDate()
		).thenReturn(
			date
		);

		Mockito.when(
			oAuth2Authorization.getAccessTokenExpirationDate()
		).thenReturn(
			date
		);

		Mockito.when(
			oAuth2Authorization.getCreateDate()
		).thenReturn(
			date
		);

		Mockito.when(
			oAuth2Authorization.getExpandoBridge()
		).thenReturn(
			Mockito.mock(ExpandoBridge.class)
		);

		return oAuth2Authorization;
	}

	private final LocalOAuthClient _localOAuthClient = Mockito.mock(
		LocalOAuthClient.class);
	private final OAuth2ApplicationLocalService _oAuth2ApplicationLocalService =
		Mockito.mock(OAuth2ApplicationLocalService.class);
	private final OAuth2AuthorizationLocalService
		_oAuth2AuthorizationLocalService = Mockito.mock(
			OAuth2AuthorizationLocalService.class);
	private final OAuth2AuthorizationService _oAuth2AuthorizationService =
		Mockito.mock(OAuth2AuthorizationService.class);
	private final OAuth2FaroController _oAuth2FaroController =
		new OAuth2FaroController();

}