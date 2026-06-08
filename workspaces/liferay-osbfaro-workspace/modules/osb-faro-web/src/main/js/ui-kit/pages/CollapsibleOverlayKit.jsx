/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useState} from 'react';
import CollapsibleOverlay from '~/shared/components/CollapsibleOverlay';

const CollapsibleOverlayKit = function CollapsibleOverlayKit() {
	const [visible, setVisible] = useState(true);

	return (
		<div style={{height: '500px', position: 'relative'}}>
			<ClayButton
				className="button-root"
				displayType="secondary"
				onClick={() => setVisible(true)}
			>
				toggle collapsible
			</ClayButton>

			<CollapsibleOverlay
				onClose={() => setVisible(false)}
				title="Collapsible Title"
				visible={visible}
			>
				<div style={{padding: '16px'}}>Collapsible Child</div>
			</CollapsibleOverlay>
		</div>
	);
};

export default CollapsibleOverlayKit;
