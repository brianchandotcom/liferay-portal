/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

import {ExtendRequestStatus} from '../../enums/SSATrials';

import './ExtensionStatus.scss';

type ExtensionStatusProps = {
	extensionStatus: keyof typeof extensionStatusLabel;
};

const extensionStatusLabel = {
	approved: 'Approved',
	autoapproved: 'Auto  Approved',
	extensionexpired: 'Extension Expired',
	notrequested: 'Not Requested',
	pending: 'Pending',
	rejected: 'Rejected',
};

const ExtensionStatus = ({extensionStatus}: ExtensionStatusProps) => (
	<span
		className={classNames('extension-status', {
			'extension-status-approved': [
				ExtendRequestStatus.APPROVED,
				ExtendRequestStatus.AUTO_APPROVED,
			].includes(extensionStatus as ExtendRequestStatus),
			'extension-status-expired': [
				ExtendRequestStatus.EXTENSION_EXPIRED,
				ExtendRequestStatus.REJECTED,
			].includes(extensionStatus as ExtendRequestStatus),
			'extension-status-not-requested':
				extensionStatus === ExtendRequestStatus.NOT_REQUESTED,
			'extension-status-pending':
				extensionStatus === ExtendRequestStatus.PENDING,
		})}
	>
		{extensionStatusLabel[extensionStatus]}
	</span>
);
export default ExtensionStatus;
