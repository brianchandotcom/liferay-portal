/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {get} from 'lodash';
import PropTypes from 'prop-types';
import React from 'react';
import {connect} from 'react-redux';
import {compose} from 'redux';
import {updateDefaultChannelId} from '~/shared/actions/preferences';
import {collapseSidebar} from '~/shared/actions/sidebar';
import * as API from '~/shared/api';
import {getDefaultChannel} from '~/shared/components/channels-menu';
import Sidebar from '~/shared/components/sidebar';
import {ActionType, ChannelContext} from '~/shared/context/channel';
import {hasChanges} from '~/shared/util/react';

import {User} from '../util/records';
import checkProjectState from './CheckProjectState';
import checkSegmentLink from './CheckSegmentLink';
import checkValidChannel from './CheckValidChannel';
import withCurrentUser from './WithCurrentUser';
import withDefaultChannelId from './WithDefaultChannelId';
import withQuery from './WithQuery';
import {withError, withLoading} from './util';

/**
 * Wraps a component with the sidebar, and also comes with the
 * CurrentUser and Project HOCs built in.
 * @param {string|function} sidebarIdorFn - The sidebarId to mark as active. You
 * can also pass a function that takes the components props and should return the
 * active sidebar id
 * @returns {function} - The sidebar hoc with the applied preferences.
 */

export default compose(
	checkProjectState,
	checkSegmentLink,
	withCurrentUser,
	connect(
		(store, {currentUser}) => ({
			collapsed: store.getIn(['sidebar', String(currentUser.id)], false),
		}),
		{collapseSidebar, updateDefaultChannelId}
	),
	withQuery(
		API.channels.fetchAll,
		({groupId}) => ({groupId}),
		({data, ...otherParams}) => ({
			channels: get(data, 'items', null),
			...otherParams,
		})
	),
	withError(),
	withLoading(),
	withDefaultChannelId,
	checkValidChannel,
	(WrappedComponent) => {
		class WithSidebar extends React.Component {
			static contextType = ChannelContext;

			static propTypes = {
				channels: PropTypes.arrayOf(
					PropTypes.shape({
						createTime: PropTypes.number,
						id: PropTypes.string,
						name: PropTypes.string,
						permissionType: PropTypes.number,
					})
				),
				collapsed: PropTypes.bool.isRequired,

				collapseSidebar: PropTypes.func.isRequired,
				currentUser: PropTypes.instanceOf(User).isRequired,
				defaultChannelId: PropTypes.string,
				groupId: PropTypes.string.isRequired,
				location: PropTypes.object,
			};

			state = {
				showTransition: false,
			};

			constructor(props, {channelDispatch}) {
				super(props);

				const {
					channels,
					defaultChannelId,
					groupId,
					updateDefaultChannelId,
				} = props;

				this._toggleSidebarEvent = new CustomEvent('toggleSidebar');

				const channel = getDefaultChannel(defaultChannelId, channels);

				if (channel && defaultChannelId !== channel.id) {
					updateDefaultChannelId({
						defaultChannelId: channel.id,
						groupId,
					});
				}

				channelDispatch({
					payload: channel,
					type: ActionType.setSelectedChannel,
				});

				channelDispatch({
					payload: channels,
					type: ActionType.setChannels,
				});
			}

			componentDidMount() {
				this.setState({
					showTransition: true,
				});
			}

			componentDidUpdate(prevProps) {
				const {channelDispatch} = this.context;

				if (hasChanges(prevProps, this.props, 'collapsed')) {
					setTimeout(
						() => window.dispatchEvent(this._toggleSidebarEvent),
						250
					);
				}

				if (hasChanges(prevProps, this.props, 'defaultChannelId')) {
					const {
						context: {channels},
						props: {defaultChannelId},
					} = this;

					channelDispatch({
						payload: getDefaultChannel(defaultChannelId, channels),
						type: ActionType.setSelectedChannel,
					});
				}
			}

			@autobind
			handleSidebarToggle() {
				const {collapseSidebar, collapsed, currentUser} = this.props;

				collapseSidebar({
					collapsed: !collapsed,
					currentUserId: currentUser.id,
				});
			}

			render() {
				const {
					context: {channels, selectedChannel},
					props: {
						className,
						collapsed,
						currentUser,
						groupId,
						location,
						...otherProps
					},
					state: {showTransition},
				} = this;

				const classes = getCN('with-sidebar-root', className, {
					'has-sidebar': showTransition,
					'sidebar-collapsed': collapsed,
				});

				return (
					<div className={classes}>
						<Sidebar
							activePathname={location.pathname}
							channelId={selectedChannel && selectedChannel.id}
							channels={channels}
							collapsed={collapsed}
							currentUser={currentUser}
							groupId={groupId}
							onToggle={this.handleSidebarToggle}
						/>

						<WrappedComponent
							{...otherProps}
							currentUser={currentUser}
							groupId={groupId}
						/>
					</div>
				);
			}
		}

		return WithSidebar;
	}
);
