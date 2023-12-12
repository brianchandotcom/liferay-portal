/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {ReactNode} from 'react';

const statusBarClassNames = {
	blocked: 'blocked',
	complete: 'completed',
	didnotrun: 'didnotrun',
	failed: 'failed',
	inanalysis: 'in-analysis',
	incomplete: 'light',
	inprogress: 'inprogress',
	open: 'open',
	other: 'primary',
	passed: 'passed',
	scheduled: 'untested',
	self: 'info',
	testfix: 'test-fix',
	untested: 'untested',
};

export type StatusBadgeType = keyof typeof statusBarClassNames;

export type StatusBadgeProps = {
	children: ReactNode;
	type: StatusBadgeType;
};

const StatusBadge: React.FC<StatusBadgeProps> = ({children, type}) => (
	<span
		className={classNames(
			'label label-chart text-uppercase text-nowrap',
			(statusBarClassNames as any)[type?.toLowerCase()] ||
				type?.toLowerCase().replace(' ', '-')
		)}
	>
		{children}
	</span>
);

export default StatusBadge;
