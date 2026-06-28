/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationLocalService;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

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
		ReflectionTestUtil.setFieldValue(
			_oAuth2FaroController, "_oAuth2AuthorizationLocalService",
			_oAuth2AuthorizationLocalService);
		ReflectionTestUtil.setFieldValue(
			_oAuth2FaroController, "_oAuth2AuthorizationService",
			_oAuth2AuthorizationService);
	}

	@Test
	public void testRevokeToken() throws Exception {
		OAuth2Authorization oAuth2Authorization = Mockito.mock(
			OAuth2Authorization.class);

		long oAuth2AuthorizationId = 123;

		Mockito.when(
			oAuth2Authorization.getOAuth2AuthorizationId()
		).thenReturn(
			oAuth2AuthorizationId
		);

		Mockito.when(
			_oAuth2AuthorizationLocalService.
				fetchOAuth2AuthorizationByAccessTokenContent("abc")
		).thenReturn(
			oAuth2Authorization
		);

		_oAuth2FaroController.revokeToken(1, "abc");

		Mockito.verify(
			_oAuth2AuthorizationService
		).revokeOAuth2Authorization(
			oAuth2AuthorizationId
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRevokeTokenWhenTokenIsNotFound() throws Exception {
		_oAuth2FaroController.revokeToken(1, "nonexistent");
	}

	private final OAuth2AuthorizationLocalService
		_oAuth2AuthorizationLocalService = Mockito.mock(
			OAuth2AuthorizationLocalService.class);
	private final OAuth2AuthorizationService _oAuth2AuthorizationService =
		Mockito.mock(OAuth2AuthorizationService.class);
	private final OAuth2FaroController _oAuth2FaroController =
		new OAuth2FaroController();

}