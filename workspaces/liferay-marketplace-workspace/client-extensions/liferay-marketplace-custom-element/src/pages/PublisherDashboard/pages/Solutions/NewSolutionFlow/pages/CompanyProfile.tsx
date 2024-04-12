/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import ReactQuill from 'react-quill';

import Form from '../../../../../../components/MarketplaceForm';
import i18n from '../../../../../../i18n';

const CompanyProfile = () => {
	const [editorValue, setEditorValue] = useState('');

	return (
		<div className="mb-4 solutions-form-company-profile">
			<h3>{i18n.translate('company-profile')}</h3>

			<hr />

			<Form.Label className="mt-3" htmlFor="description">
				{i18n.translate('description')}
			</Form.Label>

			<div className="rich-text-editor">
				<ReactQuill
					onChange={setEditorValue}
					placeholder="Insert text here"
					value={editorValue}
				/>
			</div>

			<Form.Label className="mt-5" htmlFor="website">
				Website
			</Form.Label>

			<Form.Input
				name="website"
				placeholder="http://www.yourdomain.com"
				type="text"
			/>

			<Form.Label className="mt-5" htmlFor="email" required>
				Email
			</Form.Label>

			<Form.Input
				name="email"
				placeholder="name@yourdomain.com"
				type="name@yourdomain.com"
			/>

			<Form.Label className="mt-5" htmlFor="phone" required>
				Phone
			</Form.Label>

			<Form.Input
				name="phone"
				placeholder="+1 (123) 456-7890"
				type="name@yourdomain.com"
			/>
		</div>
	);
};

export default CompanyProfile;
