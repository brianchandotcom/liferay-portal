/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import React from 'react';
import Modal from '~/shared/components/modal';

interface IWelcomeProps {
	groupId?: string;
	onClose: () => void;
	onNext: (increment?: number) => void;
}

const Welcome: React.FC<IWelcomeProps> = ({onClose, onNext}) => (
	<>
		<Modal.Header onClose={onClose} />

		<Modal.Body>

			{/* TODO: LRAC-7427 Adjust SVGs with Linear Gradients */}
			<div className="analytics-onboarding-welcome-usage-icon icon" />

			<div className="text-center">
				<Text size={10} weight="bold">
					{Liferay.Language.get('welcome-to-analytics-cloud')}
				</Text>

				<p>
					<Text color="secondary" size={6}>
						{Liferay.Language.get(
							'just-a-few-more-steps-to-set-up-your-workspace'
						)}
					</Text>
				</p>
			</div>
		</Modal.Body>

		<Modal.Footer className="d-flex justify-content-center">
			<ClayButton
				autoFocus
				className="button-root"
				displayType="primary"
				onClick={() => onNext()}
			>
				{Liferay.Language.get('next')}
			</ClayButton>
		</Modal.Footer>
	</>
);

export default Welcome;
