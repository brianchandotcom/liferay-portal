/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.engine.client.model.Metric;
import com.liferay.osb.faro.engine.client.model.Trend;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leslie Wong
 */
public class JSONUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testWriteValueAsStringExcludesNullMetricFields()
		throws Exception {

		Metric metric = new Metric();

		metric.setMetricType("anonymousIndividualsCount");

		Assert.assertEquals(
			"{\"metricType\":\"anonymousIndividualsCount\",\"value\":0.0}",
			JSONUtil.writeValueAsString(metric));
	}

	@Test
	public void testWriteValueAsStringIncludesNonnullMetricFields()
		throws Exception {

		Metric metric = new Metric();

		metric.setMetricType("knownIndividualsCount");
		metric.setPreviousValue(10D);
		metric.setPreviousValueKey("previousKey");

		Trend trend = new Trend();

		trend.setPercentage(new BigDecimal("12.5"));
		trend.setTrendClassification("POSITIVE");

		metric.setTrend(trend);

		metric.setValue(20D);
		metric.setValueKey("key");

		Assert.assertEquals(
			StringBundler.concat(
				"{\"metricType\":\"knownIndividualsCount\",",
				"\"previousValue\":10.0,\"previousValueKey\":\"previousKey\",",
				"\"trend\":{\"percentage\":12.5,",
				"\"trendClassification\":\"POSITIVE\"},\"value\":20.0,",
				"\"valueKey\":\"key\"}"),
			JSONUtil.writeValueAsString(metric));
	}

}