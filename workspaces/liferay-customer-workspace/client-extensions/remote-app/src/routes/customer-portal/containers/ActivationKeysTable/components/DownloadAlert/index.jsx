/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {memo} from 'react';
import {
	ALERT_DOWNLOAD_TYPE,
	AUTO_CLOSE_ALERT_TIME,
} from '../../../../utils/constants';

const DownloadAlert = ({downloadStatus, message, setDownloadStatus}) => (
	<ClayAlert.ToastContainer>
		<ClayAlert
			autoClose={AUTO_CLOSE_ALERT_TIME[downloadStatus]}
			className="cp-activation-key-download-alert"
			displayType={ALERT_DOWNLOAD_TYPE[downloadStatus]}
			onClose={() => setDownloadStatus('')}
		>
			{message}
		</ClayAlert>
	</ClayAlert.ToastContainer>
);

export default memo(DownloadAlert);
