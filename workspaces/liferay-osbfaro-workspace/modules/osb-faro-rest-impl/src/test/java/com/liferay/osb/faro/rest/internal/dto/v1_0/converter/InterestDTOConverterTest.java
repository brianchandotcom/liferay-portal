/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.Interest;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Nilton Vieira
 */
public class InterestDTOConverterTest {

	@Test
	public void testToDTO() {
		Assert.assertNull(_interestDTOConverter.toDTO(null, null));

		com.liferay.osb.faro.engine.client.model.Interest engineClientInterest =
			new com.liferay.osb.faro.engine.client.model.Interest();

		engineClientInterest.setDateRecorded(RandomTestUtil.nextDate());
		engineClientInterest.setName(RandomTestUtil.randomString());
		engineClientInterest.setOwnerIdentifier(RandomTestUtil.randomString());
		engineClientInterest.setRelatedPagesCount(RandomTestUtil.randomInt());
		engineClientInterest.setScore(RandomTestUtil.randomDouble());

		Interest interest = _interestDTOConverter.toDTO(
			null, engineClientInterest);

		Assert.assertEquals(
			engineClientInterest.getDateRecorded(), interest.getDateRecorded());
		Assert.assertEquals(
			engineClientInterest.getOwnerIdentifier(),
			interest.getIndividualId());
		Assert.assertEquals(engineClientInterest.getName(), interest.getName());
		Assert.assertEquals(
			Long.valueOf(engineClientInterest.getRelatedPagesCount()),
			interest.getRelatedPagesCount());
		Assert.assertEquals(
			Double.valueOf(engineClientInterest.getScore()),
			interest.getScore());
	}

	private final InterestDTOConverter _interestDTOConverter =
		new InterestDTOConverter();

}