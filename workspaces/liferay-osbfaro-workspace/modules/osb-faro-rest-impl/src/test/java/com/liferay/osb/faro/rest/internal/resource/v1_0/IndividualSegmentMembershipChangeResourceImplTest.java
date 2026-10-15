/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Leslie Wong
 */
public class IndividualSegmentMembershipChangeResourceImplTest {

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_contactsEngineClient.getIndividualSegmentMembershipChanges(
				Mockito.any(), Mockito.anyString(), Mockito.any(),
				Mockito.any(), Mockito.any(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.any())
		).thenReturn(
			new Results<>()
		);

		ReflectionTestUtil.setFieldValue(
			_individualSegmentMembershipChangeResourceImpl,
			"_contactsEngineClient", _contactsEngineClient);
		ReflectionTestUtil.setFieldValue(
			_individualSegmentMembershipChangeResourceImpl,
			"_faroProjectLocalService",
			Mockito.mock(FaroProjectLocalService.class));
	}

	@Test
	public void testGetWorkspaceGroupIndividualSegmentMembershipChangesPageWithRangeEnd()
		throws Exception {

		IllegalArgumentException illegalArgumentException = Assert.assertThrows(
			IllegalArgumentException.class,
			() ->
				_individualSegmentMembershipChangeResourceImpl.
					getWorkspaceGroupIndividualSegmentMembershipChangesPage(
						RandomTestUtil.randomLong(),
						RandomTestUtil.randomString(), null, "2026-01-31", null,
						null, null,
						Pagination.of(
							RandomTestUtil.randomInt(),
							RandomTestUtil.randomInt()),
						null));

		Assert.assertEquals(
			"\"rangeEnd\" and \"rangeStart\" must be set together",
			illegalArgumentException.getMessage());

		Mockito.verifyNoInteractions(_contactsEngineClient);
	}

	@Test
	public void testGetWorkspaceGroupIndividualSegmentMembershipChangesPageWithRangeEndAndRangeStart()
		throws Exception {

		_individualSegmentMembershipChangeResourceImpl.
			getWorkspaceGroupIndividualSegmentMembershipChangesPage(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				null, "2026-01-31", null, "2026-01-01", null,
				Pagination.of(
					RandomTestUtil.randomInt(), RandomTestUtil.randomInt()),
				null);

		ArgumentCaptor<Date> endDateArgumentCaptor = ArgumentCaptor.forClass(
			Date.class);
		ArgumentCaptor<Date> startDateArgumentCaptor = ArgumentCaptor.forClass(
			Date.class);

		Mockito.verify(
			_contactsEngineClient
		).getIndividualSegmentMembershipChanges(
			Mockito.any(), Mockito.anyString(), Mockito.any(),
			startDateArgumentCaptor.capture(), endDateArgumentCaptor.capture(),
			Mockito.anyInt(), Mockito.anyInt(), Mockito.any()
		);

		Assert.assertEquals(
			_toDate(LocalDate.parse("2026-01-31")),
			endDateArgumentCaptor.getValue());
		Assert.assertEquals(
			_toDate(LocalDate.parse("2026-01-01")),
			startDateArgumentCaptor.getValue());
	}

	@Test
	public void testGetWorkspaceGroupIndividualSegmentMembershipChangesPageWithRangeStart()
		throws Exception {

		IllegalArgumentException illegalArgumentException = Assert.assertThrows(
			IllegalArgumentException.class,
			() ->
				_individualSegmentMembershipChangeResourceImpl.
					getWorkspaceGroupIndividualSegmentMembershipChangesPage(
						RandomTestUtil.randomLong(),
						RandomTestUtil.randomString(), null, null, null,
						"2026-01-01", null,
						Pagination.of(
							RandomTestUtil.randomInt(),
							RandomTestUtil.randomInt()),
						null));

		Assert.assertEquals(
			"\"rangeEnd\" and \"rangeStart\" must be set together",
			illegalArgumentException.getMessage());

		Mockito.verifyNoInteractions(_contactsEngineClient);
	}

	@Test
	public void testGetWorkspaceGroupIndividualSegmentMembershipChangesPageWithoutRange()
		throws Exception {

		_individualSegmentMembershipChangeResourceImpl.
			getWorkspaceGroupIndividualSegmentMembershipChangesPage(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				null, null, null, null, null,
				Pagination.of(
					RandomTestUtil.randomInt(), RandomTestUtil.randomInt()),
				null);

		ArgumentCaptor<Date> endDateArgumentCaptor = ArgumentCaptor.forClass(
			Date.class);
		ArgumentCaptor<Date> startDateArgumentCaptor = ArgumentCaptor.forClass(
			Date.class);

		Mockito.verify(
			_contactsEngineClient
		).getIndividualSegmentMembershipChanges(
			Mockito.any(), Mockito.anyString(), Mockito.any(),
			startDateArgumentCaptor.capture(), endDateArgumentCaptor.capture(),
			Mockito.anyInt(), Mockito.anyInt(), Mockito.any()
		);

		LocalDate localDate = LocalDate.now(ZoneOffset.UTC);

		Assert.assertEquals(
			_toDate(localDate), endDateArgumentCaptor.getValue());
		Assert.assertEquals(
			_toDate(localDate.minusDays(30)),
			startDateArgumentCaptor.getValue());
	}

	private Date _toDate(LocalDate localDate) {
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneOffset.UTC);

		return Date.from(zonedDateTime.toInstant());
	}

	private final ContactsEngineClient _contactsEngineClient = Mockito.mock(
		ContactsEngineClient.class);
	private final IndividualSegmentMembershipChangeResourceImpl
		_individualSegmentMembershipChangeResourceImpl =
			new IndividualSegmentMembershipChangeResourceImpl();

}