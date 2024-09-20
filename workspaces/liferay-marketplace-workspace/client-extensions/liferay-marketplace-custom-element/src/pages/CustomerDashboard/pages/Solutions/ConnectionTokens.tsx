/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';

import Table from '../../../../components/Table/Table';
import {getRandomID} from '../../../../utils/string';

const ConnectionTokens = () => (
	<div className="py-4">
		<div className="border col-6 col-lg-6 col-md-12 p-4">
			<div className="align-items-center d-flex justify-content-between">
				<h3>Connect Your Liferay DXP Analytics</h3>

				<div
					className="align-items-center d-flex justify-content-center"
					style={{
						backgroundColor: '#dadada',
						borderRadius: '50%',
						height: 40,
						width: 40,
					}}
				>
					<ClayIcon symbol="diagram" />
				</div>
			</div>

			<div className="mt-2 py-3">
				<label htmlFor="token">
					Copy this token to your Liferay DXP Instance
				</label>

				<ClayInput
					disabled
					id="token"
					name="token"
					readOnly
					value={getRandomID()}
				/>
			</div>

			<a
				href="https://learn.liferay.com/en/w/analytics-cloud/getting-started/connecting-liferay-dxp-to-analytics-cloud"
				rel="noopener norefeerer"
				target="_blank"
			>
				Click here to learn how to connect Liferay DXP to Analytics
				Cloud
			</a>
		</div>

		<div className="border mt-4 p-4">
			<h3 className="mb-4">Data Sources</h3>

			<Table
				columns={[
					{key: 'name', title: 'Name'},
					{key: 'source', title: 'Source'},
					{key: 'dateAdded', title: 'Date Added'},
					{key: 'status', title: 'Status'},
				]}
				rows={[
					{
						dateAdded: '-',
						name: 'Liferay DXP',
						source: 'Liferay Portal',
						status: 'DISCONNECTED',
					},
				]}
			/>
		</div>
	</div>
);

export default ConnectionTokens;
