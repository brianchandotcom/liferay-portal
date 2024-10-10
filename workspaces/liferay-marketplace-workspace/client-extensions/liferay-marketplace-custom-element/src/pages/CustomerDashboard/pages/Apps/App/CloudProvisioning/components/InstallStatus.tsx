/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import {InstallStatus} from '../types';

type InstallationStatusProps = {
	children?: string;
	status?: string;
};

const InstallationStatus = ({children, status}: InstallationStatusProps) => (
	<div className="align-items-center d-flex">
		<ClayIcon
			className={classNames('mr-2', {
				'text-danger': status === InstallStatus.EXPIRED,
				'text-info': status === InstallStatus.IN_PROGRESS,
				'text-primary': status === InstallStatus.READY_TO_INSTALL,
				'text-success': status === InstallStatus.INSTALLED,
			})}
			fontSize="0.6rem"
			symbol="circle"
		/>

		<span className="font-weight-bold text-black-50 text-capitalize">
			{children}
		</span>
	</div>
);

export default InstallationStatus;
