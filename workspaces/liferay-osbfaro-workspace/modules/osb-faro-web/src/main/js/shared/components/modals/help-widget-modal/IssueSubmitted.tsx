/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';
import Modal from '~/shared/components/modal';

import {IHelpWidgetScreenProps} from './types';

const IssueSubmitted: React.FC<IHelpWidgetScreenProps> = ({onClose}) => (
	<>
		<Modal.Header
			onClose={onClose}
			title={Liferay.Language.get('issue-submitted')}
		/>

		<Modal.Body className="align-items-center d-flex flex-column">
			<ClayIcon
				className="icon-root icon-size-xxxl my-5"
				symbol="ac_no_sites"
			/>

			<h3>{Liferay.Language.get('message-received')}</h3>

			<p className="description">
				{Liferay.Language.get(
					'thanks-for-your-contribution-well-look-in-to-this-as-soon-as-possible'
				)}
			</p>
		</Modal.Body>
	</>
);

export default IssueSubmitted;
