/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const getPersonas = (personaArray) => {
	let persona = '';

	if (personaArray) {
		persona = personaArray[0].name;

		if (personaArray.length > 1) {
			persona += ', +' + (personaArray.length - 1);
		}
	}

	return persona;
};

export const getShortText = (text, characterLimit = 150) => {
	if (text.length > characterLimit) {
		const lastSpaceIndex = text
			.substring(0, characterLimit)
			.lastIndexOf(' ');
		text = text.substring(0, lastSpaceIndex) + '...';
	}

	return text;
};

export const getTooltipPersona = (personaArray) => {
	let persona = '';

	personaArray.forEach((personas) => {
		persona += personas.name + ', ';
	});

	return persona.slice(0, -2);
};
