/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.osb.faro.rest.dto.v1_0.IndividualSegment;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Drew Hoffer
 */
public class IndividualSegmentDTOConverterTest {

	@Test
	public void testToDTO() {
		com.liferay.osb.faro.engine.client.model.IndividualSegment
			engineClientIndividualSegment =
				new com.liferay.osb.faro.engine.client.model.
					IndividualSegment();

		engineClientIndividualSegment.setAccountsCount(
			RandomTestUtil.randomLong());
		engineClientIndividualSegment.setId(RandomTestUtil.randomString());
		engineClientIndividualSegment.setLastMembershipUpdateDate(
			RandomTestUtil.nextDate());
		engineClientIndividualSegment.setName(RandomTestUtil.randomString());

		IndividualSegment individualSegment =
			_individualSegmentDTOConverter.toDTO(
				new FaroDTOConverterContext(
					false, engineClientIndividualSegment.getId(), null),
				engineClientIndividualSegment);

		Assert.assertEquals(
			Long.valueOf(engineClientIndividualSegment.getAccountsCount()),
			individualSegment.getAccountsCount());
		Assert.assertEquals(
			engineClientIndividualSegment.getId(), individualSegment.getId());
		Assert.assertEquals(
			engineClientIndividualSegment.getLastMembershipUpdateDate(),
			individualSegment.getLastMembershipUpdateDate());
		Assert.assertEquals(
			engineClientIndividualSegment.getName(),
			individualSegment.getName());

		Assert.assertNull(
			_individualSegmentDTOConverter.toDTO(
				new FaroDTOConverterContext(false, null, null), null));
	}

	private final IndividualSegmentDTOConverter _individualSegmentDTOConverter =
		new IndividualSegmentDTOConverter();

}