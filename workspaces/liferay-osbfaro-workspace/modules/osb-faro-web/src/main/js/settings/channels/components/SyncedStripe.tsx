/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import {sub} from '~/shared/util/lang';

interface ISyncedStripeProps {
	channelsSyncedCount: number;
	sitesSyncedCount: number;
}

export function getTitle(
	sitesSyncedCount: number,
	channelsSyncedCount: number
): string | any[] {
	if (sitesSyncedCount === 1) {
		if (channelsSyncedCount === 1) {
			return Liferay.Language.get(
				'there-is-1-site-and-1-channel-synced-to-this-property'
			);
		}

		return sub(
			Liferay.Language.get(
				'there-is-1-site-and-x-channels-synced-to-this-property'
			),
			[channelsSyncedCount]
		);
	}

	if (channelsSyncedCount === 1) {
		return sub(
			Liferay.Language.get(
				'there-are-x-sites-and-1-channel-synced-to-this-property'
			),
			[sitesSyncedCount]
		);
	}

	return sub(
		Liferay.Language.get(
			'there-are-x-sites-and-x-channels-synced-to-this-property'
		),
		[sitesSyncedCount, channelsSyncedCount]
	);
}

const SyncedStripe: React.FC<ISyncedStripeProps> = ({
	channelsSyncedCount,
	sitesSyncedCount,
}) => (
	<div
		className={getCN('sites-synced-stripe-root', {
			empty: !sitesSyncedCount && !channelsSyncedCount,
		})}
	>
		<div className="align-items-center d-flex title">
			{getTitle(sitesSyncedCount, channelsSyncedCount)}
		</div>

		<div>
			{sub(
				Liferay.Language.get(
					'manage-sites-synced-to-this-property-by-going-to-x-in-your-dxp-instance'
				),
				[
					<b key="INSTANCE_SETTINGS">
						{Liferay.Language.get(
							'instance-settings-analytics-cloud'
						)}
					</b>,
				],
				false
			)}
		</div>
	</div>
);

export default SyncedStripe;
