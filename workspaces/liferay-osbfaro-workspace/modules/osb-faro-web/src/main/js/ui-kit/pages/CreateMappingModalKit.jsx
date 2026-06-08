/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Provider} from 'react-redux';
import ModalRenderer from '~/shared/components/ModalRenderer';
import CreateMappingModal from '~/shared/components/modals/CreateMappingModal';
import mockStore from '~/test/mock-store';

const store = mockStore();

class CreateMappingModalKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Provider store={store}>
					<CreateMappingModal {...this.props} />

					<ModalRenderer />
				</Provider>
			</div>
		);
	}
}

export default CreateMappingModalKit;
