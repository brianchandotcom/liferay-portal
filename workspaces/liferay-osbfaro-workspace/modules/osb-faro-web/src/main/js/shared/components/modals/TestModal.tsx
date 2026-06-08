/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {noop} from 'lodash';
import React from 'react';
import Modal from '~/shared/components/modal';

const TestModal: React.FC<{onClose: () => void; title: string}> = ({
	onClose = noop,
	title = 'Modal',
}) => (
	<Modal>
		<Modal.Header onClose={() => onClose} title={title} />

		<Modal.Body inlineScroller>
			<div className="h4">Modal Body</div>
		</Modal.Body>

		<Modal.Footer>
			<ClayButton
				className="button-root"
				displayType="secondary"
				onClick={() => onClose}
			>
				Cancel
			</ClayButton>

			<ClayButton className="button-root" displayType="primary">
				Submit
			</ClayButton>
		</Modal.Footer>
	</Modal>
);

export default TestModal;
