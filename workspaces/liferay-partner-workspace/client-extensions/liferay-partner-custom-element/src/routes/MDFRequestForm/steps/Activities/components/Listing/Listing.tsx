/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import Button from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ArrayHelpers} from 'formik';

import LiferayPicklist from '../../../../../../common/interfaces/liferayPicklist';
import MDFRequestActivity from '../../../../../../common/interfaces/mdfRequestActivity';
import ActivityPanel from '../../../../components/ActivityPanel';
import getNewActivity from '../../utils/getNewActivity';

interface IProps {
	activities: MDFRequestActivity[];
	claimPercent: number;
	currency: LiferayPicklist;
	hasActivityErrorsByIndex: (index: number) => boolean;
	onAdd: () => void;
	onEdit: (index: number) => void;
	onRemove: (index: number) => void;
	overallCampaignName: string;
}

const Listing = ({
	activities,
	claimPercent,
	currency,
	hasActivityErrorsByIndex,
	onAdd,
	onEdit,
	onRemove,
	overallCampaignName,
	push,
}: IProps & ArrayHelpers) => {
	const handleOnAdd = () => {
		push(getNewActivity(claimPercent, currency));

		onAdd();
	};

	return (
		<>
			<div>
				{!!activities.length &&
					activities.map((activity, index) => (
						<>
							{!activity.removed && (
								<ActivityPanel
									activity={activity}
									hasErrors={hasActivityErrorsByIndex(index)}
									key={index}
									onEdit={() => onEdit(index)}
									onRemove={() => onRemove(index)}
									overallCampaignName={overallCampaignName}
								/>
							)}
						</>
					))}

				{!activities.length && (
					<ClayAlert displayType="info" title="Info:">
						No entries were found
					</ClayAlert>
				)}
			</div>

			<Button
				className="align-items-center d-flex"
				onClick={handleOnAdd}
				outline
				small
			>
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="plus" />
				</span>
				Add Activity
			</Button>
		</>
	);
};

export default Listing;
