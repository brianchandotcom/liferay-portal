/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AttachmentUploader from './components/AttachmentUploader';
import './app.scss';


const AttachmentFileUploader = () => {
	return (
		<div className='container-attach'>
			<AttachmentUploader />
		</div>
	);
};

export default AttachmentFileUploader;
