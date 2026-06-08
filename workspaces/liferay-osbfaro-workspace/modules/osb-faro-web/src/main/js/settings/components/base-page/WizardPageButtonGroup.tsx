/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

interface IWizardPageButtonGroupProps {
	nextButtonLabel: string;
	nextButtonLoading?: boolean;
	onCancel: () => void;
	prevButtonLabel: string;
}

export const WizardPageButtonGroup = function WizardPageButtonGroup({
	nextButtonLabel,
	nextButtonLoading,
	onCancel,
	prevButtonLabel,
}: IWizardPageButtonGroupProps) {
	return (
		<div className="mt-5">
			<ClayButton
				block
				disabled={nextButtonLoading}
				loading={nextButtonLoading}
				type="submit"
			>
				{nextButtonLabel}
			</ClayButton>
			<ClayButton
				block
				borderless
				displayType="secondary"
				onClick={onCancel}
			>
				{prevButtonLabel}
			</ClayButton>
		</div>
	);
};
