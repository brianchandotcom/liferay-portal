/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';
import {Liferay} from '../liferay';

class LiferayMessageBoardImpl {
	public getMessagesByThreadIdURL(threadId: number) {
		return `/message-board-threads/${threadId}/message-board-messages`;
	}

	public getMessagesIdURL(messageId: number) {
		return `/message-board-messages/${messageId}`;
	}

	public createMbThread(threadName: string) {
		return fetcher.post(
			`/sites/${Liferay.ThemeDisplay.getSiteGroupId()}/message-board-threads`,
			{
				articleBody: threadName,
				headline: threadName,
			}
		);
	}

	public createMbMessage(message: string, threadId: number) {
		return fetcher.post(
			`/message-board-threads/${threadId}/message-board-messages`,
			{
				articleBody: message,
				encodingFormat: 'html',
				headline: message,
			}
		);
	}
}

const liferayMessageBoardImpl = new LiferayMessageBoardImpl();

export {liferayMessageBoardImpl};
