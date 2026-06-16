/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.contacts.model.constants.ContactsCardTemplateConstants;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.constants.FieldMappingConstants;
import com.liferay.osb.faro.engine.client.model.FieldMapping;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.constants.FaroPaginationConstants;
import com.liferay.osb.faro.web.internal.model.display.contacts.FieldMappingDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.osb.faro.web.internal.util.IndividualSegmentUtil;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.osb.faro.web.internal.util.SchemaOrgUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class SegmentDistributionContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public SegmentDistributionContactsCardTemplateDisplay() {
	}

	public SegmentDistributionContactsCardTemplateDisplay(
			FaroProject faroProject, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(
			contactsCardTemplate, size,
			getSupportedSizes(contactsCardTemplate));

		_binSize = MapUtil.getDouble(settings, "binSize");

		String fieldMappingFieldName = MapUtil.getString(
			settings, "fieldMappingFieldName");

		if (Validator.isNotNull(fieldMappingFieldName)) {
			FieldMapping fieldMapping = contactsEngineClient.getFieldMapping(
				faroProject, fieldMappingFieldName);

			_fieldMappingDisplay = new FieldMappingDisplay(fieldMapping);
		}

		_graphType = MapUtil.getInteger(settings, "graphType");
		_max = MapUtil.getInteger(settings, "max");
		_numberOfBins = MapUtil.getInteger(settings, "numberOfBins");
	}

	@Override
	public Map<String, Object> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		List<Map<String, Object>> individualFieldDistribution =
			new ArrayList<>();

		if (_fieldMappingDisplay != null) {
			try {
				if (SchemaOrgUtil.isSubtype(
						_fieldMappingDisplay.getType(),
						FieldMappingConstants.TYPE_NUMBER)) {

					double maxValue = GetterUtil.getDouble(
						IndividualSegmentUtil.getFieldValue(
							faroProject, faroEntityDisplay.getId(),
							_fieldMappingDisplay.getName(),
							FaroPaginationConstants.ORDER_BY_TYPE_DESC,
							contactsEngineClient));
					double minValue = GetterUtil.getDouble(
						IndividualSegmentUtil.getFieldValue(
							faroProject, faroEntityDisplay.getId(),
							_fieldMappingDisplay.getName(),
							FaroPaginationConstants.ORDER_BY_TYPE_ASC,
							contactsEngineClient));

					if (_binSize < 0) {
						_binSize = IndividualSegmentUtil.getDefaultBinSize(
							_numberOfBins, minValue, maxValue);
					}

					_minBinSize = IndividualSegmentUtil.getMinBinSize(
						minValue, maxValue);
				}

				individualFieldDistribution =
					IndividualSegmentUtil.getFieldDistribution(
						faroProject, faroEntityDisplay.getId(),
						_fieldMappingDisplay.getId(), _binSize, _numberOfBins,
						1, _max, contactsEngineClient);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}

		return HashMapBuilder.<String, Object>put(
			"individualFieldDistribution", individualFieldDistribution
		).build();
	}

	protected static int[] getSupportedSizes(
			ContactsCardTemplate contactsCardTemplate)
		throws Exception {

		Map<String, Object> settings = JSONUtil.readValue(
			contactsCardTemplate.getSettings(),
			new TypeReference<Map<String, Object>>() {
			});

		int graphType = MapUtil.getInteger(settings, "graphType");

		if (graphType ==
				ContactsCardTemplateConstants.SETTINGS_GRAPH_TYPE_PIE) {

			return _SUPPORTED_SIZES_TYPE_PIE;
		}

		return _SUPPORTED_SIZES;
	}

	private static final int[] _SUPPORTED_SIZES = {1};

	private static final int[] _SUPPORTED_SIZES_TYPE_PIE = {1};

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentDistributionContactsCardTemplateDisplay.class);

	private double _binSize;
	private FieldMappingDisplay _fieldMappingDisplay;
	private int _graphType;
	private int _max;
	private double _minBinSize;
	private int _numberOfBins;

}