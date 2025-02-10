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

	const endDateYear = endDate.getFullYear();
	const startDateYear = startDate.getFullYear();
	const yearlyTerms = [];

	const isExactlyOneYearApart = (startDate: Date, endDate: Date): boolean => {
		const oneYearLater = new Date(startDate);
		oneYearLater.setFullYear(oneYearLater.getFullYear() + 1);

		return (
			endDate.getFullYear() === oneYearLater.getFullYear() &&
			endDate.getMonth() === oneYearLater.getMonth() &&
			endDate.getDate() === oneYearLater.getDate()
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
			year + 1,
			startDate.getMonth(),
			startDate.getDate() - 1
		);
		const startDateIterationYear = new Date(startDate);

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
