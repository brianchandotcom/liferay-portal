/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import React from 'react';
import Modal from '~/shared/components/modal';

interface IReadyToGoProps {
	onClose: () => void;
}

const ReadyToGo: React.FC<IReadyToGoProps> = ({onClose}) => (
	<>
		<Modal.Header onClose={onClose} />

		<Modal.Body>

			{/* TODO: LRAC-7427 Adjust SVGs with Linear Gradients */}
			<div className="analytics-onboarding-ready-to-use icon" />

			<div className="mb-4 text-center">
				<Text size={10} weight="bold">
					{Liferay.Language.get('you-are-ready-to-go')}
				</Text>

				<p>
					<Text color="secondary" size={6}>
						{Liferay.Language.get('your-workspace-is-all-set-up')}
					</Text>
				</p>
			</div>

			<div className="text-center">
				<p>
					<Text color="secondary">
						{Liferay.Language.get(
							'tracking-will-start-immediately-however-it-may-take-some-time-for-data-to-appear-in-your-workspace'
						)}
					</Text>
				</p>

				<p>
					<Text color="secondary">
						{Liferay.Language.get(
							'make-sure-to-set-your-time-period-to-last-24-hours-to-see-if-your-data-is-coming-in-correctly'
						)}
					</Text>
				</p>
			</div>
		</Modal.Body>

		<Modal.Footer className="d-flex justify-content-end">
			<ClayButton
				className="button-root"
				displayType="primary"
				onClick={onClose}
			>
				{Liferay.Language.get('done')}
			</ClayButton>
		</Modal.Footer>
	</>
);

export default ReadyToGo;
