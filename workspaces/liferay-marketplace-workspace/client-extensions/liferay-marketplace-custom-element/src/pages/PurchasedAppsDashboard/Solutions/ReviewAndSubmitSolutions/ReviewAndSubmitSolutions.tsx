/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useState} from 'react';

import {Section} from '../../../../components/Section/Section';
import {getProduct} from '../../../../utils/api';
import {
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../../utils/util';
import {CardSectionsBody} from '../../../ReviewAndSubmitAppPage/CardSectionsBody';
import {App} from '../../../ReviewAndSubmitAppPage/ReviewAndSubmitAppPageUtil';

type ReviewAndSubmitSolutions = {
	productERC?: string;
	productId?: number;
	readonly?: boolean;
};

export function ReviewAndSubmitSolutions({
	productERC,
	productId,
	readonly = true,
}: ReviewAndSubmitSolutions) {
	const [app, setApp] = useState<any>();
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const getData = async () => {
			setLoading(true);

			const productResponse = await getProduct({
				appERC: productERC,
				nestedFields: 'attachments,images,skus,productSpecifications',
			});

			const productCategories: string[] = [];
			const productTags: string[] = [];

			productResponse?.categories?.forEach((category: any) => {
				if (category.vocabulary === 'marketplace solution category') {
					productCategories.push(category.name);
				} else if (
					category.vocabulary === 'marketplace solution tags'
				) {
					productTags.push(category.name);
				}
			});

			const attachment = productResponse.attachments.find(
				(attachment) => {
					return (attachment.tags || []).indexOf('app icon') < 0;
				}
			);

			const thumbnail = showAppImage(
				getThumbnailByProductAttachment(productResponse.images)
			);

			const newApp = {
				attachmentTitle: attachment?.title['en_US'] as string,
				categories: productCategories,
				description: productResponse.description['en_US'],
				name: productResponse.name['en_US'],
				storefront: (productResponse.images || []).filter((image) => {
					return image.galleryEnabled;
				}),
				tags: productTags,
				thumbnail,
			};

			setApp(newApp);

			setLoading(false);
		};

		getData();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [productERC, productId]);

	return (
		<div className="review-and-submit-app-page-container">
			<Section
				disabled={readonly}
				label={readonly ? '' : 'App Submission'}
				tooltip={readonly ? '' : 'More info'}
				tooltipText={readonly ? '' : 'More Info'}
			>
				<div className="review-and-submit-app-page-card-container">
					<div className="review-and-submit-app-page-card-body">
						{loading ? (
							<ClayLoadingIndicator
								displayType="primary"
								shape="circle"
								size="md"
							/>
						) : (
							<CardSectionsBody
								app={app as App}
								isApp={false}
								readonly={readonly}
							/>
						)}
					</div>
				</div>
			</Section>
		</div>
	);
}
