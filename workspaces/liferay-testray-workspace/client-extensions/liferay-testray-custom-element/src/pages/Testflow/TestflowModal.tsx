/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';

import BreadcrumbSearch from '../../components/BreadcrumbSearch';
import Form from '../../components/Form';
import Modal from '../../components/Modal';
import {FormModalOptions} from '../../hooks/useFormModal';
import i18n from '../../i18n';
import {Liferay} from '../../services/liferay';
import {testrayTaskImpl} from '../../services/rest';

type TestflowModalProps = {
	modal: FormModalOptions;
};

const MAX_ENTITIES_TO_SEARCH = 3;

const TestflowModal: React.FC<TestflowModalProps> = ({
	modal: {observer, onClose, visible},
}) => {
	const navigate = useNavigate();
	const [breadCrumb, setBreadCrumb] = useState([]);

	const [project, routine, build] = breadCrumb as {
		label: string;
		value: number;
	}[];

	const onSubmit = async () => {
		const buildId = build.value;
		const projectId = project.value;
		const routineId = routine.value;

		const taskResponse = await testrayTaskImpl.getTasksByBuildId(buildId);

		if (taskResponse?.totalCount) {
			return Liferay.Util.openToast({
				message: i18n.translate('a-task-for-this-build-already-exists'),
				type: 'danger',
			});
		}

		navigate(
			`/project/${projectId}/routines/${routineId}/build/${buildId}/testflow/create`
		);
	};

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={onSubmit}
					primaryButtonProps={{
						disabled: breadCrumb.length !== MAX_ENTITIES_TO_SEARCH,
						title: i18n.translate('analyze'),
					}}
				/>
			}
			observer={observer}
			size="lg"
			title={i18n.translate('select-build')}
			visible={visible}
		>
			<BreadcrumbSearch
				maxEntitiesToSearch={MAX_ENTITIES_TO_SEARCH}
				onBreadcrumbChange={setBreadCrumb}
			/>
		</Modal>
	);
};

export default TestflowModal;
