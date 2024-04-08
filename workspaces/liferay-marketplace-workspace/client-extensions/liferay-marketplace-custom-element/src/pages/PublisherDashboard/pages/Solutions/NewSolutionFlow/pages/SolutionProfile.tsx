/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';

import './index.scss';

import {useState} from 'react';

import Form from '../../../../../../components/MarketplaceForm';
import i18n from '../../../../../../i18n';
import {getIconSpriteMap} from '../../../../../../liferay/constants';

const SolutionProfile = () => {
	const [value, setValue] = useState('');
	const [items, setItems] = useState([
		{
			label: 'Category',
			value: 'Category',
		},
	]);

	const sourceItems = [
		{
			label: 'Category',
			value: 'Category',
		},
		{
			label: 'Tag',
			value: 'Tag',
		},
	];

	return (
		<div className="mb-4 solution-profile-container">
			<h3>{i18n.translate('solutions-info')}</h3>
			<hr />

			<div className="align-items-center d-flex mt-5">
				<div className="align-items-center d-flex justify-content-center mr-4 upload-icon-background">
					<ClayIcon symbol="picture" />
				</div>

				<input
					accept="image/jpeg, image/png, image/gif"
					id="file"
					name="file"
					type="file"
				/>

				<label className="btn btn-primary btn-sm" htmlFor="file">
					{i18n.translate('upload-image')}
				</label>
			</div>

			<Form.Label className="mt-5" htmlFor="name" required>
				{i18n.translate('name')}
			</Form.Label>
			<Form.Input
				name="name"
				placeholder="Enter solution name"
				type="text"
			/>

			<Form.Label className="mt-5" htmlFor="description" required>
				{i18n.translate('description')}
			</Form.Label>
			<Form.Input
				component="textarea"
				name="description"
				placeholder="Enter solution description"
				type="textarea"
			/>

			<Form.Label className="mt-5" htmlFor="categories" required>
				{i18n.translate('categories')}
			</Form.Label>

			<ClayMultiSelect
				inputName="description-selector"
				items={items}
				onChange={setValue}
				onItemsChange={setItems}
				placeholder="Select categories"
				sourceItems={sourceItems}
				spritemap={getIconSpriteMap()}
				value={value}
			/>

			<Form.Label className="mt-5" htmlFor="tags" required>
				{i18n.translate('tags')}
			</Form.Label>

			<ClayMultiSelect
				inputName="tags-selector"
				items={items}
				onChange={setValue}
				onItemsChange={setItems}
				placeholder="Select tags"
				sourceItems={sourceItems}
				spritemap={getIconSpriteMap()}
				value={value}
			/>
		</div>
	);
};

export default SolutionProfile;
