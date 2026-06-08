/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isFSA} from 'flux-standard-action';

import * as actions from '../modals';

describe('Modal Actions', () => {
	describe('open', () => {
		it('returns an action', () => {
			const action = actions.open();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.OPEN_MODAL);
		});

		it('contains modal type and props', () => {
			const action = actions.open('Foo', {hidden: true});

			expect(action.payload.props.hidden).toBe(true);
			expect(action.payload.type).toBe('Foo');
		});
	});

	describe('close', () => {
		it('returns an action', () => {
			const action = actions.close();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.CLOSE_MODAL);
		});
	});

	describe('closeAll', () => {
		it('returns an action', () => {
			const action = actions.closeAll();

			expect(isFSA(action)).toBe(true);
			expect(action.type).toBe(actions.actionTypes.CLOSE_ALL_MODALS);
		});
	});
});
