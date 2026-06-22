/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.IndividualSegment;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.constants.FaroPaginationConstants;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.IndividualSegmentDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public class AssociatedSegmentsContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public AssociatedSegmentsContactsCardTemplateDisplay() {
	}

	public AssociatedSegmentsContactsCardTemplateDisplay(
			FaroProject faroProject, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);
	}

	@Override
	public Map<String, Object> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		return HashMapBuilder.<String, Object>put(
			"contactsEntityResults",
			() -> {
				Results<IndividualSegment> results =
					contactsEngineClient.getIndividualIndividualSegments(
						faroProject, null, faroEntityDisplay.getId(),
						StringPool.BLANK,
						IndividualSegment.Status.ACTIVE.name(), 1,
						getSize() * _ITEMS_PER_COLUMN,
						Collections.singletonList(
							new OrderByField(
								"name",
								FaroPaginationConstants.ORDER_BY_TYPE_ASC,
								true)));

				List<IndividualSegment> individualSegments = results.getItems();

				return new FaroResultsDisplay(
					TransformUtil.transform(
						individualSegments, IndividualSegmentDisplay::new),
					results.getTotal());
			}
		).build();
	}

	private static final int _ITEMS_PER_COLUMN = 6;

	private static final int[] _SUPPORTED_SIZES = {1};

}