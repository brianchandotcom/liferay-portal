/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import {OrderStatus} from '../../enums/OrderStatus';

import './LabelStatus.scss';

interface LabelStatusProps {
	provisioning?: string;
	provisioningLabel?: string;
}

const LabelStatus = ({provisioning, provisioningLabel}: LabelStatusProps) => {
	return (
		<>
			<ClayIcon
				className={classNames('mx-2 label-status-icon', {
					'label-status-icon-completed':
						provisioningLabel === OrderStatus.COMPLETED,
					'label-status-icon-pending':
						provisioningLabel === OrderStatus.PENDING,
					'label-status-icon-processing':
						provisioningLabel === OrderStatus.PROCESSING,
				})}
				symbol="circle"
			/>

			<span className="label-status-text">{provisioning}</span>
		</>
	);
};

export default LabelStatus;
