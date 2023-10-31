/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayNavigationBar from '@clayui/navigation-bar';
import {useState} from 'react';
import {Outlet, useNavigate, useParams} from 'react-router-dom';

import {AppTabType} from './enums/AppTabType';

const AppOutlet = () => {
	const navigate = useNavigate();

	const [active, setActive] = useState('');

	const {appId: productId} = useParams();

	return (
		<div>
			<ClayButton
				className="mb-2"
				displayType="unstyled"
				onClick={() => navigate('/')}
			>
				<ClayIcon className="mr-2" symbol="order-arrow-left" />
				Back to My Apps
			</ClayButton>
			<ClayNavigationBar className="mb-4" triggerLabel={active}>
				<ClayNavigationBar.Item active={active === AppTabType.DETAILS}>
					<ClayButton
						onClick={() => {
							navigate(`/app/${productId}`);
							setActive(AppTabType.DETAILS);
						}}
					>
						Details
					</ClayButton>
				</ClayNavigationBar.Item>
				<ClayNavigationBar.Item active={active === AppTabType.LICENSES}>
					<ClayButton
						onClick={() => {
							navigate(`app/${productId}/licenses`);
							setActive(AppTabType.LICENSES);
						}}
					>
						Licenses
					</ClayButton>
				</ClayNavigationBar.Item>
			</ClayNavigationBar>
			<Outlet />
		</div>
	);
};

export default AppOutlet;
