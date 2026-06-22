/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import com.liferay.petra.function.transform.TransformUtil;

/**
 * @author Shinn Lok
 */
public abstract class PagedModel<T, R>
	extends org.springframework.hateoas.PagedModel<T> {

	public Results<R> getResults() {
		Results<R> results = new Results<>();

		results.setItems(
			TransformUtil.transform(
				getContent(), content -> processContent(content)));

		PageMetadata pageMetadata = getMetadata();

		if (pageMetadata != null) {
			results.setTotal((int)pageMetadata.getTotalElements());
		}

		return results;
	}

	public abstract R processContent(T content);

}