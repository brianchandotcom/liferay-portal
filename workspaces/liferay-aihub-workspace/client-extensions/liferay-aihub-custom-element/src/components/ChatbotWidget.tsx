/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useRef, useState} from 'react';

import {createEventSource, getChatbotConfig, postChatMessage} from '../api';
import {ChatMessage, ChatbotConfig, WidgetConfiguration} from '../types';
import AssistantMessage from './AssistantMessage';
import ChatbotFooter from './ChatbotFooter';
import ChatbotHeader from './ChatbotHeader';
import ChatbotInput from './ChatbotInput';
import ChatbotIntro from './ChatbotIntro';
import ErrorMessage from './ErrorMessage';
import GeneratingIndicator from './GeneratingIndicator';
import {ChatIcon, CloseIcon} from './Icons';
import UserMessage from './UserMessage';

interface ChatbotWidgetProps {
	widgetConfiguration: WidgetConfiguration;
}

export default function ChatbotWidget({
	widgetConfiguration,
}: ChatbotWidgetProps) {
	const [chatbotConfig, setChatbotConfig] = useState<ChatbotConfig | null>(
		null
	);
	const [generating, setGenerating] = useState(false);
	const [messages, setMessages] = useState<ChatMessage[]>([]);
	const [notificationDismissed, setNotificationDismissed] = useState(false);
	const [open, setOpen] = useState(false);

	const eventSourceReference = useRef<string | null>(null);
	const generatingTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(
		null
	);
	const messagesEndRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		getChatbotConfig(widgetConfiguration.chatbotExternalReferenceCode)
			.then(setChatbotConfig)
			.catch((error) => {
				console.error('Error fetching config:', error);
			});
	}, [widgetConfiguration.chatbotExternalReferenceCode]);

	useEffect(() => {
		if (!chatbotConfig?.active) {
			return;
		}

		const eventSource = createEventSource();

		eventSource.addEventListener('Chat Message Sent', (event) => {
			if (generatingTimeoutRef.current) {
				clearTimeout(generatingTimeoutRef.current);
				generatingTimeoutRef.current = null;
			}

			const dataJSON = JSON.parse((event as MessageEvent).data);

			setMessages((prev) => [
				...prev,
				{sender: 'assistant', text: dataJSON.data},
			]);

			setGenerating(false);
		});

		eventSource.addEventListener('Subscribe', (event) => {
			eventSourceReference.current = (event as MessageEvent).data;
		});

		return () => {
			if (generatingTimeoutRef.current) {
				clearTimeout(generatingTimeoutRef.current);
			}

			eventSource.close();
		};
	}, [chatbotConfig]);

	useEffect(() => {
		messagesEndRef.current?.scrollIntoView({behavior: 'smooth'});
	}, [messages, generating]);

	const handleToggle = useCallback(() => {
		setOpen((prev) => !prev);
		setNotificationDismissed(true);
	}, []);

	const sendMessage = useCallback(
		(text: string) => {
			if (!eventSourceReference.current) {
				return;
			}

			setMessages((prev) => [...prev, {sender: 'user', text}]);
			setGenerating(true);

			postChatMessage(
				widgetConfiguration.chatbotExternalReferenceCode,
				eventSourceReference.current,
				text
			)
				.then((response) => {
					if (!response.ok) {
						throw new Error('Failed to post message');
					}

					generatingTimeoutRef.current = setTimeout(() => {
						setMessages((prev) => [
							...prev,
							{sender: 'error', text: ''},
						]);
						setGenerating(false);
					}, 30000);
				})
				.catch((error) => {
					console.error('Failed to send message:', error);

					setMessages((prev) => [
						...prev,
						{sender: 'error', text: ''},
					]);
					setGenerating(false);
				});
		},
		[widgetConfiguration.chatbotExternalReferenceCode]
	);

	if (!chatbotConfig || !chatbotConfig.active) {
		return null;
	}

	return (
		<>
			<div className={'aihub-panel' + (open ? ' open' : '')}>
				<ChatbotHeader
					onClose={handleToggle}
					title={chatbotConfig.title}
				/>

				<div className="aihub-messages">
					<ChatbotIntro
						introMessage={chatbotConfig.introMessage}
						title={chatbotConfig.title}
					/>

					{messages.map((msg, index) => {
						if (msg.sender === 'assistant') {
							return (
								<AssistantMessage key={index} text={msg.text} />
							);
						}

						if (msg.sender === 'error') {
							return <ErrorMessage key={index} />;
						}

						return <UserMessage key={index} text={msg.text} />;
					})}

					{generating && <GeneratingIndicator />}

					<div ref={messagesEndRef} />
				</div>

				<ChatbotInput
					disabled={generating}
					onSubmit={sendMessage}
					placeholder={chatbotConfig.placeholderMessage}
				/>

				<ChatbotFooter />
			</div>

			{!open &&
				!notificationDismissed &&
				chatbotConfig.notificationMessage && (
					<div className="aihub-notification">
						<span>{chatbotConfig.notificationMessage}</span>

						<button
							aria-label="Dismiss"
							className="aihub-notification-close"
							onClick={() => setNotificationDismissed(true)}
						>
							<CloseIcon />
						</button>
					</div>
				)}

			<button
				aria-label={open ? 'Close AI Assistant' : 'Open AI Assistant'}
				className="aihub-toggle"
				onClick={handleToggle}
			>
				{open ? <CloseIcon /> : <ChatIcon />}
			</button>
		</>
	);
}
