/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.util.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Miguel Pastor
 */
@RunWith(PowerMockRunner.class)
public class PortalBeanLocatorUtilTest extends PowerMockito {

	@Test
	public void testBeanLocatorHasNotBeenSet() {
		try {
			PortalBeanLocatorUtil.locate("beanName");
		}
		catch (BeanLocatorException ble) {
			Assert.assertTrue(true);
			return;
		}

		Assert.fail("No bean locator should be inyected");
	}

	@Test
	public void testLocateExistingBean() {
		when(
			_beanLocator.locate("existingBean")
		).thenReturn(
			new String("existingBean")
		);

		PortalBeanLocatorUtil.setBeanLocator(_beanLocator);

		String bean = (String) PortalBeanLocatorUtil.locate("existingBean");

		Assert.assertNotNull(bean);
		Assert.assertEquals("existingBean", bean);

		Mockito.verify(_beanLocator, Mockito.times(1));
	}

	@Test
	public void testLocateNonExistingBean() {
		when(
			_beanLocator.locate("nonExistingBean")
		).thenReturn(
			null
		);

		PortalBeanLocatorUtil.setBeanLocator(_beanLocator);

		String bean = (String) PortalBeanLocatorUtil.locate("nonExistingBean");

		Assert.assertNull(bean);

		Mockito.verify(_beanLocator, Mockito.times(1));
	}

	@After
	public void tearDown() {
		PortalBeanLocatorUtil.setBeanLocator(null);
	}

	@Mock
	private BeanLocator _beanLocator;

}