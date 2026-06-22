/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util.comparator;

import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Geyson Silva
 */
public class FaroChannelComparator extends OrderByComparator<FaroChannel> {

	public FaroChannelComparator(List<OrderByField> orderByFields) {
		_orderByFields = orderByFields;
	}

	@Override
	public int compare(FaroChannel faroChannel1, FaroChannel faroChannel2) {
		return 0;
	}

	@Override
	public String getOrderBy() {
		return StringUtil.merge(
			TransformUtil.transform(
				_orderByFields,
				orderByField -> {
					String format = null;

					if (StringUtil.equals(
							orderByField.getFieldName(), "createTime")) {

						format = "%s %s";
					}
					else {
						format = "lower(%s) %s";
					}

					return String.format(
						format, _fieldNames.get(orderByField.getFieldName()),
						orderByField.getOrderBy());
				}),
			", ");
	}

	private static final Map<String, String> _fieldNames = HashMapBuilder.put(
		"createTime", "OSBFaro_FaroChannel.createTime"
	).put(
		"name", "OSBFaro_FaroChannel.name"
	).build();

	private final List<OrderByField> _orderByFields;

}