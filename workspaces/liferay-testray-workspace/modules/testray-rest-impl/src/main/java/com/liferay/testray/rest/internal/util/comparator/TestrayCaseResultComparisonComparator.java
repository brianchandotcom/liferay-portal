/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.util.comparator;

import com.liferay.testray.rest.dto.v1_0.TestrayCaseResultComparison;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Nilton Vieira
 */
public class TestrayCaseResultComparisonComparator
	implements Comparator<TestrayCaseResultComparison> {

	@Override
	public int compare(
		TestrayCaseResultComparison testrayCaseResultComparison1,
		TestrayCaseResultComparison testrayCaseResultComparison2) {

		if (!Objects.equals(
				testrayCaseResultComparison1.getStatus1(),
				testrayCaseResultComparison2.getStatus1())) {

			return testrayCaseResultComparison1.getStatus1(
			).compareTo(
				testrayCaseResultComparison2.getStatus1()
			);
		}

		if (!Objects.equals(
				testrayCaseResultComparison1.getStatus2(),
				testrayCaseResultComparison2.getStatus2())) {

			return testrayCaseResultComparison1.getStatus2(
			).compareTo(
				testrayCaseResultComparison2.getStatus2()
			);
		}

		if (testrayCaseResultComparison1.getPriority() !=
				testrayCaseResultComparison2.getPriority()) {

			return testrayCaseResultComparison1.getPriority() -
				testrayCaseResultComparison2.getPriority();
		}

		if (!Objects.equals(
				testrayCaseResultComparison1.getTestrayComponentName(),
				testrayCaseResultComparison2.getTestrayComponentName())) {

			return testrayCaseResultComparison1.getTestrayComponentName(
			).compareTo(
				testrayCaseResultComparison2.getTestrayComponentName()
			);
		}

		return testrayCaseResultComparison1.getName(
		).compareTo(
			testrayCaseResultComparison2.getName()
		);
	}

}