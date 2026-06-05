/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.constants;

import com.liferay.osb.faro.engine.client.model.Asset;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class AssetConstants {

	public static final String TYPE_ASSET = "Asset";

	public static Map<String, String> getTypes() {
		return _types;
	}

	public static List<String> getTypes(int action) {
		return _actionTypes.getOrDefault(action, Collections.emptyList());
	}

	private static final Map<Integer, List<String>> _actionTypes =
		HashMapBuilder.<Integer, List<String>>put(
			ActivityConstants.ACTION_DOWNLOADS,
			Collections.singletonList(Asset.AssetType.Document.name())
		).put(
			ActivityConstants.ACTION_SUBMISSIONS,
			Collections.singletonList(Asset.AssetType.Form.name())
		).put(
			ActivityConstants.ACTION_VISITS,
			Arrays.asList(
				Asset.AssetType.Form.name(), Asset.AssetType.Page.name())
		).build();
	private static final Map<String, String> _types = HashMapBuilder.put(
		"asset", TYPE_ASSET
	).put(
		"document", Asset.AssetType.Document.name()
	).put(
		"form", Asset.AssetType.Form.name()
	).put(
		"webPage", Asset.AssetType.Page.name()
	).build();

}