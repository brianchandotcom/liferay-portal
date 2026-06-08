/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLazyQuery} from '@apollo/client';
import ClayForm from '@clayui/form';
import React, {useEffect, useState} from 'react';
import {useHistory} from 'react-router-dom';
import {WizardPageButtonGroup} from '~/settings/components/base-page/WizardPageButtonGroup';
import {updateSearchParams} from '~/settings/components/base-page/utis';
import {useInterval} from '~/shared/hooks/useInterval';
import DataSourceQuery, {
	DataSource,
	DataSourceSyncData,
} from '~/shared/queries/DataSourceQuery';
import {DataSourceTypes, OrderByDirections} from '~/shared/util/constants';
import {CREATE_DATE} from '~/shared/util/pagination';

import {ReviewSyncedDataFragment} from '../ReviewSyncedDataFragment';

const TIMEOUT_INTERVAL = 5000;

interface IReviewSyncedDataStepProps {
	onNext: () => void;
	onPrev: () => void;
}

const ReviewSyncedDataStep = ({onNext, onPrev}: IReviewSyncedDataStepProps) => {
	const history = useHistory();
	const [dataSource, setDataSource] = useState<DataSource>({
		contactsSyncDetails: {selected: false},
		id: '',
		sitesSyncDetails: {selected: false},
	});
	const [getDataSources, {data}] = useLazyQuery<DataSourceSyncData>(
		DataSourceQuery,
		{
			fetchPolicy: 'network-only',
			variables: {
				size: 1,
				sort: {
					column: CREATE_DATE,
					type: OrderByDirections.Descending,
				},
				type: DataSourceTypes.Liferay,
			},
		}
	);

	useInterval<void>(getDataSources, TIMEOUT_INTERVAL);

	useEffect(() => {
		if (data) {
			const dataSource = data.dataSources[0];

			setDataSource(dataSource);
		}
	}, [data]);

	useEffect(() => {
		getDataSources();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayForm
			onSubmit={async (event) => {
				event.preventDefault();

				updateSearchParams(history, 'dataSourceId', dataSource.id);

				onNext();
			}}
		>
			<ReviewSyncedDataFragment
				contactsSelected={dataSource?.contactsSyncDetails?.selected}
				sitesSelected={dataSource?.sitesSyncDetails?.selected}
			/>

			<WizardPageButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				onCancel={() => {
					updateSearchParams(history, 'dataSourceId', dataSource.id);

					onPrev();
				}}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

export {ReviewSyncedDataStep};
