/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface IItem {
	key: string;
	name: string;
}

export default function sortLiferayVersions(items: IItem[]): IItem[] {
	const compareVersions = (a: string, b: string): number => {
		const parseVersion = (version: string) =>
			version
				.split(/[.\s]/)
				.map((part) => parseInt(part.replace(/[^0-9]/g, ''), 10) || 0);

		const partsA = parseVersion(a);
		const partsB = parseVersion(b);
		const maxLength = Math.max(partsA.length, partsB.length);

		for (let i = 0; i < maxLength; i++) {
			const diff = (partsB[i] || 0) - (partsA[i] || 0);
			if (diff !== 0) {
				return diff;
			}
		}

		return 0;
	};

	return [...items].sort((a, b) => compareVersions(a.name, b.name));
}
