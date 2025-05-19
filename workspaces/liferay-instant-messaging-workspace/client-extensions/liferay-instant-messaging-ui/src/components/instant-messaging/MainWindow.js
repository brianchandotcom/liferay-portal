/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './MainWindow.css';

import ClayAlert from '@clayui/alert';
import ClayBadge from '@clayui/badge';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import ClayPanel from '@clayui/panel';
import ClayTabs from '@clayui/tabs';
import ClayToolbar from '@clayui/toolbar';
import {forwardRef, useCallback, useEffect, useRef, useState} from 'react';

import {liferayInstantMessagingConnect} from '../../services/instant-messaging/connection';
import {getContacts} from '../../services/instant-messaging/contacts';
import {MessageType} from '../../utils/constants';
import ChatWindow from './ChatWindow';
import ContactCard from './ContactCard';

// eslint-disable-next-line no-unused-vars
const MainWindow = forwardRef((props, ref) => {
	const [active, setActive] = useState(0);
	const [contacts, setContacts] = useState([]);
	const [contactsQuery, setContactsQuery] = useState('');
	const [expanded, setExpanded] = useState(false);
	const [filteredContacts, setFilteredContacts] = useState([]);
	const [isChatOpen, setIsChatOpen] = useState(false);
	const [notifications, setNotifications] = useState(null);
	const [onlineIds, setOnlineIds] = useState([]);
	const [selectedContact, setSelectedContact] = useState(null);
	const [selfContact, setSelfContact] = useState(null);
	const [socket, setSocket] = useState(null);
	const [totalNotifications, setTotalNotifications] = useState(0);

	const chatWindowRef = useRef(null);

	const loadContacts = async () => {
		setContacts(await getContacts());
		setFilteredContacts(await getContacts());
	};

	const handleCloseChat = () => {
		setIsChatOpen(false);
		setSelectedContact(null);
		setActive(0);
	};

	const handleContactClick = (contact) => {
		setIsChatOpen(true);
		setSelectedContact(contact);
		setActive(1);
	};

	const handleIMSocketMessage = useCallback((event) => {
		const message = JSON.parse(event.data);

		switch (message.type) {
			case MessageType.IMCONTACTS: {
				setOnlineIds(message.data);
				break;
			}

			case MessageType.MASSAGE: {
				if (chatWindowRef.current) {
					chatWindowRef.current.handleSeen();

					chatWindowRef.current.handleIncomingMessage(message);
				}

				break;
			}

			case MessageType.MESSAGE_NOTIFICATION: {
				setNotifications(message.data);
				break;
			}

			case MessageType.BOTSTARTTHINKING: {
				if (chatWindowRef.current) {
					chatWindowRef.current.handleBotStatus(message);
				}
				break;
			}

			case MessageType.BOTENDTHINKING: {
				if (chatWindowRef.current) {
					chatWindowRef.current.handleBotStatus(message);
				}
				break;
			}
			default: {
				break;
			}
		}
	}, []);

	const handleIsOnline = (userId) => {
		if (onlineIds) {
			return onlineIds.find((id) => id === userId) ? true : false;
		}
		else {
			return false;
		}
	};

	useEffect(() => {}, [selectedContact]);

	useEffect(() => {
		if (contactsQuery && !!contactsQuery.length) {
			const filteredContacts = contacts.filter((contact) =>
				contact.name
					.toLowerCase()
					.startsWith(contactsQuery.toLowerCase())
			);

			setFilteredContacts(filteredContacts);
		}
		else {
			setFilteredContacts(contacts);
		}
	}, [contactsQuery, contacts]);

	useEffect(() => {
		if (socket) {

			// eslint-disable-next-line react-compiler/react-compiler
			socket.onmessage = handleIMSocketMessage;
		}
	}, [socket, handleIMSocketMessage]);

	useEffect(() => {
		if (contacts && !!contacts.length) {
			const _self = contacts.find((contact) => contact.self);

			setSelfContact(_self);
		}
	}, [contacts]);

	useEffect(() => {
		let total = 0;
		if (notifications) {
			Object.keys(notifications).map((key) => {
				total += notifications[key];
			});
		}
		setTotalNotifications(total);
	}, [notifications]);

	useEffect(() => {
		if (!socket) {
			liferayInstantMessagingConnect().then((_socket) => {
				if (_socket) {
					setSocket(_socket);
					loadContacts();
				}
			});
		}
	}, [socket]);

	return (
		<div className="bg-white">
			<ClayPanel
				collapsable
				displayTitle={
					<>
						<ClayBadge
							className="total-notification-badge"
							displayType="info"
							label={totalNotifications}
						/>
						<label>Liferay Instant Messaging</label>
					</>
				}
				displayType="secondary"
				expanded={expanded}
				onExpandedChange={setExpanded}
				showCollapseIcon={true}
			>
				<ClayPanel.Body className="instant-messaging-messaging">
					{selfContact && (
						<>
							<ClayTabs.Content
								activeIndex={active}
								className="h-100 mx-auto w-100"
								fade
							>
								<ClayTabs.TabPane aria-labelledby="tab-1">
									<ClayList>
										<ClayForm.Group>
											<ClayInput.Group>
												<ClayInput.GroupItem>
													<ClayInput
														onChange={(event) => {
															setContactsQuery(
																event.target
																	.value
															);
														}}
														placeholder="Search for contact..."
														type="text"
													/>
												</ClayInput.GroupItem>
											</ClayInput.Group>
										</ClayForm.Group>

										{!isChatOpen && (
											<div className="contacts-list">
												{filteredContacts &&
													!!filteredContacts.length &&
													filteredContacts
														.filter(
															(contact) =>
																!contact.self
														)
														.map(
															(
																contact,
																index
															) => (
																<ContactCard
																	detail={
																		contact
																	}
																	handleClick={() => {
																		handleContactClick(
																			contact
																		);
																	}}
																	key={`contact_${index}`}
																	notifications={
																		notifications &&
																		notifications[
																			contact
																				.userId
																		]
																			? notifications[
																					contact
																						.userId
																				]
																			: 0
																	}
																	online={handleIsOnline(
																		contact.userId
																	)}
																></ContactCard>
															)
														)}
											</div>
										)}
									</ClayList>
								</ClayTabs.TabPane>

								<ClayTabs.TabPane aria-labelledby="tab-2">
									{selectedContact && (
										<>
											<ClayToolbar className="mb-3">
												<ClayToolbar.Nav>
													<ClayToolbar.Item
														className="text-left"
														expand
													>
														<ClayToolbar.Section>
															<ClayIcon symbol="user" />

															<label className="mx-1 text-capitalize">
																{
																	selectedContact.name
																}
															</label>
														</ClayToolbar.Section>
													</ClayToolbar.Item>

													<ClayToolbar.Item>
														<ClayButtonWithIcon
															aria-label="Close"
															displayType="unstyled"
															onClick={
																handleCloseChat
															}
															size="xs"
															symbol="times"
															title="Close"
														/>
													</ClayToolbar.Item>
												</ClayToolbar.Nav>
											</ClayToolbar>

											<ChatWindow
												connection={socket}
												ref={chatWindowRef}
												self={selfContact}
												target={selectedContact}
											></ChatWindow>
										</>
									)}
								</ClayTabs.TabPane>
							</ClayTabs.Content>
						</>
					)}

					{!selfContact && (
						<div>
							<ClayAlert
								displayType="warning"
								title="Unavailable"
							>
								Liferay Instant Messaging is unavailable for
								users who are not logged in.
							</ClayAlert>
						</div>
					)}
				</ClayPanel.Body>
			</ClayPanel>
		</div>
	);
});

export default MainWindow;
