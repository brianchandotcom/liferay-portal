/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Table from '../../../../../../../../common/components/Table';
import {TypeActivityKey} from '../../../../../../../../common/enums/TypeActivityKey';
import MDFRequestActivity from '../../../../../../../../common/interfaces/mdfRequestActivity';
import {Liferay} from '../../../../../../../../common/services/liferay';
import ActivityContent from './components/ActivityContent';
import getContentMarketFields from './utils/getContentMarketFields';
import getDigitalMarketFields from './utils/getDigitalMarketFields';
import getEventFields from './utils/getEventFields';
import getMiscellaneousMarketing from './utils/getMiscellaneousMarketing';

interface IProps {
	mdfRequestActivity: MDFRequestActivity;
}

interface Item {
	[key: string]: string | undefined;
}

type TypeOfActivityComponent = {
	[key in TypeActivityKey]: Item[];
};
const ActivityReviewEntry = ({mdfRequestActivity}: IProps) => {
	const fieldsByTypeActivity: TypeOfActivityComponent = {
		[TypeActivityKey.DIGITAL_MARKETING]: getDigitalMarketFields(
			mdfRequestActivity
		),
		[TypeActivityKey.CONTENT_MARKETING]: getContentMarketFields(
			mdfRequestActivity
		),
		[TypeActivityKey.EVENT]: getEventFields(mdfRequestActivity),
		[TypeActivityKey.MISCELLANEOUS_MARKETING]: getMiscellaneousMarketing(
			mdfRequestActivity
		),
	};

	const options = {timeZone: 'UTC'};

	return (
		<>
			<Table<Item>
				className="bg-brand-primary-lighten-6 border-top table-striped"
				columns={[
					{
						columnKey: 'title',
						label: 'Campaign Activity',
					},
					{
						columnKey: 'value',
						label: '',
					},
				]}
				rows={[
					{
						title: 'Activity name',
						value: mdfRequestActivity.name,
					},
					{
						title: 'Type of Activity',
						value: mdfRequestActivity.typeActivity.name,
					},
					{
						title: 'Tactic',
						value: mdfRequestActivity.tactic.name,
					},
					...fieldsByTypeActivity[
						mdfRequestActivity.typeActivity.key as TypeActivityKey
					],
					{
						title: 'Start Date',
						value:
							mdfRequestActivity.startDate &&
							new Date(
								mdfRequestActivity.startDate
							).toLocaleDateString(
								Liferay.ThemeDisplay.getBCP47LanguageId(),
								options
							),
					},
					{
						title: 'End Date',
						value:
							mdfRequestActivity.endDate &&
							new Date(
								mdfRequestActivity.endDate
							).toLocaleDateString(
								Liferay.ThemeDisplay.getBCP47LanguageId(),
								options
							),
					},
				]}
			/>

			<ActivityContent mdfRequestActivity={mdfRequestActivity} />
		</>
	);
};
export default ActivityReviewEntry;
