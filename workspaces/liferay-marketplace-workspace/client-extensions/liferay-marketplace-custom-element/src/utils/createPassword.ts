/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const characters =
	'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#0123456789';

const getRandomInteger = (min: number, max: number) => {
	return Math.floor(Math.random() * (max - min + 1)) + min;
};

const createPassword = () => {
	let password = '';
	if (characters.length) {
		for (let i = 0; i < 10; i++) {
			password += characters[getRandomInteger(0, characters.length - 1)];
		}

		return password;
	}

	return password;
};

export {createPassword};
