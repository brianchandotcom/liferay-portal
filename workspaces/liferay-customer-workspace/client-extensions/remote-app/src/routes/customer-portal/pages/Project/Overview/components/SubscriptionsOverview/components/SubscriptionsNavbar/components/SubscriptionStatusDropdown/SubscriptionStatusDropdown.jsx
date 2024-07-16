/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useCallback, useMemo, useState} from 'react';
import i18n from '../../../../../../../../../../../common/I18n';
import Skeleton from '../../../../../../../../../../../common/components/Skeleton';
import getKebabCase from '../../../../../../../../../../../common/utils/getKebabCase';
import {SUBSCRIPTIONS_STATUS} from '../../../../../../../../../utils/constants';

const MAX_SUBSCRIPTION_STATUS = 3;

const SubscriptionStatusDropdown = ({disabled, loading, onClick}) => {
	const [active, setActive] = useState(false);
	const [items, setItems] = useState([
		{
			active: true,
			label: SUBSCRIPTIONS_STATUS.active,
		},
		{
			active: false,
			label: SUBSCRIPTIONS_STATUS.expired,
		},
		{
			active: false,
			label: SUBSCRIPTIONS_STATUS.future,
		},
	]);

	const activeItems = useMemo(() => items.filter((item) => item.active), [
		items,
	]);

	const getTriggerLabel = useCallback(() => {
		if (activeItems.length === MAX_SUBSCRIPTION_STATUS) {
			return i18n.translate('all');
		}

		return activeItems
			.map((item) => i18n.translate(getKebabCase(item.label)))
			.join(', ');
	}, [activeItems]);

	const handleOnClick = (index) => {
		items[index].active = !items[index].active;
		const currentActiveItems = items.filter((item) => item.active);

		if (currentActiveItems.length !== MAX_SUBSCRIPTION_STATUS) {
			onClick(currentActiveItems.map((item) => item.label));
		} else {
			onClick();
		}

		setItems([...items]);
	};

	const handleClickAll = () => {
		setItems((previousItems) => [
			...previousItems.map((item) => ({...item, active: true})),
		]);

		onClick(items.map((item) => item.label));
	};

	return (
		<div className="align-items-center d-flex ml-2 mt-2">
			<div className="font-weight-bold pr-1 text-paragraph-sm">
				{loading ? (
					<Skeleton height={18} width={40} />
				) : (
					`${i18n.translate('status')}:`
				)}
			</div>

			<DropDown
				active={active}
				closeOnClickOutside
				menuWidth="shrink"
				onActiveChange={setActive}
				trigger={
					<Button
						borderless
						className="align-items-center d-flex px-2"
						disabled={disabled || loading}
						size="sm"
					>
						{loading ? (
							<Skeleton height={18} width={46} />
						) : (
							getTriggerLabel()
						)}

						<span className="inline-item-after">
							<ClayIcon symbol="caret-bottom" />
						</span>
					</Button>
				}
			>
				<>
					{items.map((item, index) => (
						<DropDown.Item
							className="pr-6"
							disabled={
								(item.active && activeItems.length < 2) ||
								disabled
							}
							key={`${item.label}-${index}`}
							onClick={() => handleOnClick(index)}
							symbolRight={item.active && 'check'}
						>
							{i18n.translate(getKebabCase(item.label))}
						</DropDown.Item>
					))}

					<DropDown.Item
						className="pr-6"
						disabled={
							activeItems.length === MAX_SUBSCRIPTION_STATUS ||
							disabled
						}
						onClick={() => handleClickAll()}
						symbolRight={
							activeItems.length === MAX_SUBSCRIPTION_STATUS &&
							'check'
						}
					>
						{i18n.translate('all')}
					</DropDown.Item>
				</>
			</DropDown>
		</div>
	);
};

export default SubscriptionStatusDropdown;
