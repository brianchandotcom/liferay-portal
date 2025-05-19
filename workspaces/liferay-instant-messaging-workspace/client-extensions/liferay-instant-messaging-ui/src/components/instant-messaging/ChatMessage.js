/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {forwardRef, useState} from 'react';

import {ChatMessageType} from '../../utils/constants';
import {showError} from '../../utils/util';

// eslint-disable-next-line no-unused-vars
const ChatMessage = forwardRef((props, ref) => {
	const {message, self} = props;

	const [error, setError] = useState(false);

	const handleError = (error) => {
		setError(true);
		showError('Error', error.message);
	};

	return (
		<>
			{message.chatMessageType === ChatMessageType.TEXT && (
				<div
					className={`chat-bubble ${self.userId.toString() === message.from.toString() ? 'sent' : 'received'}`}
				>
					{message.message}
				</div>
			)}

			{message &&
				message.chatMessageType === ChatMessageType.FILE &&
				error && (
					<>
						<ClayAlert
							displayType="danger"
							role={null}
							title="Error"
						>
							An error occurred while loading the file:{' '}

							{message.file.fileName}.
						</ClayAlert>
					</>
				)}

			{message.chatMessageType === ChatMessageType.FILE && !error && (
				<div
					className={`chat-bubble ${self.userId.toString() === message.from.toString() ? 'sent' : 'received'}`}
				>
					<a href={message.file.fileUrl}>
						<img
							alt={message.file.fileName}
							className="bg-white w-100"
							onError={handleError}
							src={message.file.preview}
						/>
					</a>
				</div>
			)}
		</>
	);
});

export default ChatMessage;
