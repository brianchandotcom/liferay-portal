/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import NewRequestModal from '~/shared/components/modals/NewRequestModal';

const handleClose = () => alert('close!');
const handleSubmit = () => alert('submit!');

const NewRequestModalKit = () => (
	<div className="modal-container">
		<NewRequestModal
			groupId="123123"
			onClose={handleClose}
			onSubmit={handleSubmit}
		/>
	</div>
);

export default NewRequestModalKit;
