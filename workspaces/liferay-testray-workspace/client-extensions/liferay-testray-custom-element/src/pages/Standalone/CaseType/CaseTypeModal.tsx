/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ListView from '../../../components/ListView';
import i18n from '../../../i18n';
import CaseTypeFormModal from './CaseTypeFormModal';
import useCaseTypeActions from './useCaseTypeActions';

const CaseTypeModal = () => {
	const {actions, formModal} = useCaseTypeActions();

	return (
		<>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
				}}
				resource="/casetypes"
				tableProps={{
					actions,
					columns: [
						{
							key: 'name',
							sorteable: true,
							value: i18n.translate('name'),
						},
					],
				}}
			/>

			<CaseTypeFormModal modal={formModal.modal} />
		</>
	);
};

export default CaseTypeModal;
