/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import i18n from '../../../../i18n';
import fetcher from '../../../../services/fetcher';
import PublisherRequestTable from './PublisherRequestTable';
import Page from '../../../../components/Page';

const PublisherRequest = () => {
	const {
		error,
		data: publisherRequests,
		isLoading,
		mutate,
	} = useSWR<APIResponse<PublisherRequestInfo>>(
		'requestpublisheraccounts',
		() => fetcher('o/c/requestpublisheraccounts?sort=dateCreated:desc')
	);

	return (
		<Page
			description={i18n.translate('users-requests-to-become-a-publisher')}
			title={i18n.translate('publisher-requests')}
			pageRendererProps={{isLoading, error}}
		>
			<PublisherRequestTable
				items={publisherRequests?.items || []}
				mutate={mutate}
			/>
		</Page>
	);
};

export default PublisherRequest;
