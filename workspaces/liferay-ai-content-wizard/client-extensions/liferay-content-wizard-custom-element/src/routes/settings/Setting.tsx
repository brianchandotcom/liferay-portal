/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import {maskApiKey, providerStatuses} from '.';
import Container from '../../components/Container';
import QATable from '../../components/QATable';

export default function Setting() {
	const [hidden, setHidden] = useState(true);
	const navigate = useNavigate();
	const outletContext = useOutletContext<{setting: any}>();

	const {setting = {}} = outletContext || {};

	return (
		<Container>
			<h1>Setting</h1>

			<QATable
				className="col-6"
				items={[
					{
						title: 'Active',
						value: (
							<>
								{' '}
								<ClayIcon
									{...(providerStatuses as any)[
										setting.active
									]}
								/>{' '}
								{String(setting.active)}
							</>
						),
					},
					{
						title: 'API Key',
						value: (
							<span className="align-items-center d-flex">
								{hidden
									? maskApiKey(setting.apiKey)
									: setting.apiKey}
								<ClayIcon
									className="cursor-pointer ml-2"
									fontSize={18}
									onClick={() => setHidden(!hidden)}
									symbol={hidden ? 'view' : 'hidden'}
								/>
							</span>
						),
					},
					{title: 'Provider', value: setting.provider.name},
					{title: 'Model', value: setting.model.name},
					{title: 'Model Image', value: setting.imageModel.name},
					{title: 'Description', value: setting.description},
				]}
				orientation="HORIZONTAL"
			/>

			<Button
				className="mt-4"
				displayType="secondary"
				onClick={() => navigate('/')}
			>
				Back
			</Button>
		</Container>
	);
}
