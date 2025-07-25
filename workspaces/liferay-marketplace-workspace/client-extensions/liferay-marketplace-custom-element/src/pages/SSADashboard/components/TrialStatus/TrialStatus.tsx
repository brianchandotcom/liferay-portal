/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import {OrderStatus as Status} from '../../../../enums/Order';
import {TRIAL_STATUS_LABEL} from '../../constants';

import './TrialStatus.scss';

type TrialStatusProps = {
	trialStatus: string;
};

const TrialStatus = ({trialStatus}: TrialStatusProps) => (
	<>
		<ClayIcon
			className={classNames('mr-2 trial-status-icon', {
				'trial-status-icon-completed': [
					Status.COMPLETED,
					Status.APPROVED,
				].includes(trialStatus as Status),
				'trial-status-icon-in_progress':
					Status.IN_PROGRESS === trialStatus,
				'trial-status-icon-pending': Status.PENDING === trialStatus,
				'trial-status-icon-processing':
					trialStatus === Status.PROCESSING,
			})}
			symbol="circle"
		/>

		<span className="trial-status-text">
			{TRIAL_STATUS_LABEL[trialStatus as keyof typeof TRIAL_STATUS_LABEL]}
		</span>
	</>
);

export default TrialStatus;
