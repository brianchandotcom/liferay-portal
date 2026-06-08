/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import autobind from 'autobind-decorator';
import React from 'react';
import ReactDOM from 'react-dom';
import Popover from '~/shared/components/Popover';

import Row from '../components/Row';

export default class PopoverKit extends React.Component {
	state = {
		show: false,
		tooltipTarget: {},
	};

	constructor() {
		super();
	}

	@autobind
	handleMouseOut() {
		this.setState({
			show: false,
		});
	}

	@autobind
	handleMouseOver(event) {
		this.setState({
			show: true,
			tooltipTarget: event.target,
		});
	}

	render() {
		return (
			<div>
				<Row>
					<h3>Popover</h3>

					<div>
						<ClayButton
							className="button-root"
							displayType="secondary"
							onBlur={this.handleMouseOver}
							onFocus={this.handleMouseOut}
							onMouseOut={this.handleMouseOut}
							onMouseOver={this.handleMouseOver}
							ref={this._triggerRef}
						>
							Mouse over me!
						</ClayButton>

						{ReactDOM.createPortal(
							<Popover
								alignElement={this.state.tooltipTarget}
								title="Popover content"
								visible={this.state.show}
							/>,
							document.querySelector('body.dxp')
						)}
					</div>
				</Row>
			</div>
		);
	}
}
