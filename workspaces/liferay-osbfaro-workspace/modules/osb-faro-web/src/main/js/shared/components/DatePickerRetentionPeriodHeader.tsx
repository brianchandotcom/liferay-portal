/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text as ClayText} from '@clayui/core';
import React from 'react';
import {sub} from '~/shared/util/lang';

export const DatePickerRetentionPeriodHeader =
	function DatePickerRetentionPeriodHeader({
		retentionPeriod,
	}: {
		retentionPeriod: number;
	}) {
		return (
			<>
				<ClayText size={2} weight="semi-bold">
					{Liferay.Language.get('custom-range').toUpperCase()}
				</ClayText>
				<div>
					<ClayText color="secondary" size={2}>
						{sub(
							Liferay.Language.get(
								'dates-prior-to-x-months-cannot-be-selected-due-to-your-workspaces-data-retention-period'
							),
							[retentionPeriod]
						)}
					</ClayText>
				</div>
			</>
		);
	};
