/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Link} from 'react-router-dom';
import {States} from '~/components/EmptyState';
import {useHeader} from '~/hooks';
import i18n from '~/i18n';

const Page404 = () => {
	useHeader({
		dropdown: [],
		headerActions: {actions: []},
		heading: [{title: ''}],
		tabs: [],
		timeout: 100,
	});

	return (
		<div className="align-items-center d-flex flex-column">
			<img draggable={false} src={States.NOT_FOUND} width="50%" />

			<div className="align-items-center d-flex flex-column">
				<h1>{i18n.translate('sorry-this-page-does-not-exist')}</h1>

				<Link to="/">{i18n.translate('go-to-homepage')}</Link>
			</div>
		</div>
	);
};

export default Page404;
