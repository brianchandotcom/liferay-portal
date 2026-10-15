/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.jaxrs.container.request.filter;

import com.liferay.osb.faro.util.FaroThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.container.ContainerRequestContext;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Leslie Wong
 */
public class OriginContainerRequestFilterTest {

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			_originContainerRequestFilter, "_httpServletRequest",
			_httpServletRequest);
	}

	@After
	public void tearDown() {
		FaroThreadLocal.setOrigin(null);
	}

	@Test
	public void testFilterWithForwardedRequest() {
		Mockito.when(
			_httpServletRequest.getAttribute(
				"com.liferay.portal.vulcan.internal.http." +
					"VulcanRequestForwarderHttpServletRequestWrapper")
		).thenReturn(
			true
		);

		_originContainerRequestFilter.filter(_containerRequestContext);

		Assert.assertEquals("mcp", FaroThreadLocal.getOrigin());
	}

	@Test
	public void testFilterWithoutForwardedRequest() {
		_originContainerRequestFilter.filter(_containerRequestContext);

		Assert.assertEquals("rest-client", FaroThreadLocal.getOrigin());
	}

	private final ContainerRequestContext _containerRequestContext =
		Mockito.mock(ContainerRequestContext.class);
	private final HttpServletRequest _httpServletRequest = Mockito.mock(
		HttpServletRequest.class);
	private final OriginContainerRequestFilter _originContainerRequestFilter =
		new OriginContainerRequestFilter();

}