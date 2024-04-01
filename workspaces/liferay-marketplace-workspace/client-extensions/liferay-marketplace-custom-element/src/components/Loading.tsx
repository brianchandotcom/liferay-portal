/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

type LoadingProps = {
	className?: string;
};

const Loading: React.FC<LoadingProps> = ({className}) => (
	<ClayLoadingIndicator
		className={className}
		displayType="primary"
		shape="squares"
		size="lg"
	/>
);

export default Loading;
