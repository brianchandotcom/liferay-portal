/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import {PropTypes} from 'prop-types';
import React from 'react';

import Overlay from '../Overlay';

jest.unmock('react-dom');

describe('Overlay', () => {
	afterEach(cleanup);

	it('renders the trigger', () => {
		render(
			<Overlay>
				{[
					<button key="foo">trigger</button>,
					<div key="bar">content</div>,
				]}
			</Overlay>
		);

		expect(document.body).toMatchSnapshot();
	});

	it('calls render content if the overlay content re-renders', () => {
		class TestComponent extends React.Component {
			static propTypes = {
				content: PropTypes.string.isRequired,
			};

			render() {
				return (
					<Overlay active>
						<button>trigger</button>
						<div className="content">{this.props.content}</div>
					</Overlay>
				);
			}
		}

		const {rerender} = render(<TestComponent content="foo" />);
		rerender(<TestComponent content="bar" />);
		expect(document.body).toMatchSnapshot();
	});

	it('stays active if a click occurs in a nested overlay', () => {
		class TestComponent extends React.Component {
			render() {
				return (
					<Overlay {...this.props} active>
						<button>trigger</button>
						<div className="content">
							<Overlay active>
								<button>nested</button>
								<div className="nested-content-container">
									<div className="nested-content" />
								</div>
							</Overlay>
						</div>
					</Overlay>
				);
			}
		}

		const onOutsideClick = jest.fn();
		const {container, getByText} = render(
			<TestComponent onOutsideClick={onOutsideClick} />
		);

		jest.runAllTimers();

		expect(onOutsideClick).not.toHaveBeenCalled();

		expect(container).toMatchSnapshot();

		fireEvent.click(getByText('nested'));

		jest.runAllTimers();

		expect(onOutsideClick).not.toHaveBeenCalled();
	});
});
