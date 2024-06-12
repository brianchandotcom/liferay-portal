/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import Page from '../../../../components/Page';
import i18n from '../../../../i18n';
import fetcher from '../../../../services/fetcher';
import PublisherRequestTable from './PublisherRequestTable';

const PublisherRequest = () => {
	const {
		data: publisherRequests,
		error,
		isLoading,
		mutate,
	} = useSWR<APIResponse<PublisherRequestInfo>>(
		'requestpublisheraccounts',
		() => fetcher('o/c/requestpublisheraccounts?sort=dateCreated:desc')
	);

	return (
		<Page
			description={i18n.translate('users-requests-to-become-a-publisher')}
			pageRendererProps={{error, isLoading}}
			title={i18n.translate('publisher-requests')}
		>
			<PublisherRequestTable
				items={publisherRequests?.items || []}
				mutate={mutate}
			/>
		</Page>
	);
};

export default PublisherRequest;
