/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.util.comparator;

import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Nilton Vieira
 */
public class TestrayCaseResultsComparator
	implements Comparator<List<Map<String, Object>>> {

	@Override
	public int compare(
		List<Map<String, Object>> testrayCaseResults1,
		List<Map<String, Object>> testrayCaseResults2) {

		int score1 = _getTestrayCaseResultsScore(testrayCaseResults1);
		int score2 = _getTestrayCaseResultsScore(testrayCaseResults2);

		if (score1 > score2) {
			return -1;
		}
		else if (score1 < score2) {
			return 1;
		}

		return 0;
	}

	private int _getTestrayCaseResultsScore(
		List<Map<String, Object>> testrayCaseResults) {

		int score = 0;

		for (Map<String, Object> testrayCaseResult : testrayCaseResults) {
			score += GetterUtil.getInteger(testrayCaseResult.get("priority_"));
		}

		return score;
	}

}