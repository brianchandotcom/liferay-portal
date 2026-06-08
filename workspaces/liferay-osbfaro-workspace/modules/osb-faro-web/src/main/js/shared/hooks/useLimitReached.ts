/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export enum FeatureName {
	Batch = 'batch',
	RealTime = 'real time',
	EventAnalysis = 'event analysis',
}

export const useLimitReached = function useLimitReached({
	data = [],
	featureName,
}: {
	data?: Array<{
		currentUsage: number;
		limit: number;
		name?: string;
		type?: string;
	}>;
	featureName: FeatureName;
}) {
	const usage = data?.find(
		(item) =>
			(item['type'] || item['name'] || '').toLowerCase() ===
			featureName.toLowerCase()
	);

	if (!usage) {
		return false;
	}

	if (usage.limit === -1) {
		return false;
	}

	return usage.currentUsage >= usage.limit;
};
