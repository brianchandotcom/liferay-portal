import React, {createContext, useContext, useEffect, useState} from 'react';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {DataSource} from 'shared/util/records';
import {fetch} from 'shared/api/data-source';
import {useParams} from 'react-router-dom';
import {useQueryParams} from 'shared/hooks/useQueryParams';

interface IWizardPageContext {
	dataSource: DataSource | null;
	loadingContext: boolean;
	refetchDataSource: (dataSourceId: string) => void;
}

const WizardPageContext = createContext<IWizardPageContext>({
	dataSource: null,
	loadingContext: false,
	refetchDataSource: () => {}
});

async function fetchDataSource({
	dataSourceId,
	groupId,
	setDataSource,
	setLoading
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
			id: dataSourceId
		});

		setDataSource(new DataSource(dataSource));
	} catch (error) {
		addAlert({
			alertType: Alert.Types.Error,
			message: Liferay.Language.get(
				'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists-please-contact-support'
			)
		});
	} finally {
		setLoading(false);
	}
}

export const useWizardPage = () => useContext(WizardPageContext);

export const WizardPageProvider = ({children}: {children: React.ReactNode}) => {
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
				setLoading
			});
		}
	}, [groupId, dataSourceId]);

	return (
		<WizardPageContext.Provider
			value={{
				dataSource,
				loadingContext: loading,
				refetchDataSource: dataSourceId => {
					fetchDataSource({
						dataSourceId,
						groupId,
						setDataSource,
						setLoading
					});
				}
			}}
		>
			{children}
		</WizardPageContext.Provider>
	);
};
