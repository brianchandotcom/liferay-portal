/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import {autoCancel, autoCancelWith, hasRequest} from '../request-decorator';

jest.unmock('react-dom');

describe('request-decorator', () => {
	describe('autoCancel', () => {
		it('cancels the request if the same request was made again', () => {
			expect.assertions(2);

			const cancel = jest.fn();

			class TestAutoCancel extends React.Component {
				@autoCancel
				handleClick() {
					return {cancel};
				}

				render() {
					return <button onClick={this.handleClick}>click me</button>;
				}
			}

			const {getByText} = render(<TestAutoCancel />);

			fireEvent.click(getByText('click me'));

			expect(cancel).not.toBeCalled();

			fireEvent.click(getByText('click me'));

			expect(cancel).toBeCalled();
		});

		it('does not cancel the request if cancel is false', () => {
			expect.assertions(2);

			const cancel = jest.fn();

			class TestAutoCancel extends React.Component {
				@autoCancelWith(false)
				handleClick() {
					return {cancel};
				}

				render() {
					return <button onClick={this.handleClick}>click me</button>;
				}
			}

			const {getByText} = render(<TestAutoCancel />);

			fireEvent.click(getByText('click me'));

			expect(cancel).not.toBeCalled();

			fireEvent.click(getByText('click me'));

			expect(cancel).not.toBeCalled();
		});
	});

	describe('hasRequest', () => {
		it('cancels the requests on the disposal of the component', () => {
			expect.assertions(2);

			const cancel = jest.fn();

			@hasRequest
			class TestAutoCancel extends React.Component {
				@autoCancel
				handleClick() {
					return {cancel};
				}

				render() {
					return <button onClick={this.handleClick}>click me</button>;
				}
			}

			const {getByText, unmount} = render(<TestAutoCancel />);

			fireEvent.click(getByText('click me'));

			expect(cancel).not.toBeCalled();

			unmount();

			expect(cancel).toBeCalled();
		});
	});
});
