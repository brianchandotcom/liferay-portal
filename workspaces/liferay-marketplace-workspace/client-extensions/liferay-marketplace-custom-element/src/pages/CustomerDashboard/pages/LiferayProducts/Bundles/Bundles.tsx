/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-bundles-Identifier: LGPL-2.1-or-later OR bundlesRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Bundles.scss';

import useSWR from 'swr';

import fetcher from '../../../../../services/fetcher';
import DownloadTable from '../../Apps/App/Download/DownloadTable';

type LiferayBundle = {
	bundleLink: string;
	bundleName: string;
};

const LiferayProductsBundles = () => {
	const {data, isLoading} = useSWR<APIResponse<LiferayBundle>>(
		`liferay-free-tier-bundles`,
		() => fetcher('o/c/liferaybundles?sort=bundleName:desc')
	);

	const liferayBundles = data?.items || [];

	const virtualItems = liferayBundles.map(
		(liferayBundle): VirtualItem => ({
			url: liferayBundle.bundleLink,
			usages: 0,
			version: liferayBundle.bundleName,
		})
	);

	return (
		<div className="bundles mb-9 mt-4">
			<DownloadTable
				loading={isLoading}
				title="bundle-name"
				virtualItems={virtualItems}
			/>
		</div>
	);
};

export default LiferayProductsBundles;
