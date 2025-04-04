/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const getYearlyTerms = ({
	endDate,
	startDate,
}: {
	endDate: string | Date;
	startDate: string | Date;
}) => {
	endDate = new Date(endDate);
	startDate = new Date(startDate);

	const endDateYear = endDate.getUTCFullYear();
	const startDateYear = startDate.getUTCFullYear();
	const yearlyTerms = [];

	const isExactlyOneYearApart = (startDate: Date, endDate: Date): boolean => {
		const oneYearLater = new Date(startDate);
		oneYearLater.setFullYear(oneYearLater.getUTCFullYear() + 1);

		return (
			endDate.getUTCFullYear() === oneYearLater.getUTCFullYear() &&
			endDate.getUTCMonth() === oneYearLater.getUTCMonth() &&
			endDate.getUTCDate() === oneYearLater.getUTCDate()
		);
	};

	if (
		startDateYear + 1 > endDateYear ||
		isExactlyOneYearApart(startDate, endDate)
	) {
		return [{endDate, startDate}];
	}

	for (let year = startDateYear; year <= endDateYear; year++) {
		let endDateIterationYear = new Date(
			Date.UTC(
				year + 1,
				startDate.getUTCMonth(),
				startDate.getUTCDate() - 1
			)
		);
		const startDateIterationYear = new Date(startDate.setFullYear(year));

		if (startDateIterationYear > endDate) {
			break;
		}

		if (endDateIterationYear > endDate) {
			endDateIterationYear = endDate;
		}

		yearlyTerms.push({
			endDate: endDateIterationYear,
			startDate: startDateIterationYear,
		});
	}

	return yearlyTerms;
};

export {getYearlyTerms};
