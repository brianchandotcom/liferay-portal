/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const validSLAs = [
	'Global 24/7 Support',
	'Gold Subscription',
	'Platinum Subscription',
	'Premier 24/7 Support',
	'Premium Subscription',
	'Standard 8/5 Support',
	'Strategic 24/7 Support',
];

const loading = fragmentElement.querySelector('.loading-container');
const form = fragmentElement.querySelector('.callback-form-container');
const error = fragmentElement.querySelector('.error-container');

const updateState = (state) => {
	if (layoutMode === 'edit') {
		if (loading) {
			loading.classList.add('d-none');
			loading.classList.remove('d-flex');
		}

		if (form) {
			form.classList.remove('d-none');
		}

		if (error) {
			error.classList.remove('d-none');
			error.classList.add('d-block');
		}

		return;
	}

	[loading, form, error].forEach((element) => {
		if (element) {
			element.classList.add('d-none');
			element.classList.remove('d-flex');
		}
	});

	if (state === 'form' && form) {
		form.classList.remove('d-none');
	}
	else if (state === 'error' && error) {
		error.classList.remove('d-none');
		error.classList.add('d-block');
	}
	else if (state === 'loading' && loading) {
		loading.classList.remove('d-none');
		loading.classList.add('d-flex');
	}
};

const checkPermission = async () => {
	if (layoutMode === 'edit') {
		updateState();

		return;
	}

	try {
		updateState('loading');

		const userId = Liferay.ThemeDisplay.getUserId();
		const userAccountResponse = await Liferay.Util.fetch(
			`/o/headless-admin-user/v1.0/user-accounts/${userId}`
		);

		if (!userAccountResponse.ok) {
			throw new Error(
				`User account fetch failed: ${userAccountResponse.status}`
			);
		}

		const userAccount = await userAccountResponse.json();
		const accountBriefs = userAccount.accountBriefs || [];

		if (!accountBriefs.length) {
			updateState('error');

			return;
		}

		const permissionChecks = accountBriefs.map(async (account) => {
			if (!account.externalReferenceCode) {
				return false;
			}

			try {
				const response = await Liferay.Util.fetch(
					`/o/c/koroneikiaccounts/by-external-reference-code/${account.externalReferenceCode}`
				);
				if (response.ok) {
					const data = await response.json();

					return validSLAs.includes(data.slaCurrent);
				}
			}
			catch (error) {
				console.warn(
					'Account error:',
					account.externalReferenceCode,
					error
				);
			}

			return false;
		});

		const results = await Promise.all(permissionChecks);
		const hasPermission = results.some((permission) => permission);

		updateState(hasPermission ? 'form' : 'error');
	}
	catch (error) {
		console.error('Error:', error);
		updateState('error');
	}
};

checkPermission();
