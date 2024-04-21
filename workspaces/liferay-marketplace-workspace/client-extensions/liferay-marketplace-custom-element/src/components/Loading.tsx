/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ComponentProps} from 'react';

type LoadingProps = ComponentProps<typeof ClayLoadingIndicator>;

const Loading: React.FC<LoadingProps> = ({
	displayType = 'primary',
	shape = 'squares',
	size = 'lg',
	...props
}) => (
	<ClayLoadingIndicator
		displayType={displayType}
		shape={shape}
		size={size}
		{...props}
	/>
);

export default Loading;
