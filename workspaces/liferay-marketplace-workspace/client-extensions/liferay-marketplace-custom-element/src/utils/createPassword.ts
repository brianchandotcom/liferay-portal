/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
