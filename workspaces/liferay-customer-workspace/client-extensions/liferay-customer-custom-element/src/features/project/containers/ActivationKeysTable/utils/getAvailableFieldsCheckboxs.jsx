/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const isQuarterlyRelease = (version) => /^\d{4}\.Q[1-4]$/.test(version);

const parseQuarterlyRelease = (version) => {
	const [year, quarter] = version.split('.Q');

	return parseFloat(`${year}.${quarter}`);
};

export default function getAvailableFieldsCheckboxs(items, getItem) {
	return items
		?.reduce((accumulatorItems, currentItem) => {
			const item = getItem(currentItem);

			if (!item || accumulatorItems.includes(item)) {
				return accumulatorItems;
			}

			return [...accumulatorItems, item];
		}, [])
		.sort((previousItem, nextItem) => {
			const isPreviousQR = isQuarterlyRelease(previousItem);
			const isNextQR = isQuarterlyRelease(nextItem);

			if (isPreviousQR && isNextQR) {
				return parseQuarterlyRelease(nextItem) - parseQuarterlyRelease(previousItem);
			}

			if (isPreviousQR) {
				return -1;
			}
			if (isNextQR) {
				return 1;
			}

			const numPreviousItem = parseFloat(previousItem);
			const numNextItem = parseFloat(nextItem);

			if (numPreviousItem === numNextItem) {
				return nextItem.localeCompare(previousItem);
			}

			return numNextItem - numPreviousItem;
		});
}
