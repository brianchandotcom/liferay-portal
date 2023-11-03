/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayNavigationBar from '@clayui/navigation-bar';
import {useEffect} from 'react';
import {useOutletContext} from 'react-router-dom';

import {useAppContext} from '../../../../manage-app-state/AppManageState';
import {TYPES} from '../../../../manage-app-state/actionTypes';
import {ReviewAndSubmitAppPage} from '../../../ReviewAndSubmitAppPage/ReviewAndSubmitAppPage';

import './App.scss';

const App = () => {
	const [, dispatch] = useAppContext();
	const {selectedApp} = useOutletContext<any>();

	useEffect(() => {
		if (!selectedApp) {
			return;
		}

		dispatch({
			payload: {
				value: {
					appERC: selectedApp.externalReferenceCode,
					appProductId: selectedApp.productId,
				},
			},
			type: TYPES.SUBMIT_APP_PROFILE,
		});
	}, [
		dispatch,
		selectedApp,
		selectedApp?.externalReferenceCode,
		selectedApp?.productId,
	]);

	if (!selectedApp) {
		return null;
	}

	const status = selectedApp.workflowStatusInfo.label.replace(
		/(^\w|\s\w)/g,
		(m: string) => m.toUpperCase()
	);

	return (
		<div className="w-100">
			{status === 'Draft' && (
				<ClayAlert
					className="app-details-page-alert-container"
					displayType="info"
				>
					<span className="app-details-page-alert-text">
						This submission is currently under review by Liferay.
						Once the process is complete, you will be able to
						publish it to the marketplace. Meanwhile, any
						information or data from this app submission cannot be
						updated.
					</span>
				</ClayAlert>
			)}

			<div>
				<ClayNavigationBar
					className="app-details-page-navigation-bar"
					triggerLabel="App Detatils"
				>
					<ClayNavigationBar.Item active>
						<ClayButton>App Details</ClayButton>
					</ClayNavigationBar.Item>
				</ClayNavigationBar>

				<ReviewAndSubmitAppPage
					onClickBack={() => {}}
					onClickContinue={() => {}}
					productERC={selectedApp.externalReferenceCode}
					productId={selectedApp.productId}
					readonly
				/>
			</div>
		</div>
	);
};

export default App;
