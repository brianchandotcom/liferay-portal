import {cleanup, fireEvent, render} from '@testing-library/react';
import {fromJS} from 'immutable';
import React from 'react';

import * as modalActions from '../../actions/modals';
import {ModalRenderer} from '../ModalRenderer';

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

jest.mock('../../actions/modals');

jest.unmock('react-dom');

const {modalTypes} = modalActions;

describe('ModalRenderer', () => {
	afterEach(() => {
		jest.clearAllMocks();
		cleanup();
	});

	it('renders', () => {
		const {container} = render(<ModalRenderer modalsIList={fromJS([])} />);

		expect(
			container.querySelector('.modal-renderer-root')
		).toBeInTheDocument();
	});

	it('renders test modal', () => {
		const {container} = render(
			<ModalRenderer
				modalsIList={fromJS([
					{
						props: {},
						type: modalTypes.TEST,
					},
				])}
			/>
		);

		expect(container.querySelector('.modal-container')).toBeTruthy();
	});

	it('adds .modal-open to body', () => {
		expect(document.body).not.toHaveClass('modal-open');

		const {rerender} = render(<ModalRenderer modalsIList={fromJS([])} />);

		rerender(
			<ModalRenderer
				modalsIList={fromJS([
					{
						props: {},
						type: modalTypes.TEST,
					},
				])}
			/>
		);

		expect(document.body).toHaveClass('modal-open');
	});

	it('renders multiple modals', () => {
		const {container} = render(
			<ModalRenderer
				modalsIList={fromJS([
					{
						props: {},
						type: modalTypes.TEST,
					},
					{
						props: {},
						type: modalTypes.TEST,
					},
				])}
			/>
		);

		expect(container.querySelectorAll('.modal-container').length).toBe(2);
	});

	it('passes props to modal', () => {
		const {getByText} = render(
			<ModalRenderer
				modalsIList={fromJS([
					{
						props: {
							title: 'FooBar',
						},
						type: modalTypes.TEST,
					},
				])}
			/>
		);

		expect(getByText('FooBar')).toBeTruthy();
	});

	it('closes modal on click outside', () => {
		const {container} = render(
			<ModalRenderer
				close={modalActions.close}
				modalsIList={fromJS([
					{
						props: {
							title: 'FooBar',
						},
						type: modalTypes.TEST,
					},
				])}
			/>
		);

		expect(modalActions.close).not.toHaveBeenCalled();
		fireEvent.click(container.querySelector('.modal-container'));
		expect(modalActions.close).toHaveBeenCalled();
	});

	it('does not close modal if closeOnBlur is disabled', () => {
		const {container} = render(
			<ModalRenderer
				modalsIList={fromJS([
					{
						closeOnBlur: false,
						props: {
							title: 'FooBar',
						},
						type: modalTypes.TEST,
					},
				])}
			/>
		);

		expect(modalActions.close).not.toHaveBeenCalled();
		fireEvent.click(container.querySelector('.modal-container'));
		expect(modalActions.close).not.toHaveBeenCalled();
	});
});
