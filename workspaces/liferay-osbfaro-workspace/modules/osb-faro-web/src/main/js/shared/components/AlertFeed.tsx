/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {List, Map} from 'immutable';
import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {CSSTransition, TransitionGroup} from 'react-transition-group';
import {RootState} from '~/shared/store';
import {Alert as AlertType} from '~/shared/types';

import {removeAlert} from '../actions/alerts';
import Alert, {ALERT_CONFIG_MAP, AlertTypes} from './Alert';

const {danger, info, secondary, success, warning} = ALERT_CONFIG_MAP;

type AlertDisplaysType = {
	[alertType: string]: {
		iconSymbol?: string;
		title?: string;
		type?: AlertTypes;
	};
};

const ALERT_DISPLAYS: AlertDisplaysType = {
	[AlertType.Types.Default]: info,
	[AlertType.Types.Error]: danger,
	[AlertType.Types.Pending]: secondary,
	[AlertType.Types.Success]: success,
	[AlertType.Types.Warning]: warning,
};

const connector = connect(
	(state: RootState) => ({
		alertsIMap: state.get('alerts'),
		modalActive: state.get('modals').size > 0,
	}),
	{
		removeAlert,
	}
);

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IAlertFeedProps
	extends React.HTMLAttributes<HTMLElement>,
		PropsFromRedux {}

export const AlertFeed = function AlertFeed({
	alertsIMap,
	className,
	modalActive = false,
	removeAlert,
}: IAlertFeedProps) {
	return (
		<div
			className={getCN(className, 'alert-feed-root alert-notifications', {
				'modal-active': modalActive,
			})}
		>
			<TransitionGroup>
				{alertsIMap
					.map((alertIMap: Map<string, any>) => {
						const {
							iconSymbol: symbol,
							title: label,
							type: display,
						} = ALERT_DISPLAYS[alertIMap.get('alertType')];

						const id = alertIMap.get('id');
						const message = alertIMap.get('message');

						return (
							<CSSTransition
								appear
								classNames="transition-slide-up"
								key={id}
								timeout={{enter: 150, exit: 150}}
							>
								<Alert
									iconSymbol={symbol}
									id={id}
									onClose={removeAlert}
									title={label}
									type={display}
								>
									{List.isList(message)
										? message.toJS()
										: message}
								</Alert>
							</CSSTransition>
						);
					})
					.toArray()}
			</TransitionGroup>
		</div>
	);
};

export default connector(AlertFeed);
