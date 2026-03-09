/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-bundles-Identifier: LGPL-2.1-or-later OR bundlesRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Download from '../../Apps/App/Download/Download';

import './Bundles.scss';

const LiferayServiceBundles = () => {
	return (
		<div className="bundles mb-9 mt-4">
			<Download title="bundle-name" showSearchBar={false} />
		</div>
	);
};

export default LiferayServiceBundles;
