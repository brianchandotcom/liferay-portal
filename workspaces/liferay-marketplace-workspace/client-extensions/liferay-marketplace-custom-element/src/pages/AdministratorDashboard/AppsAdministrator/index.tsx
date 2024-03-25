import useSWR from 'swr';
import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import fetcher from '../../../services/fetcher';
import AppAdministratorTable from './AppAdministratorTable';
import i18n from '../../../i18n';

const AppAdministrator = () => {
	const {data: apps} = useSWR<APIResponse<PublisherRequestInfo>>(
		'appsAdministrator',
		async () => {
			const getAppsResponse = await fetcher(
				'o/headless-commerce-admin-catalog/v1.0/products?nestedFields=productSpecifications&sort=createDate:desc'
			);

			return getAppsResponse;
		}
	);

	return (
		<DashboardPage
			messages={{
				description: i18n.translate('all-published-apps'),
				title: i18n.translate('published-apps'),
			}}
		>
			<AppAdministratorTable items={apps?.items || []} />
		</DashboardPage>
	);
};

export default AppAdministrator;
