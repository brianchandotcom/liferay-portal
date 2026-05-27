/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {EventSource} from 'eventsource';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {
	createEventSource,
	getChatbotConfiguration,
	postChatMessage,
} from '../api';
import {getLanguageId, getLocalizedValue} from '../locale';
import AssistantMessage from './AssistantMessage';
import ChatbotFooter from './ChatbotFooter';
import ChatbotHeader from './ChatbotHeader';
import ChatbotInput from './ChatbotInput';
import ChatbotIntro from './ChatbotIntro';
import ErrorMessage from './ErrorMessage';
import {ChatIcon, CloseIcon} from './Icons';
import LoadingIndicator from './LoadingIndicator';
import SendFeedbackModal from './SendFeedbackModal';
import Toast from './Toast';
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
	const [reportTraceId, setReportTraceId] = useState<string | null>(null);
	const [subscribed, setSubscribed] = useState(false);
	const [toastMessage, setToastMessage] = useState<string | null>(null);

	const eventSourceRef = useRef<EventSource | null>(null);
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

		let active = true;

		createEventSource()
			.then((eventSource) => {
				if (!active) {
					eventSource?.close();

					return;
				}

				if (!eventSource) {
					setMessages((prev) => [
						...prev,
						{sender: 'error', text: ''},
					]);
					setLoading(false);

					return;
				}

				eventSourceRef.current = eventSource;

				eventSource.addEventListener('Chat Message Sent', (event) => {
					if (loadingTimeoutRef.current) {
						clearTimeout(loadingTimeoutRef.current);
						loadingTimeoutRef.current = null;
					}

					try {
						const data = JSON.parse((event as MessageEvent).data);

						setMessages((prev) => [
							...prev,
							{
								sender: 'assistant',
								text: data.data,
								traceId:
									data.traceId ??
									eventSourceReference.current ??
									undefined,
							},
						]);
					}
					catch (error) {
						console.error('Error parsing chat message:', error);

						setMessages((prev) => [
							...prev,
							{sender: 'error', text: ''},
						]);
					}

					setLoading(false);
				});

				eventSource.addEventListener('Subscribe', (event) => {
					eventSourceReference.current = (event as MessageEvent).data;
					setSubscribed(true);
				});

				eventSource.addEventListener('error', () => {
					setSubscribed(false);

					if (loadingTimeoutRef.current) {
						clearTimeout(loadingTimeoutRef.current);
						loadingTimeoutRef.current = null;

						setMessages((prev) => [
							...prev,
							{sender: 'error', text: ''},
						]);
						setLoading(false);
					}
					else if (eventSource.readyState === EventSource.CLOSED) {
						console.error('EventSource connection closed');

						setMessages((prev) => [
							...prev,
							{sender: 'error', text: ''},
						]);
					}
				});
			})
			.catch((error) => {
				console.error('Failed to create event source:', error);

				setMessages((prev) => [...prev, {sender: 'error', text: ''}]);
				setLoading(false);
			});

		return () => {
			active = false;

			if (loadingTimeoutRef.current) {
				clearTimeout(loadingTimeoutRef.current);
			}

			eventSourceRef.current?.close();
			eventSourceRef.current = null;
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

	const localized = useMemo(() => {
		if (!chatbotConfiguration) {
			return null;
		}

		const pick = getLocalizedValue({
			defaultLanguageId: chatbotConfiguration.defaultLanguageId,
			editingLanguageId: getLanguageId(),
		});

		return {
			disclaimerMessage: pick(
				chatbotConfiguration.disclaimerMessage_i18n
			),
			introMessage: pick(chatbotConfiguration.introMessage_i18n),
			notificationMessage: pick(
				chatbotConfiguration.notificationMessage_i18n
			),
			placeholderMessage: pick(
				chatbotConfiguration.placeholderMessage_i18n
			),
			title: pick(chatbotConfiguration.title_i18n),
		};
	}, [chatbotConfiguration]);

	if (!chatbotConfiguration?.active || !localized) {
		return null;
	}

	const avatarURL = chatbotConfiguration.avatar?.fileURL;

	return (
		<>
			<div
				className={`aihub-panel${open ? ' open' : ''}`}
				ref={panelRef}
				tabIndex={-1}
			>
				<ChatbotHeader
					avatar={avatarURL}
					onClose={handleToggle}
					title={localized.title}
				/>

				<div aria-live="polite" className="aihub-messages">
					<ChatbotIntro
						avatar={avatarURL}
						introMessage={localized.introMessage}
						title={localized.title}
					/>

					{messages.map((msg, index) => {
						if (msg.sender === 'assistant') {
							return (
								<AssistantMessage
									avatar={avatarURL}
									key={index}
									onReport={
										msg.traceId
											? () =>
													setReportTraceId(
														msg.traceId!
													)
											: undefined
									}
									onThumbsUp={
										msg.traceId
											? () =>
													setToastMessage(
														'Thanks for your feedback!'
													)
											: undefined
									}
									text={msg.text}
									title={localized.title}
								/>
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
					placeholder={localized.placeholderMessage}
				/>

				<ChatbotFooter
					disclaimerMessage={localized.disclaimerMessage}
				/>
			</div>

			{!open &&
				!notificationDismissed &&
				localized.notificationMessage && (
					<div className="aihub-notification">
						<span>{localized.notificationMessage}</span>

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

			{reportTraceId !== null && (
				<SendFeedbackModal
					agentId={widgetConfiguration.chatbotExternalReferenceCode}
					onClose={() => setReportTraceId(null)}
					onSubmitted={() => {
						setReportTraceId(null);
						setToastMessage('Thanks for your feedback!');
					}}
					traceId={reportTraceId}
				/>
			)}

			{toastMessage && (
				<Toast
					message={toastMessage}
					onDismiss={() => setToastMessage(null)}
				/>
			)}
		</>
	);
}
