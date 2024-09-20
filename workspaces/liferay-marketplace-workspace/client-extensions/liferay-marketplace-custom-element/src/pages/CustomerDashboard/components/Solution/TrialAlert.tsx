/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';

import {ORDER_WORKFLOW_STATUS_CODE} from '../../../../enums/Order';

const statusAlert = {
	[ORDER_WORKFLOW_STATUS_CODE.CANCELLED]: {
		displayType: 'danger',
		text: 'Your order has been cancelled. Please contact support if you have any questions.',
	},
	[ORDER_WORKFLOW_STATUS_CODE.ON_HOLD]: {
		displayType: 'secondary',
		text: 'Your order is currently on hold. Please check your email for further instructions.',
	},
	[ORDER_WORKFLOW_STATUS_CODE.PROCESSING]: {
		displayType: 'info',
		text: 'Your order is being processed. We will notify you once it is ready for the next step.',
	},
	[ORDER_WORKFLOW_STATUS_CODE.PENDING]: {
		displayType: 'warning',
		text: 'Your order is pending. Please wait a few minutes or hours for the processing to complete.',
	},
};

type TrialAlertProps = {
	orderStatusCode: number;
};

const TrialAlert: React.FC<TrialAlertProps> = ({orderStatusCode}) => {
	const alert = (statusAlert as any)[orderStatusCode];

	if (!alert) {
		return null;
	}

	return <ClayAlert displayType={alert.displayType}>{alert.text}</ClayAlert>;
};

export default TrialAlert;
