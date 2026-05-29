/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ReactNode} from 'react';

import './KnockoutEmptyState.scss';

type KnockoutEmptyStateProps = {
	description: string | ReactNode;
	title: string;
};

export default function KnockoutEmptyState(props: KnockoutEmptyStateProps) {
	return (
		<div
			className="align-items-center d-flex flex-column justify-content-center px-2 text-center"
			id="knockout-empty-state"
		>
			<div className="alert">
				<ClayIcon color="#0B5FFF" fontSize={32} symbol="warning-full" />
			</div>

			<h3 className="mb-4">{props.title}</h3>

			<small>{props.description}</small>
		</div>
	);
}
