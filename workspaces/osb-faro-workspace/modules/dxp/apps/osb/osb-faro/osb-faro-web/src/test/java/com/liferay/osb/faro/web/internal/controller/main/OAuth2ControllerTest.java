/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller.main;

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.oauth2.provider.service.OAuth2AuthorizationService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author Eudaldo Alonso
 */
public class OAuth2ControllerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtils.setField(
			_oAuth2Controller, "_oAuth2AuthorizationService",
			_oAuth2AuthorizationService);
	}

	@Test
	public void testRevokeToken() throws Exception {
		OAuth2Authorization oAuth2Authorization = Mockito.mock(
			OAuth2Authorization.class);

		String accessToken = "abc";

		Mockito.when(
			oAuth2Authorization.getAccessTokenContent()
		).thenReturn(
			accessToken
		);

		long oAuth2AuthorizationId = 123;

		Mockito.when(
			oAuth2Authorization.getOAuth2AuthorizationId()
		).thenReturn(
			oAuth2AuthorizationId
		);

		Mockito.when(
			_oAuth2AuthorizationService.getUserOAuth2Authorizations(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenReturn(
			Arrays.asList(oAuth2Authorization)
		);

		_oAuth2Controller.revokeToken(1, accessToken);

		Mockito.verify(
			_oAuth2AuthorizationService
		).revokeOAuth2Authorization(
			oAuth2AuthorizationId
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRevokeTokenWhenTokenIsNotFound() throws Exception {
		Mockito.when(
			_oAuth2AuthorizationService.getUserOAuth2Authorizations(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)
		).thenReturn(
			Collections.emptyList()
		);

		_oAuth2Controller.revokeToken(1, "nonexistent");
	}

	private final OAuth2AuthorizationService _oAuth2AuthorizationService =
		Mockito.mock(OAuth2AuthorizationService.class);
	private final OAuth2Controller _oAuth2Controller = new OAuth2Controller();

}