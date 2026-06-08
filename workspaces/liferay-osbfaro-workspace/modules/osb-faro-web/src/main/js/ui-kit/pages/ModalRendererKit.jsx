/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import autobind from 'autobind-decorator';
import {times} from 'lodash';
import React from 'react';
import {Provider} from 'react-redux';
import * as modalActions from '~/shared/actions/modals';
import ModalRenderer from '~/shared/components/ModalRenderer';
import mockStore from '~/test/mock-store';

import Row from '../components/Row';

class ModalRendererKit extends React.Component {
	constructor(props) {
		super(props);

		this._store = mockStore();
	}

	@autobind
	handleOpen() {
		this._store.dispatch(
			modalActions.open(modalActions.modalTypes.TEST, {
				onClose: this.handleClose,
			})
		);
	}

	@autobind
	handleOpenThree() {
		times(3, (i) =>
			this._store.dispatch(
				modalActions.open(modalActions.modalTypes.TEST, {
					onClose: this.handleClose,
					title: `Modal Number #${i + 1}`,
				})
			)
		);
	}

	@autobind
	handleClose() {
		this._store.dispatch(modalActions.close());
	}

	render() {
		return (
			<div>
				<Provider store={this._store}>
					<ModalRenderer />

					<Row>
						<ClayButton
							className="button-root"
							displayType="primary"
							onClick={this.handleOpen}
						>
							Open Modal
						</ClayButton>
					</Row>

					<Row>
						<ClayButton
							className="button-root"
							displayType="primary"
							onClick={this.handleOpenThree}
						>
							Open Three Modals
						</ClayButton>
					</Row>
				</Provider>
			</div>
		);
	}
}

export default ModalRendererKit;
