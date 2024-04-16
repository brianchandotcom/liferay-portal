/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.util.comparator;

import com.liferay.testray.rest.dto.v1_0.TestrayCaseResult;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Nilton Vieira
 */
public class TestrayCaseResultComparator
	implements Comparator<TestrayCaseResult> {

	@Override
	public int compare(
		TestrayCaseResult testrayCaseResult1,
		TestrayCaseResult testrayCaseResult2) {

		if (!Objects.equals(
				testrayCaseResult1.getStatus1(),
				testrayCaseResult2.getStatus1())) {

			return testrayCaseResult1.getStatus1(
			).compareTo(
				testrayCaseResult2.getStatus1()
			);
		}

		if (!Objects.equals(
				testrayCaseResult1.getStatus2(),
				testrayCaseResult2.getStatus2())) {

			return testrayCaseResult1.getStatus2(
			).compareTo(
				testrayCaseResult2.getStatus2()
			);
		}

		if (testrayCaseResult1.getPriority() !=
				testrayCaseResult2.getPriority()) {

			return testrayCaseResult1.getPriority() -
				testrayCaseResult2.getPriority();
		}

		if (!Objects.equals(
				testrayCaseResult1.getTestrayComponentName(),
				testrayCaseResult2.getTestrayComponentName())) {

			return testrayCaseResult1.getTestrayComponentName(
			).compareTo(
				testrayCaseResult2.getTestrayComponentName()
			);
		}

		return testrayCaseResult1.getName(
		).compareTo(
			testrayCaseResult2.getName()
		);
	}

}