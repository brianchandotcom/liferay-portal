/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useEffect, useRef, useState} from 'react';

import {
	createEventSource,
	getChatbotConfiguration,
	postChatMessage,
} from '../api';
import AssistantMessage from './AssistantMessage';
import ChatbotFooter from './ChatbotFooter';
import ChatbotHeader from './ChatbotHeader';
import ChatbotInput from './ChatbotInput';
import ChatbotIntro from './ChatbotIntro';
import ErrorMessage from './ErrorMessage';
import {ChatIcon, CloseIcon} from './Icons';
import LoadingIndicator from './LoadingIndicator';
import UserMessage from './UserMessage';

import type {
	ChatMessage,
	ChatbotConfiguration,
	WidgetConfiguration,
} from '../types';

interface ChatbotWidgetProps {
	widgetConfiguration: WidgetConfiguration;
}

export default function ChatbotWidget({
	widgetConfiguration,
}: ChatbotWidgetProps) {
	const [chatbotConfiguration, setChatbotConfiguration] =
		useState<ChatbotConfiguration | null>(null);
	const [loading, setLoading] = useState(false);
	const [messages, setMessages] = useState<ChatMessage[]>([]);
	const [notificationDismissed, setNotificationDismissed] = useState(false);
	const [open, setOpen] = useState(false);
	const [subscribed, setSubscribed] = useState(false);

	const eventSourceReference = useRef<string | null>(null);
	const loadingTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(
		null
	);
	const messagesEndRef = useRef<HTMLDivElement>(null);
	const panelRef = useRef<HTMLDivElement>(null);

	useEffect(() => {
		getChatbotConfiguration(
			widgetConfiguration.chatbotExternalReferenceCode
		)
			.then(setChatbotConfiguration)
			.catch((error) => {
				console.error('Error fetching chatbot configuration:', error);
			});
	}, [widgetConfiguration.chatbotExternalReferenceCode]);

	useEffect(() => {
		if (!chatbotConfiguration?.active) {
			return;
		}

		const eventSource = createEventSource();

		eventSource.addEventListener('Chat Message Sent', (event) => {
			if (loadingTimeoutRef.current) {
				clearTimeout(loadingTimeoutRef.current);
				loadingTimeoutRef.current = null;
			}

			try {
				const data = JSON.parse((event as MessageEvent).data);

				setMessages((prev) => [
					...prev,
					{sender: 'assistant', text: data.data},
				]);
			}
			catch (error) {
				console.error('Error parsing chat message:', error);

				setMessages((prev) => [...prev, {sender: 'error', text: ''}]);
			}

			setLoading(false);
		});

		eventSource.addEventListener('Subscribe', (event) => {
			eventSourceReference.current = (event as MessageEvent).data;
			setSubscribed(true);
		});

		eventSource.addEventListener('error', () => {
			console.error('EventSource connection error');

			setSubscribed(false);
			setMessages((prev) => [...prev, {sender: 'error', text: ''}]);
		});

		return () => {
			if (loadingTimeoutRef.current) {
				clearTimeout(loadingTimeoutRef.current);
			}

			eventSource.close();
			setSubscribed(false);
		};
	}, [chatbotConfiguration]);

	useEffect(() => {
		if (open) {
			panelRef.current?.focus();
		}
	}, [open]);

	useEffect(() => {
		messagesEndRef.current?.scrollIntoView({behavior: 'smooth'});
	}, [messages, loading]);

	const handleToggle = useCallback(() => {
		setOpen((prev) => !prev);
		setNotificationDismissed(true);
	}, []);

	const sendMessage = useCallback(
		async (text: string) => {
			if (!eventSourceReference.current) {
				return;
			}

			setMessages((prev) => [...prev, {sender: 'user', text}]);
			setLoading(true);

			try {
				const response = await postChatMessage(
					widgetConfiguration.chatbotExternalReferenceCode,
					eventSourceReference.current,
					text
				);

				if (!response.ok) {
					throw new Error('Failed to post message');
				}

				loadingTimeoutRef.current = setTimeout(() => {
					setMessages((prev) => [
						...prev,
						{sender: 'error', text: ''},
					]);
					setLoading(false);
				}, 30000);
			}
			catch (error) {
				console.error('Failed to send message:', error);

				setMessages((prev) => [...prev, {sender: 'error', text: ''}]);
				setLoading(false);
			}
		},
		[widgetConfiguration.chatbotExternalReferenceCode]
	);

	if (!chatbotConfiguration?.active) {
		return null;
	}

	return (
		<>
			<div
				className={`aihub-panel${open ? ' open' : ''}`}
				ref={panelRef}
				tabIndex={-1}
			>
				<ChatbotHeader
					onClose={handleToggle}
					title={chatbotConfiguration.title}
				/>

				<div aria-live="polite" className="aihub-messages">
					<ChatbotIntro
						introMessage={chatbotConfiguration.introMessage}
						title={chatbotConfiguration.title}
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

					{loading && <LoadingIndicator />}

					<div ref={messagesEndRef} />
				</div>

				<ChatbotInput
					disabled={
						loading || !subscribed || !eventSourceReference.current
					}
					onSubmit={sendMessage}
					placeholder={chatbotConfiguration.placeholderMessage}
				/>

				<ChatbotFooter />
			</div>

			{!open &&
				!notificationDismissed &&
				chatbotConfiguration.notificationMessage && (
					<div className="aihub-notification">
						<span>{chatbotConfiguration.notificationMessage}</span>

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
