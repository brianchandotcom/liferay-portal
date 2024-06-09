/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {memo} from 'react';

import useMarketo, {useMarketoProps} from '../../hooks/useMarketoForm';
import Loading from '../Loading';

import './index.scss';

type MarketoFormProps = {
	formId: string;
	hidden?: boolean;
} & useMarketoProps;

const MarketoForm = memo(({formId, hidden, ...hookProps}: MarketoFormProps) => {
	const {started} = useMarketo({
		formId,
		...hookProps,
	});

	return (
		<>
			{!started && <Loading />}

			<form
				aria-hidden="true"
				className={hidden ? 'hidden' : 'block'}
				id={`mktoForm_${formId}`}
			/>
		</>
	);
});

export default MarketoForm;
