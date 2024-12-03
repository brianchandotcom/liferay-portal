/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
import i18n from '~/common/I18n';

import './SVAffectedVersions.css';

const SVAffectedVersions = ({
	affectedVersions,
}: {
	affectedVersions: string[] | undefined;
}) => {
	if (!affectedVersions || !affectedVersions.length) {
		return null;
	}

	const firstAffectedVersion = affectedVersions[0];
	const remainingVersions = affectedVersions.slice(1);
	const secondAffectedVersion = affectedVersions[1];

	const remainingCount = remainingVersions.length;

	const otherAffectedVersions =
		remainingCount > 0 ? `${remainingCount} others` : null;

	return (
		<div>
			<span className="label">{firstAffectedVersion}</span>

			{otherAffectedVersions && (
				<ClayTooltipProvider>
					<span
						className="label remaining-affected-versions"
						title={remainingVersions.join('\n')}
					>
						{remainingCount > 1
							? i18n.sub('x-others', [remainingCount])
							: secondAffectedVersion}
					</span>
				</ClayTooltipProvider>
			)}
		</div>
	);
};

export default SVAffectedVersions;
