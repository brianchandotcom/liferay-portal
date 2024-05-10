/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Jethr0FieldLabel from '../Jethr0FieldLabel/Jethr0FieldLabel';

function Jethr0JobFieldLabel({fromRoutine, labelKey, labelName, routine}) {
	return (
		<>
			<Jethr0FieldLabel labelKey={labelKey} labelName={labelName} />

			{fromRoutine && routine && (
				<div>
					{'routine: '}
					<a href={'/#/routines/' + routine.id}>{routine.name}</a>
				</div>
			)}
		</>
	);
}

export default Jethr0JobFieldLabel;
