/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {act, cleanup, render, renderHook} from '@testing-library/react';
import {vi} from 'vitest';

import Modal from './Modal';

describe('Modal', () => {
	beforeAll(() => {
		vi.resetAllMocks();
		vi.useFakeTimers();
	});

	afterEach(() => {
		cleanup();
		vi.clearAllTimers();
	});

	it('render with success', async () => {
		const {asFragment, container, rerender} = render(
			<Modal observer={null} visible={false}>
				<span>Content...</span>
			</Modal>
		);

		const {result} = renderHook(() => useModal());

		expect(asFragment()).toMatchSnapshot();
		expect(document.querySelector('.modal-open')).toBeFalsy();
		expect(container.querySelector('span')).toBeFalsy();

		rerender(
			<Modal observer={result.current.observer} visible>
				<span>Content...</span>
			</Modal>
		);

		await act(async () => {
			await vi.runAllTimers();
		});

		expect(document.querySelector('.modal-open')).toBeTruthy();
	});
});
