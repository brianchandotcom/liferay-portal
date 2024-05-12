/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ComponentProps, ReactNode} from 'react';

type LoadingProps = ComponentProps<typeof ClayLoadingIndicator>;

type FullScreenProps = {
	children: ReactNode;
};

const Loading: React.FC<LoadingProps> & {FullScreen: typeof FullScreen} = ({
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

const FullScreen: React.FC<FullScreenProps> = ({children}) => (
	<div className="loading-overlay">
		<div className="loading-container">
			<div className="loading-text">
				<Loading className="mb-6" />
				<span className="mt-4">{children}</span>
			</div>
		</div>
	</div>
);

Loading.FullScreen = FullScreen;

export default Loading;
