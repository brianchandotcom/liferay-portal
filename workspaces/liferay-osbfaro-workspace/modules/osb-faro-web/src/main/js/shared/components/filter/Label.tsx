/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

interface ILabelProps extends React.HTMLAttributes<HTMLSpanElement> {
	label: string;
	onRemove: () => void;
}

export const Label = function Label({label, onRemove}: ILabelProps) {
	return (
		<span className="label label-dismissible label-lg label-secondary">
			<span className="label-item label-item-expand">{label}</span>
			<span className="label-item label-item-after">
				<ClayButton
					aria-label={Liferay.Language.get('close')}
					className="button-root close"
					displayType="unstyled"
					onClick={onRemove}
				>
					<ClayIcon symbol="icon-root times" />
				</ClayButton>
			</span>
		</span>
	);
};
