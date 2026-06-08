/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';
import Loading, {Align} from '~/shared/components/Loading';

import {useMutationObserver} from './utils';

interface IDownloadReportButton {
	disabled: boolean;
	loading?: boolean;
	onClick: () => void;
}

export const DownloadReportButton = function DownloadReportButton({
	disabled,
	loading = false,
	onClick,
}: IDownloadReportButton) {
	const {loadingCount} = useMutationObserver();

	return (
		<ClayButton
			borderless
			disabled={disabled || loading || loadingCount > 0}
			displayType="secondary"
			onClick={onClick}
			size="sm"
		>
			<ClayIcon className="mr-2" symbol="download" />

			{Liferay.Language.get('download-reports')}

			{(loading || loadingCount > 0) && <Loading align={Align.Right} />}
		</ClayButton>
	);
};
