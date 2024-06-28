/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DOMPurify from 'isomorphic-dompurify';

import withProviders from '../../../hoc/withProviders';
import useSWR from 'swr';
import {getEulaDescription} from '../../../utils/util';
import i18n from '../../../i18n';

export function LicenseAgreement() {
	const {data: eula = ''} = useSWR('/eula', getEulaDescription);

	return (
		<div>
			<header className="d-flex justify-content-center">
				<h2>{i18n.translate('end-user-license-agreement')}</h2>
			</header>

			<hr />

			<body>
				<div
					dangerouslySetInnerHTML={{
						__html: DOMPurify.sanitize(eula),
					}}
				/>
			</body>
		</div>
	);
}

export default withProviders(LicenseAgreement);
