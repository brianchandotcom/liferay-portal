/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {
	forwardRef,
	useCallback,
	useEffect,
	useImperativeHandle,
	useState,
} from 'react';

import {getChatLog} from '../../services/instant-messaging/messages';
import {ChatMessageType, MessageType} from '../../utils/constants';
import {showWarning} from '../../utils/util';
import AssetSelector from './AssetSelector';
import ChatMessage from './ChatMessage';

const MESSAGE_LENGTH = 500;

const ChatWindow = forwardRef((props, ref) => {
	const {connection, self, target} = props;
	const {type} = target;

	const [chatMessageType, setChatMessageType] = useState(
		ChatMessageType.TEXT
	);
	const [chatMessages, setChatMessages] = useState([]);
	const [isLoading, setIsLoading] = useState(false);
	const [isThinking, setIsThinking] = useState(false);
	const [message, setMessage] = useState('');
	const {observer, onOpenChange, open} = useModal();

	const handleBotStatus = (status) => {
		const {from, to} = status.data;

		if (from === target.userId && to === self.userId) {
			setIsThinking(status.type === MessageType.BOTSTARTTHINKING);
		}
	};

	const clearNotifications = useCallback(() => {
		if (connection && connection.OPEN === 1) {
			const messageBody = {
				data: {
					from: target.userId,
					to: self.userId,
				},
				name: 'Chat',
				type: MessageType.MESSAGE_SEEN,
			};

			connection.send(JSON.stringify(messageBody));
		}
	}, [connection, self, target]);

	const loadChatLog = useCallback(async () => {
		const messagesLog = await getChatLog(self.userId, target.userId);
		setChatMessages(messagesLog);
		clearNotifications();
	}, [clearNotifications, self, target]);

	const handleKeyDown = (event) => {
		if (
			event.key === 'Enter' &&
			!isThinking &&
			!isLoading &&
			message &&
			!!message.length
		) {
			event.preventDefault();
			handleSendMessage();
		}
	};

	const handleMessageChange = (event) => {
		if (event.target.value.length < MESSAGE_LENGTH) {
			setMessage(event.target.value);
			setChatMessageType(ChatMessageType.TEXT);
		}
		else {
			showWarning(
				'Message Length',
				`Your text message cannot exceed ${MESSAGE_LENGTH} characters`
			);
		}
	};

	const handleSendAttachment = (file) => {
		setIsLoading(true);

		const messageBody = {
			data: {
				chatMessageType: ChatMessageType.FILE,
				file: {
					fileName: file.fileName,
					fileUrl: file.contentUrl,
					preview: `${file.contentUrl}&${
						file.encodingFormat.startsWith('image')
							? 'imagePreview=1'
							: 'previewFileIndex=1'
					}`,
				},
				from: self.userId,
				to: target.userId,
			},
			name: 'Chat',
			type: MessageType.MASSAGE,
		};

		connection.send(JSON.stringify(messageBody));
		setIsLoading(false);
		onOpenChange(false);
	};

	const handleSendMessage = () => {
		if (message.length <= 0) {
			return;
		}

		setIsLoading(true);

		const messageBody = {
			data: {
				chatMessageType,
				from: self.userId,
				message,
				to: target.userId,
			},
			name: 'Chat',
			type: MessageType.MASSAGE,
		};

		connection.send(JSON.stringify(messageBody));
		setMessage('');
		setIsLoading(false);
	};

	const handleBotStart = useCallback(() => {
		const messageBody = {
			data: {
				from: self.userId,
				to: target.userId,
			},
			name: 'Chat',
			type: MessageType.START_BOT_CONNECTION,
		};

		connection.send(JSON.stringify(messageBody));
	}, [connection, self, target]);

	const handleBotEnd = useCallback(() => {
		const messageBody = {
			data: {
				from: self.userId,
				to: target.userId,
			},
			name: 'Chat',
			type: MessageType.END_BOT_CONNECTION,
		};

		connection.send(JSON.stringify(messageBody));
	}, [connection, self, target]);

	const handleSeen = () => {
		clearNotifications();
	};

	const getChatWindowId = () => target.userId;

	const handleShowAssetSelector = () => {
		onOpenChange(true);
	};

	const handleIncomingMessage = (message) => {
		const messageFrom = message.data.from;
		const messageTo = message.data.to;

		if (
			(self.userId === messageFrom || self.userId === messageTo) &&
			(target.userId === messageFrom || target.userId === messageTo)
		) {
			setChatMessages((prev) => [...prev, message.data]);
		}
	};

	useImperativeHandle(ref, () => ({
		getChatWindowId,
		handleBotStatus,
		handleIncomingMessage,
		handleSeen,
	}));

	useEffect(() => {
		handleBotStart();
		loadChatLog();

		return () => {
			handleBotEnd();
		};
	}, [handleBotStart, loadChatLog, handleBotEnd]);

	return (
		<>
			<div className="chat-box">
				<div className="chat-container">
					{chatMessages
						.sort((a, b) => b.date - a.date)
						.map((chatMessage, index) => (
							<ChatMessage
								key={`msg_${index}`}
								message={chatMessage}
								self={self}
							/>
						))}
				</div>

				<div className="bg-light px-2 py-4">
					<ClayForm.Group className="m-0 p-2">
						<ClayInput.Group>
							{isThinking && (
								<ClayLayout.Container>
									<ClayLayout.Row>
										<ClayLayout.ContentCol
											className="d-flex"
											shrink
										>
											<ClayIcon
												className="m-auto"
												style={{
													color: 'var(--primary)',
													fontSize: '2rem',
												}}
												symbol="chatbot"
											/>
										</ClayLayout.ContentCol>

										<ClayLayout.ContentCol
											className="d-flex"
											expand
										>
											<span
												className="my-auto px-2"
												style={{
													paddingLeft: '15px',
													paddingRight: '15px',
												}}
											>
												Bot is thinking...
											</span>
										</ClayLayout.ContentCol>

										<ClayLayout.ContentCol
											className="d-flex"
											shrink
										>
											<ClayLoadingIndicator
												displayType="primary"
												shape="squares"
												size="sm"
											/>
										</ClayLayout.ContentCol>
									</ClayLayout.Row>
								</ClayLayout.Container>
							)}

							<ClayInput.GroupItem>
								<ClayInput
									onChange={handleMessageChange}
									onKeyDown={handleKeyDown}
									placeholder="Type your message here..."
									type="text"
									value={message}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<ClayButtonWithIcon
									disabled={
										isThinking ||
										isLoading ||
										!message ||
										message.length <= 0
									}
									onClick={handleSendMessage}
									symbol="reply"
									title="send"
								/>
							</ClayInput.GroupItem>

							{type !== 'bot' && (
								<ClayInput.GroupItem shrink>
									<ClayButtonWithIcon
										disabled={isLoading || !!message.length}
										onClick={handleShowAssetSelector}
										symbol="paperclip"
										title="send"
									/>
								</ClayInput.GroupItem>
							)}
						</ClayInput.Group>
					</ClayForm.Group>
				</div>
			</div>

			{open && (
				<ClayModal observer={observer} size="full-screen" status="info">
					<ClayModal.Header>Documents</ClayModal.Header>

					<ClayModal.Body>
						<AssetSelector
							handleSendAttachment={handleSendAttachment}
						/>
					</ClayModal.Body>
				</ClayModal>
			)}
		</>
	);
});

export default ChatWindow;
