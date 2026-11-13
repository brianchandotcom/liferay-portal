/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.internal;

import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.faro.engine.client.constants.FieldMappingConstants;
import com.liferay.osb.faro.engine.client.model.PagedModel;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

/**
 * @author Eudaldo Alonso
 */
public class ContactsEngineClientImplTest {

	@Test
	public void testGetFieldNamesList() {
		Map<String, String> liferayFieldNames =
			FieldMappingConstants.getLiferayFieldNames();

		List<String> labels = new ArrayList<>(liferayFieldNames.keySet());

		String fieldName = RandomTestUtil.randomString();

		_fieldNamesList = Arrays.asList(
			new ArrayList<>(Collections.singletonList(fieldName)),
			new ArrayList<>(
				Arrays.asList(fieldName, liferayFieldNames.get(labels.get(1)))),
			new ArrayList<>(Collections.singletonList(fieldName)));

		Assert.assertEquals(
			Arrays.asList(
				Arrays.asList(liferayFieldNames.get(labels.get(0)), fieldName),
				Arrays.asList(fieldName, liferayFieldNames.get(labels.get(1))),
				Collections.singletonList(fieldName)),
			_contactsEngineClientImpl.getFieldNamesList(
				Mockito.mock(FaroProject.class),
				Arrays.asList(
					labels.get(0), labels.get(1),
					RandomTestUtil.randomString()),
				FieldMappingConstants.OWNER_TYPE_INDIVIDUAL,
				Arrays.asList(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString())));
	}

	@Test
	public void testGetIndividualIndividualSegmentsWithChannelId() {
		long channelId = RandomTestUtil.randomLong();

		_contactsEngineClientImpl.getIndividualIndividualSegments(
			Mockito.mock(FaroProject.class), String.valueOf(channelId),
			RandomTestUtil.randomString(), null, null, 1, 20, null);

		String filter = String.valueOf(_uriVariables.get("filter"));

		Assert.assertFalse(filter.contains(" ne "));
		Assert.assertTrue(filter.contains("channelId eq " + channelId));
	}

	@Test
	public void testGetIndividualIndividualSegmentsWithExcludedChannelIds() {
		long channelId1 = RandomTestUtil.randomLong();
		long channelId2 = RandomTestUtil.randomLong();

		_contactsEngineClientImpl.getIndividualIndividualSegments(
			Mockito.mock(FaroProject.class), null,
			Arrays.asList(
				String.valueOf(channelId1), String.valueOf(channelId2)),
			RandomTestUtil.randomString(), null, null, 1, 20, null);

		String filter = String.valueOf(_uriVariables.get("filter"));

		Assert.assertTrue(filter.contains("channelId ne " + channelId1));
		Assert.assertTrue(filter.contains("channelId ne " + channelId2));
	}

	private final ContactsEngineClientImpl _contactsEngineClientImpl =
		new ContactsEngineClientImpl() {

			@Override
			protected <T> List<T> bulk(
				FaroProject faroProject, String type, HttpMethod httpMethod,
				TypeReference<T> typeReference,
				List<Map<String, Object>> uriVariablesList) {

				return (List<T>)_fieldNamesList;
			}

			@Override
			protected <T> T get(
				FaroProject faroProject, String type,
				ParameterizedTypeReference<T> parameterizedTypeReference,
				Map<String, Object> uriVariables) {

				_uriVariables = uriVariables;

				PagedModel<?, ?> pagedModel = Mockito.mock(PagedModel.class);

				Mockito.doReturn(
					new Results<>(Collections.emptyList(), 0)
				).when(
					pagedModel
				).getResults();

				return (T)pagedModel;
			}

		};

	private List<List<String>> _fieldNamesList;
	private Map<String, Object> _uriVariables;

}