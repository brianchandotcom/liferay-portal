/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
import {fetchProject} from '~/shared/actions/projects';
import Sheet from '~/shared/components/Sheet';
import WorkspacesBasePage from '~/shared/components/workspaces/BasePage';
import withPolling from '~/shared/hoc/WithPolling';
import {ProjectStates} from '~/shared/util/constants';
import URLConstants from '~/shared/util/url-constants';

const ActivatingDisplay = () => (
	<WorkspacesBasePage title={Liferay.Language.get('activating-workspace')}>
		<Sheet>
			<Sheet.Header className="mb-4">
				<h3 className="title">
					{Liferay.Language.get('your-workspace-is-being-activated')}
				</h3>
			</Sheet.Header>
			<Sheet.Body>
				<Sheet.Section>
					<p>
						{Liferay.Language.get(
							'this-process-will-take-a-couple-minutes'
						)}
					</p>
					<p>
						{Liferay.Language.get(
							'in-the-meantime-check-out-our-documentation-to-get-familiar-with-the-features'
						)}
					</p>
				</Sheet.Section>
			</Sheet.Body>

			<Sheet.Footer divider={false}>
				<ClayLink
					button
					className="button-root"
					displayType="primary"
					href={URLConstants.DocumentationAdminLink}
					target="_blank"
				>
					{Liferay.Language.get('check-out-docs')}
				</ClayLink>
			</Sheet.Footer>
		</Sheet>
	</WorkspacesBasePage>
);

export default withPolling(
	fetchProject,
	({state}: {state: ProjectStates}) => state !== ProjectStates.Activating
)(ActivatingDisplay);
