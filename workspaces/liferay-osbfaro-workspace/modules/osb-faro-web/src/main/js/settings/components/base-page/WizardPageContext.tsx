/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useContext, useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {addAlert} from '~/shared/actions/alerts';
import {fetch} from '~/shared/api/data-source';
import {useQueryParams} from '~/shared/hooks/useQueryParams';
import {Alert} from '~/shared/types';
import {DataSource} from '~/shared/util/records';

interface IWizardPageContext {
	dataSource: DataSource | null;
	loadingContext: boolean;
	refetchDataSource: (dataSourceId: string) => void;
}

const WizardPageContext = createContext<IWizardPageContext>({
	dataSource: null,
	loadingContext: false,
	refetchDataSource: () => {},
});

async function fetchDataSource({
	dataSourceId,
	groupId,
	setDataSource,
	setLoading,
}: {
	dataSourceId: string;
	groupId: string;
	setDataSource: (ds: DataSource) => void;
	setLoading: (loading: boolean) => void;
}) {
	setLoading(true);

	try {
		const dataSource = await fetch({
			groupId,
			id: dataSourceId,
		});

		setDataSource(new DataSource(dataSource));
	}
	catch (error) {
		addAlert({
			alertType: Alert.Types.Error,
			message: Liferay.Language.get(
				'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists-please-contact-support'
			),
		});
	}
	finally {
		setLoading(false);
	}
}

export const useWizardPage = function useWizardPage() {
	return useContext(WizardPageContext);
};

export const WizardPageProvider = function WizardPageProvider({
	children,
}: {
	children: React.ReactNode;
}) {
	const {groupId = ''} = useParams<{groupId: string}>();
	const {dataSourceId} = useQueryParams();
	const [dataSource, setDataSource] = useState<DataSource | null>(null);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (dataSourceId && !dataSource) {
			fetchDataSource({
				dataSourceId,
				groupId,
				setDataSource,
				setLoading,
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [groupId, dataSourceId]);

	return (
		<WizardPageContext.Provider
			value={{
				dataSource,
				loadingContext: loading,
				refetchDataSource: (dataSourceId) => {
					fetchDataSource({
						dataSourceId,
						groupId,
						setDataSource,
						setLoading,
					});
				},
			}}
		>
			{children}
		</WizardPageContext.Provider>
	);
};
