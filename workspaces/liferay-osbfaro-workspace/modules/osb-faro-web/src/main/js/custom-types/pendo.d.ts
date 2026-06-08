/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface PendoAPI {
	account: {
		creationDate?: string;

		// You can add any additional account level key-values here,
		// as long as it's not one of the above reserved names.

		id?: string; // Highly recommended, required if using Pendo Feedback

		// eslint-disable-next-line camelcase
		is_paying?: boolean; // Recommended if using Pendo Feedback

		// eslint-disable-next-line camelcase
		monthly_value?: boolean; // Recommended if using Pendo Feedback
		name: string;
		planLevel: string;
		planPrice?: string;
	};
	visitor: {
		email: string; // Recommended if using Pendo Feedback, or NPS Email

		// eslint-disable-next-line camelcase
		full_name: string; // Recommended if using Pendo Feedback

		id: string; // Required if user is logged in
		role: string;

		// You can add any additional visitor level key-values here,
		// as long as it's not one of the above reserved names.

	};
}

declare const pendo: {
	identify: (data: PendoAPI) => void;
	initialize: (data: PendoAPI) => void;
	isReady?: () => boolean;
};
