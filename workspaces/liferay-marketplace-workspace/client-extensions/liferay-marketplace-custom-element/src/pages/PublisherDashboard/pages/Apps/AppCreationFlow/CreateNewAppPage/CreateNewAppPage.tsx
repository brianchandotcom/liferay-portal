/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Header} from '../../../../../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../../../../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {useAppContext} from '../AppContext/AppManageState';
import {TYPES} from '../AppContext/actionTypes';

import './CreateNewAppPage.scss';
import LicenseAgreement from '../../../../../AdministratorDashboard/components/LicenseAgreement/LicenseAgreement';

type CreateNewAppPageProps = {
	catalogId: string;
	onClickContinue: () => void;
};

export function CreateNewAppPage({
	catalogId,
	onClickContinue,
}: CreateNewAppPageProps) {
	const [_, dispatch] = useAppContext();

	return (
		<div className="create-new-app-container">
			<div className="create-new-app-header">
				<Header
					description="Review and accept the legal agreement between you and Liferay before proceeding, You are about to create a new app submission."
					title="Create new app"
				/>
			</div>

			<LicenseAgreement />

			<NewAppPageFooterButtons
				onClickContinue={() => {
					dispatch({
						payload: {
							value: catalogId,
						},
						type: TYPES.UPDATE_CATALOG_ID,
					});

					onClickContinue();
				}}
				showBackButton={false}
			/>
		</div>
	);
}
