/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import useSWR from 'swr';

import HeadlessCommerceAdminCatalogImpl from '../../../../../services/rest/HeadlessCommerceAdminCatalog';
import {
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../../../utils/util';
import {ReviewAndSubmitSolutions} from './ReviewAndSubmitSolutions';

import './index.scss';
import {PRODUCT_CATEGORIES} from '../../../../../enums/Product';
import {getProductCategoriesByVocabularyName} from '../../../../../utils/productUtils';
import SolutionsDetailsHeader from './SolutionDetailsHeader/SolutionDetailsHeader';

export type Solution = {
	attachmentTitle: string;
	categories: string[];
	description: string;
	name: string;
	storefront: ProductImages[];
	tags: string[];
	thumbnail: string;
};

const NAVIGATION_BAR_OPTIONS = {
	DETAILS: 'Details',
};

const SolutionsDetails = () => {
	const [active, setActive] = useState(NAVIGATION_BAR_OPTIONS.DETAILS);
	const [solution, setSolution] = useState<Solution>();

	const {appId} = useParams();

	const productId = Number(appId) + 1;

	const {data: selectedSolution, isLoading} = useSWR(
		`/published-app/${productId}`,
		() =>
			HeadlessCommerceAdminCatalogImpl.getProduct(
				productId,
				new URLSearchParams({
					nestedFields: 'attachments,images,productSpecifications',
				})
			)
	);

	useEffect(() => {
		if (!selectedSolution) {
			return;
		}

		const {attachments, categories, description, images} = selectedSolution;

		const getData = async () => {
			const solutionCategories = getProductCategoriesByVocabularyName(
				categories,
				PRODUCT_CATEGORIES.MARKETPLACE_SOLUTION_CATEGORY
			);

			const solutionTags = getProductCategoriesByVocabularyName(
				categories,
				PRODUCT_CATEGORIES.MARKETPLACE_SOLUTION_TAGS
			);

			const attachment = attachments?.find(
				(attachment: ProductAttachment) => {
					return (attachment.tags || []).indexOf('app icon') < 0;
				}
			);

			const thumbnail = showAppImage(
				getThumbnailByProductAttachment(images)
			);

			const newSolution = {
				attachmentTitle: attachment?.title['en_US'] as string,
				categories: solutionCategories,
				description: description['en_US'],
				name: selectedSolution.name['en_US'],
				storefront: (selectedSolution.images || []).filter(
					(image: ProductAttachment) => {
						return image.galleryEnabled;
					}
				),
				tags: solutionTags,
				thumbnail,
			};

			setSolution(newSolution);
		};

		getData();
	}, [selectedSolution]);

	if (!selectedSolution || isLoading) {
		return null;
	}

	return (
		<div className="solutions-details-page-container w-100">
			<div className="mb-5 solutions-details-header">
				<SolutionsDetailsHeader selectedSolution={selectedSolution} />

				<ClayNavigationBar className="w-100" triggerLabel={active}>
					<ClayNavigationBar.Item
						active={active === NAVIGATION_BAR_OPTIONS.DETAILS}
					>
						<ClayLink
							onClick={() => {
								setActive(NAVIGATION_BAR_OPTIONS.DETAILS);
							}}
						>
							Details
						</ClayLink>
					</ClayNavigationBar.Item>
				</ClayNavigationBar>
			</div>

			<div className="solution-details-page-content">
				{active === NAVIGATION_BAR_OPTIONS.DETAILS && (
					<ReviewAndSubmitSolutions
						loading={isLoading}
						solution={solution}
					/>
				)}
			</div>
		</div>
	);
};

export default SolutionsDetails;
