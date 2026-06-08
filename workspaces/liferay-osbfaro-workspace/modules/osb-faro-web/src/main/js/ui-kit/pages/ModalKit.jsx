/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import TestModal from '~/shared/components/modals/TestModal';

class ModalKit extends React.Component {
	render() {
		return (
			<div className="modal-container">
				<TestModal size="sm" title="Small Modal" />

				<TestModal title="Default Modal" />

				<TestModal size="lg" title="Large Modal" />
			</div>
		);
	}
}

export default ModalKit;
