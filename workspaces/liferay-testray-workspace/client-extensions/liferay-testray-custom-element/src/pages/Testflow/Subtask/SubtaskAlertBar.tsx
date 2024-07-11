/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Link} from 'react-router-dom';
import i18n from '~/i18n';
import {TestraySubtask} from '~/services/rest';

type SubtaskAlertBarProps = {
	testraySubtask: TestraySubtask;
};

const SubtaskAlertBar: React.FC<SubtaskAlertBarProps> = ({testraySubtask}) => {
	return (
		<ClayAlert
			className="build-alert-bar w-100"
			title={i18n.sub(
				'this-subtask-has-been-merged-wihth-x',
				testraySubtask?.mergedToSubtask?.name
			)}
			variant="inline"
		>
			<Link
				className="font-weight-bold"
				to={`../subtasks/${testraySubtask?.mergedToSubtask?.id}`}
			>
				{i18n.sub('view-x', testraySubtask?.mergedToSubtask?.name)}
			</Link>
		</ClayAlert>
	);
};

export default SubtaskAlertBar;
