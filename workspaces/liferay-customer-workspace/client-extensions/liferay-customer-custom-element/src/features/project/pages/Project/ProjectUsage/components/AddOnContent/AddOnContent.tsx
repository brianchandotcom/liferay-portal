/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import PlusSquaresIcons from '~/assets/plus-squares-icon';

import './AddOnContent.css';

import i18n from '~/utils/I18n';

interface IProps {
	title: string;
}

const AddOnContent: React.FC<IProps> = ({title}) => (
	<div className="add-on-content align-items-center d-flex">
		<div className="align-items-center d-flex icon-background justify-content-center mr-4 p-2 rounded-circle">
			<PlusSquaresIcons />
		</div>

		<div>
			<h5 className="add-on-title">{title}</h5>

			<h4 className="add-on-description m-0">
				{i18n.translate('not-purchased')}
			</h4>
		</div>
	</div>
);

export default AddOnContent;
