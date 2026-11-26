/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.resource.v1_0;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.rest.dto.v1_0.Interest;
import com.liferay.osb.faro.rest.internal.dto.v1_0.converter.FaroDTOConverterContext;
import com.liferay.osb.faro.rest.internal.dto.v1_0.util.FaroPaginationUtil;
import com.liferay.osb.faro.rest.resource.v1_0.InterestResource;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Leslie Wong
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/interest.properties",
	scope = ServiceScope.PROTOTYPE, service = InterestResource.class
)
public class InterestResourceImpl extends BaseInterestResourceImpl {

	@Override
	public Page<Interest> getWorkspaceGroupChannelInterestsPage(
			Long groupId, String channelId, String individualId, String search,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Results<com.liferay.osb.faro.engine.client.model.Interest> results =
			_contactsEngineClient.getInterests(
				_faroProjectLocalService.getFaroProjectByGroupId(groupId),
				channelId, individualId, null, null, search, null,
				FaroPaginationUtil.getCur(pagination),
				FaroPaginationUtil.getDelta(pagination),
				_toOrderByFields(sorts));

		return Page.of(
			transform(
				results.getItems(),
				interest -> _interestDTOConverter.toDTO(
					new FaroDTOConverterContext(
						contextAcceptLanguage.isAcceptAllLanguages(), null,
						contextAcceptLanguage.getPreferredLocale()),
					interest)),
			pagination, results.getTotal());
	}

	private List<OrderByField> _toOrderByFields(Sort[] sorts) {
		return transform(
			FaroPaginationUtil.toOrderByFields(sorts),
			orderByField -> new OrderByField(
				_fieldNames.getOrDefault(
					orderByField.getFieldName(), orderByField.getFieldName()),
				String.valueOf(orderByField.getOrderBy()),
				orderByField.isSystem()));
	}

	private static final Map<String, String> _fieldNames = HashMapBuilder.put(
		"individualId", "ownerId"
	).put(
		"relatedPagesCount", "contributingPagesCount"
	).put(
		"score", "interestScore"
	).build();

	@Reference
	private ContactsEngineClient _contactsEngineClient;

	@Reference
	private FaroProjectLocalService _faroProjectLocalService;

	@Reference(
		target = "(component.name=com.liferay.osb.faro.rest.internal.dto.v1_0.converter.InterestDTOConverter)"
	)
	private DTOConverter
		<com.liferay.osb.faro.engine.client.model.Interest, Interest>
			_interestDTOConverter;

}