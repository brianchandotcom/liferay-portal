/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ListView from '../../../components/ListView';
import FactorCategoryFormModal from './FactorCategoryFormModal';
import useFactorCategoryActions from './useFactorCategoryActions';

const FactorCategoryModal = () => {
	const {actions, formModal} = useFactorCategoryActions();

	return (
		<>
			<ListView
				forceRefetch={formModal.forceRefetch}
				managementToolbarProps={{
					addButton: () => formModal.modal.open(),
				}}
				resource="/factorcategories?fields=actions,id,name"
				tableProps={{
					actions,
					columns: [
						{
							key: 'name',
							sorteable: true,
							value: 'Name',
						},
					],
				}}
			/>

			<FactorCategoryFormModal modal={formModal.modal} />
		</>
	);
};

export default FactorCategoryModal;
