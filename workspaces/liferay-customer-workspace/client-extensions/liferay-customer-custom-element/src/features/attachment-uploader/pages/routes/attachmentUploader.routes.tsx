/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import AttachmentUploadConfirmation from '../AttachmentUploadConfirmation';
import AttachmentUploader from '../AttachmentUploader';

const AttachmentUploaderRoutes = () => {
	return (
		<HashRouter>
			<Routes>
				<Route element={<AttachmentUploader />} path="/" />
				<Route
					element={<AttachmentUploadConfirmation />}
					path="/upload-confirmation"
				/>
			</Routes>
		</HashRouter>
	);
};

export default AttachmentUploaderRoutes;
