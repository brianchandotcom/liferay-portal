/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

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

	@After
	public void tearDown() {
		PortalBeanLocatorUtil.setBeanLocator(null);
	}

	@Test
	public void testBeanLocatorHasNotBeenSet() {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					PortalBeanLocatorUtil.class.getName(), Level.SEVERE)) {

			try {
				PortalBeanLocatorUtil.locate("beanName");

				Assert.fail();
			}
			catch (BeanLocatorException ble) {
				Assert.assertEquals("BeanLocator is not set", ble.getMessage());

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				Assert.assertEquals(
					logRecords.toString(), 1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Assert.assertEquals(
					"BeanLocator is null", logRecord.getMessage());
			}
		}
	}

	@Test
	public void testLocateExistingBean() {
		when(
			_beanLocator.locate("existingBean")
		).thenReturn(
			new String("existingBean")
		);

		PortalBeanLocatorUtil.setBeanLocator(_beanLocator);

		String bean = (String)PortalBeanLocatorUtil.locate("existingBean");

		Assert.assertNotNull(bean);
		Assert.assertEquals("existingBean", bean);

		Mockito.verify(_beanLocator, Mockito.times(1));
	}

	@Test
	public void testLocateNonexistingBean() {
		when(
			_beanLocator.locate("nonExistingBean")
		).thenReturn(
			null
		);

		PortalBeanLocatorUtil.setBeanLocator(_beanLocator);

		String bean = (String)PortalBeanLocatorUtil.locate("nonExistingBean");

		Assert.assertNull(bean);

		Mockito.verify(_beanLocator, Mockito.times(1));
	}

	@Mock
	private BeanLocator _beanLocator;

}