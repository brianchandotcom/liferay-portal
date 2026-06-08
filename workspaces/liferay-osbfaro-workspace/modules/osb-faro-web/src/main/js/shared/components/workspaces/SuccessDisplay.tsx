/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import React from 'react';
import Sheet from '~/shared/components/Sheet';
import WorkspacesBasePage from '~/shared/components/workspaces/BasePage';
import {sub} from '~/shared/util/lang';
import URLConstants from '~/shared/util/url-constants';

const SuccessDisplay = ({friendlyURL}: {friendlyURL: string}) => {
	const link = (
		<ClayLink
			href={`https://analytics.liferay.com/workspace${friendlyURL}`}
			key="link"
		>
			{`analytics.liferay.com/workspace${friendlyURL}`}
		</ClayLink>
	);

	return (
		<WorkspacesBasePage title={Liferay.Language.get('new-workspace')}>
			<Sheet>
				<Sheet.Header className="mb-4">
					<h3 className="title">
						{Liferay.Language.get(
							'your-workspace-is-being-created'
						)}
					</h3>
				</Sheet.Header>
				<Sheet.Body>
					<Sheet.Section>
						<p>
							{sub(
								Liferay.Language.get(
									'well-send-you-an-email-once-its-ready-to-access-at-this-url-x'
								),
								[link],
								false
							)}
						</p>
						<p>
							{sub(
								Liferay.Language.get(
									'you-can-also-leave-this-page-open-and-well-notify-you-here.it-should-take-around-x-hour'
								),
								[1]
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
};

export default SuccessDisplay;
