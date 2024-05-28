/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form from '../../../../../../components/MarketplaceForm';
import {TextBlock as TextBlockType} from '../../../../../../context/SolutionContext';
import i18n from '../../../../../../i18n';
import {BlockTypeProps} from './BlockPropsType';

const TextBlock: React.FC<BlockTypeProps<TextBlockType>> = ({
	block: {
		content: {description = '', title = ''},
	},
	onChange,
}) => (
	<div className="p-4">
		<Form.FormControl>
			<Form.Label className="mt-2" htmlFor="title" required>
				Title
			</Form.Label>

			<Form.Input
				maxLength={170}
				name="title"
				onChange={(event) => onChange({title: event.target.value})}
				placeholder="Enter title"
				type="text"
				value={title}
			/>
		</Form.FormControl>

		<Form.FormControl>
			<Form.Label className="mt-5" htmlFor="description" required>
				{i18n.translate('description')}
			</Form.Label>

			<div className="rich-text-editor">
				<Form.RichTextEditor
					maxLength={700}
					onChange={(description) => onChange({description})}
					placeholder="Insert text here"
					value={description}
				/>
			</div>
		</Form.FormControl>
	</div>
);

export default TextBlock;
