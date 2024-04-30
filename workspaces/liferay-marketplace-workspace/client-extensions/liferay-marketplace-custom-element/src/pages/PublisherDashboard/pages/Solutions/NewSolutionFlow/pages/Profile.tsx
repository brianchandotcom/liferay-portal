/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiSelect from '@clayui/multi-select';
import {filesize} from 'filesize';
import {useState} from 'react';
import ReactDOMServer from 'react-dom/server';

import {UploadedFile} from '../../../../../../components/FileList/FileList';
import Form from '../../../../../../components/MarketplaceForm';
import UploadLogo from '../../../../../../components/UploadLogo/UploadLogo';
import {
	SolutionTypes,
	useSolutionContext,
} from '../../../../../../context/SolutionContext';
import {ProductVocabulary} from '../../../../../../enums/ProductVocabulary';
import {useGetVocabulariesAndCategories} from '../../../../../../hooks/data/useGetVocabulariesAndCategories';
import i18n from '../../../../../../i18n';
import {getIconSpriteMap} from '../../../../../../liferay/constants';

const tooltipInfo = {
	categories:
		'Choose the Marketplace category that most accurately describes what your solution does. Users looking for specific types of solutions will often browse categories by searching on a specific category name in the main Marketplace home page. Having your solution listed under the appropriate category will help them find your solution.',
	description:
		'You can put anything you want here, but a good guideline is no more than 4-5 paragraphs. This field does not allow any markup tags - it’s just text. Please do not use misleading names, information, or icons. Descriptions should be as concise as possible. Ensure your icons, images, descriptions, and tags are free of profanity or other offensive material.',
	name: 'name',
	tags:
		'Tags help to describe your solution in the Marketplace. Select the tags most relevant to your solution. They can be changed if needed.',
};

const Profile = () => {
	const [
		{
			profile: {categories, file, tags},
		},
		dispatch,
	] = useSolutionContext();

	const {data = {}, isLoading} = useGetVocabulariesAndCategories([
		ProductVocabulary.SOLUTION_CATEGORY,
		ProductVocabulary.SOLUTION_TAGS,
	]);

	const defaultSourceItems = {
		categories: data[ProductVocabulary.SOLUTION_CATEGORY]?.categories ?? [],
		tags: data[ProductVocabulary.SOLUTION_TAGS]?.categories ?? [],
	};

	const [multiSelectText, setMultiSelectText] = useState({
		categories: '',
		tags: '',
	});

	const onChange = (event: any) => {
		dispatch({
			payload: {[event.target.name]: event.target.value},
			type: SolutionTypes.SET_PROFILE,
		});
	};

	const onChangeMultiSelect = (event: any) => {
		setMultiSelectText((prevState) => ({
			...prevState,
			[event.target.name]: event.target.value,
		}));
	};

	const handleLogoUpload = (files: FileList) => {
		const file = files[0];

		const newUploadedFile: UploadedFile = {
			changed: false,
			error: false,
			file,
			fileName: file.name,
			id: crypto.randomUUID(),
			preview: URL.createObjectURL(file),
			progress: 0,
			readableSize: filesize(file.size),
			uploaded: true,
		};

		dispatch({
			payload: {
				file: newUploadedFile,
			},
			type: SolutionTypes.SET_PROFILE,
		});
	};

	const getFilteredItems = (
		selectedItems: {[key: string]: string}[],
		defaultItems: {[key: string]: string}[]
	) => {
		if (selectedItems) {
			return defaultItems?.filter(
				(defaultCategory) =>
					!selectedItems?.some(
						(category) => defaultCategory.value === category.value
					)
			);
		}

		return defaultItems;
	};

	return (
		<div className="mb-4 solutions-form-profile">
			<h3>{i18n.translate('solutions-info')}</h3>
			<hr />

			<div className="align-items-center d-flex mt-5">
				<UploadLogo
					onDeleteFile={() =>
						dispatch({
							payload: {
								file: undefined,
							},
							type: SolutionTypes.SET_PROFILE,
						})
					}
					onUpload={handleLogoUpload}
					tooltip={ReactDOMServer.renderToString(
						<span>
							The icon is a small image representation of the app.
							Icons must be a PNG, JPG, or GIF format and cannot
							exceed 5MB. Animated images are prohibited. The use
							of the Liferay logo, including any permitted
							alternate versions of the Liferay logo, is permitted
							only with Liferay&apos;s express permission. Please
							refer to our{' '}
							<a
								href="https://www.liferay.com/trademark"
								target="_blank"
							>
								trademark policy
							</a>{' '}
							for details.
						</span>
					)}
					uploadedFile={file}
				/>
			</div>

			<Form.Label
				className="mt-5"
				htmlFor="name"
				info={tooltipInfo.name}
				required
			>
				{i18n.translate('name')}
			</Form.Label>

			<Form.Input
				name="name"
				onChange={onChange}
				placeholder="Enter solution name"
				type="text"
			/>

			<Form.Label
				className="mt-5"
				htmlFor="description"
				info={tooltipInfo.description}
				required
			>
				{i18n.translate('description')}
			</Form.Label>

			<Form.Input
				component="textarea"
				name="description"
				onChange={onChange}
				placeholder="Enter solution description"
				type="textarea"
			/>

			{!isLoading && (
				<div className="form-multiselect">
					<Form.Label
						className="mt-5"
						htmlFor="categories"
						info={tooltipInfo.categories}
						required
					>
						{i18n.translate('categories')}
					</Form.Label>

					<ClayMultiSelect
						{...{placeholder: 'Select categories'}}
						inputName="description-selector"
						items={categories}
						key={'cat-' + categories.length}
						onChange={(value: string) =>
							onChangeMultiSelect({
								target: {
									name: 'categories',
									value,
								},
							})
						}
						onItemsChange={(value: {[key: string]: string}[]) =>
							onChange({
								target: {name: 'categories', value},
							})
						}
						sourceItems={getFilteredItems(
							categories,
							defaultSourceItems?.categories
						)}
						spritemap={getIconSpriteMap()}
						value={multiSelectText?.categories}
					/>

					<Form.Label
						className="mt-5"
						htmlFor="tags"
						info={tooltipInfo.tags}
						required
					>
						{i18n.translate('tags')}
					</Form.Label>

					<ClayMultiSelect
						{...{placeholder: 'Select tags'}}
						inputName="tags-selector"
						items={tags}
						key={'tags-' + tags.length}
						onChange={(value: string) =>
							onChangeMultiSelect({
								target: {
									name: 'tags',
									value,
								},
							})
						}
						onItemsChange={(value: {[key: string]: string}[]) =>
							onChange({
								target: {name: 'tags', value},
							})
						}
						sourceItems={getFilteredItems(
							tags,
							defaultSourceItems?.tags
						)}
						spritemap={getIconSpriteMap()}
						value={multiSelectText?.tags}
					/>
				</div>
			)}
		</div>
	);
};

export default Profile;
