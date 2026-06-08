/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text as ClayText} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import getCN from 'classnames';
import React from 'react';

interface IDropdownRangeKeyLegacyProps {
	onClickSeeMore: () => void;
	onClickShowDatePicker: () => void;
	seeMore: boolean;
	selectedItem?: {[key: string]: any} | null;
}

export const DropdownRangeKeyLegacy = function DropdownRangeKeyLegacy({
	onClickSeeMore,
	onClickShowDatePicker,
	seeMore,
	selectedItem,
}: IDropdownRangeKeyLegacyProps) {
	return (
		<>
			{!seeMore && (
				<ClayDropDown.Item
					className="c-pointer"
					key="SEE_MORE"
					onClick={onClickSeeMore}
				>
					{Liferay.Language.get('more-preset-periods')}
				</ClayDropDown.Item>
			)}
			<ClayDropDown.Divider />
			<ClayDropDown.Item
				className={getCN('c-pointer', {
					active: selectedItem?.value === 'CUSTOM',
				})}
				key="CUSTOM"
				onClick={onClickShowDatePicker}
			>
				<ClayText size={3} weight="semi-bold">
					{Liferay.Language.get('custom-range')}
				</ClayText>
			</ClayDropDown.Item>
		</>
	);
};
