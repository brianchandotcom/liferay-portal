/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

interface IStepListProps {
	className?: string;
	hideBullets: Boolean;
	secondaryInfo?: string;
	steps: Array<string>;
}

const StepList: React.FC<IStepListProps> = ({
	className,
	hideBullets = false,
	secondaryInfo,
	steps = [],
}) => (
	<div
		className={getCN(
			'step-list-root',
			{'hide-bullets': hideBullets},
			className
		)}
	>
		{secondaryInfo && <b>{secondaryInfo}</b>}

		<ul>
			{steps.map((step, i) => (
				<li key={i}>{step}</li>
			))}
		</ul>
	</div>
);

export default StepList;
