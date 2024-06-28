/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import DOMPurify from 'isomorphic-dompurify';
import useSWR from 'swr';

import withProviders from '../../hoc/withProviders';
import i18n from '../../i18n';
import {getEulaDescription} from '../../utils/util';

export function LicenseAgreementPage() {
	const {data: eula = ''} = useSWR('/eula', getEulaDescription);

	return (
		<body>
			<header className="d-flex justify-content-center">
				<h2>{i18n.translate('end-user-license-agreement')}</h2>
			</header>

			<hr />

			<main>
				<div
					dangerouslySetInnerHTML={{
						__html: DOMPurify.sanitize(eula),
					}}
				/>
			</main>
		</body>
	);
}

export default withProviders(LicenseAgreementPage);
