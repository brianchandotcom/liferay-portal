/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

interface IRequestActionsRendererProps
	extends React.HTMLAttributes<HTMLTableCellElement> {
	data: {[key: string]: any};
	onAccept: (params: {emailAddress: string; id: string}) => any;
	onDecline: (params: {emailAddress: string; id: string}) => any;
}

const RequestActionsRenderer: React.FC<IRequestActionsRendererProps> = ({
	className,
	data: {emailAddress, id},
	onAccept,
	onDecline,
}) => (
	<td className={className}>
		<ClayButton
			className="button-root mr-3"
			displayType="primary"
			onClick={() => onAccept({emailAddress, id})}
			size="sm"
		>
			{Liferay.Language.get('accept')}
		</ClayButton>

		<ClayButton
			className="button-root"
			displayType="secondary"
			onClick={() => onDecline({emailAddress, id})}
			size="sm"
		>
			{Liferay.Language.get('decline')}
		</ClayButton>
	</td>
);

export default RequestActionsRenderer;
