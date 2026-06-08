/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import getCN from 'classnames';
import React, {FC} from 'react';
import NoResultsDisplay from '~/shared/components/NoResultsDisplay';

interface IErrorDisplayProps extends React.HTMLAttributes<HTMLElement> {
	buttonLabel?: string;
	message?: string;
	onReload?: () => void;
	spacer?: boolean;
}

const ErrorDisplay: FC<IErrorDisplayProps> = ({
	buttonLabel = Liferay.Language.get('reload'),
	className,
	message = Liferay.Language.get('an-unexpected-error-occurred'),
	onReload,
	spacer = false,
}) => (
	<NoResultsDisplay
		className={getCN(
			'error-display-root',
			'flex-grow-1',
			{'error-spacer': spacer},
			className
		)}
		title={message}
	>
		{onReload && (
			<ClayButton
				className="button-root"
				displayType="secondary"
				onClick={() => onReload()}
			>
				{buttonLabel}
			</ClayButton>
		)}
	</NoResultsDisplay>
);

export default ErrorDisplay;
