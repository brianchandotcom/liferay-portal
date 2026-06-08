/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Modal} from '~/shared/types';

const actionTypes = Modal.actionTypes;
const modalTypes = Modal.modalTypes;

export {actionTypes, modalTypes};

export const close = function close() {
	return {
		type: actionTypes.CLOSE_MODAL,
	};
};

export const closeAll = function closeAll() {
	return {
		type: actionTypes.CLOSE_ALL_MODALS,
	};
};

export const open = function open(
	type: string,
	props: {[key: string]: any} = {},
	options: {closeOnBlur?: boolean} = {}
) {
	const {closeOnBlur = true} = options;

	return {
		payload: {
			closeOnBlur,
			props,
			type,
		},
		type: actionTypes.OPEN_MODAL,
	};
};
