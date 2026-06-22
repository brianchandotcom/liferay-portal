/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.Interest;

/**
 * @author Shinn Lok
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class InterestDisplay {

	public InterestDisplay() {
	}

	@SuppressWarnings("unchecked")
	public InterestDisplay(Interest interest) {
		_name = interest.getName();
		_pagesViewCount = interest.getViews();
		_relatedPagesCount = interest.getRelatedPagesCount();
		_score = interest.getScore();
	}

	private String _name;
	private int _pagesViewCount;
	private int _relatedPagesCount;
	private double _score;

}