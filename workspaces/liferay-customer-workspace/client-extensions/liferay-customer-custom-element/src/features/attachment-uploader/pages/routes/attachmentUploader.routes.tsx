/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import Layout from '../../components/Layout';
import AttachmentUploader from '../AttachmentUploader';
import {
	AttachmentAlreadyExists,
	AttachmentNotFound,
	CommentPostFailed,
	ForbiddenAccess,
	InvalidTicketNumber,
	ServerUnavailable,
	UnexpectedError,
	UploadConfirmation,
} from '../AttachmentUploaderMessages';

const AttachmentUploaderRoutes = () => {
	return (
		<HashRouter>
			<Routes>
				<Route element={<Layout />} path="/:ticketId">
					<Route element={<AttachmentUploader />} index />
					<Route
						element={<AttachmentAlreadyExists />}
						path="attachment-already-exists"
					/>
					<Route
						element={<AttachmentNotFound />}
						path="attachment-not-found"
					/>
					<Route
						element={<CommentPostFailed />}
						path="comment-post-failed"
					/>
					<Route
						element={<ForbiddenAccess />}
						path="forbidden-access"
					/>
					<Route
						element={<InvalidTicketNumber />}
						path="invalid-ticket-number"
					/>
					<Route
						element={<ServerUnavailable />}
						path="server-unavailable"
					/>
					<Route
						element={<UnexpectedError />}
						path="unexpected-error"
					/>
					<Route
						element={<UploadConfirmation />}
						path="upload-confirmation"
					/>
				</Route>
			</Routes>
		</HashRouter>
	);
};

export default AttachmentUploaderRoutes;
