import useSWR from 'swr';
import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import PublisherRequestTable from './PublisherRequestTable';
import fetcher from '../../../services/fetcher';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import i18n from '../../../i18n';

const PublisherRequest = () => {
	const {data: publisherRequests, mutate} = useSWR<
		APIResponse<PublisherRequestInfo>
	>('requestpublisheraccounts', async () => {
		const requestPublisherResponse = await fetcher(
			'o/c/requestpublisheraccounts'
		);

		return requestPublisherResponse;
	});

	if (!publisherRequests) {
		return (
			<ClayLoadingIndicator
				displayType="primary"
				shape="squares"
				size="lg"
			/>
		);
	}

	return (
		<DashboardPage
			messages={{
				description: i18n.translate(
					'users-requests-to-become-a-publisher'
				),
				title: i18n.translate('publisher-requests'),
			}}
		>
			<PublisherRequestTable
				items={publisherRequests?.items}
				mutate={mutate}
			/>
		</DashboardPage>
	);
};

export default PublisherRequest;
