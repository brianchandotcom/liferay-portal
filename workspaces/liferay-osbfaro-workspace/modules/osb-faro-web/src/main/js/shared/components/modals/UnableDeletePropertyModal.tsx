/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import React from 'react';
import Modal from '~/shared/components/modal';
import {sub} from '~/shared/util/lang';
import URLConstants from '~/shared/util/url-constants';

const UnableDeletePropertyModal: React.FC<{
	onClose: () => void;
}> = ({onClose}) => (
	<Modal type="danger">
		<Modal.Header
			onClose={onClose}
			title={Liferay.Language.get('unable-to-delete-property')}
		/>

		<Modal.Body inlineScroller>
			<p className="text-secondary">
				{sub(
					Liferay.Language.get(
						'ensure-no-sites-and-channels-are-assigned-to-it-before-deleting-a-property'
					),
					[
						<ClayLink
							href={URLConstants.DeletePropertyDocumentation}
							key="URL"
							target="_blank"
						>
							{Liferay.Language.get(
								'access-our-documentation-to-learn-more'
							)}
						</ClayLink>,
					],
					false
				)}
			</p>
		</Modal.Body>

		<Modal.Footer>
			<ClayButton
				className="button-root"
				displayType="danger"
				onClick={onClose}
			>
				{Liferay.Language.get('done')}
			</ClayButton>
		</Modal.Footer>
	</Modal>
);

export default UnableDeletePropertyModal;
