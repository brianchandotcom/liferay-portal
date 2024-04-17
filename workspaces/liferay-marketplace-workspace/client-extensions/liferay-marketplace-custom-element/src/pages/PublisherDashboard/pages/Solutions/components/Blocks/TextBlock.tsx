/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactQuill from 'react-quill';

import Form from '../../../../../../components/MarketplaceForm';
import i18n from '../../../../../../i18n';

const TextBlock = () => (
	<div className="p-4">
		<Form.Label className="mt-2" htmlFor="title" required>
			Title
		</Form.Label>

		<Form.Input name="title" placeholder="Enter title header" type="text" />

		<Form.Label className="mt-5" htmlFor="description" required>
			{i18n.translate('description')}
		</Form.Label>

		<div className="rich-text-editor">
			<ReactQuill placeholder="Insert text here" />
		</div>
	</div>
);

export default TextBlock;
