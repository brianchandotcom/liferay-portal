/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import DOMPurify from 'dompurify';
import {useNavigate} from 'react-router-dom';

import {Section} from '../../../../../../components/Section/Section';
import {Tag} from '../../../../../../components/Tag/Tag';
import VideoThumbnail from '../../../../../../components/VideoThumbnail';
import {useNewAppContext} from '../../../../../../context/NewAppContext';
import i18n from '../../../../../../i18n';
import {PRICING_OPTIONS} from '../../constants';
import SubmitLicensingList from './SubmitLicensingList';
import SubmitSection from './SubmitSection';
import SubmitSupportList from './SubmitSupportList';

import './Submit.scss';

type PriceOptionsType = {
	description: string;
	icon?: string;
	title: string;
	tooltip: string;
};

const Submit = () => {
	const [context] = useNewAppContext();
	const navigate = useNavigate();
	const pricingOption = PRICING_OPTIONS.find(
		(pricingOption) => pricingOption.title === context.pricing.priceModel
	) as PriceOptionsType;

	return (
		<Section
			disabled
			label={i18n.translate('app-submission')}
			required
			tooltip={i18n.translate('more-info')}
			tooltipText={i18n.translate('more-info')}
		>
			<hr />

			<div className="border p-5 rounded-lg">
				<div className="align-items-center d-flex">
					{context.profile.file.preview ? (
						<img
							alt="App logo"
							className="submit-app-logo-icon"
							src={context.profile.file.preview}
						/>
					) : (
						<ClayIcon
							aria-label="New App logo"
							className="submit-app-logo-icon text-muted"
							symbol="picture"
						/>
					)}

					<div className="d-flex flex-column pl-5">
						<span className="submit-app-name">
							{context.profile.name}
						</span>
						<span className="submit-app-version">
							{context.version.version}
						</span>
					</div>
				</div>

				<hr />

				<SubmitSection
					editNavigate={() => navigate('../profile')}
					required
					title={i18n.translate('description')}
				>
					<div className="submit-app-section-body-description">
						<p
							className="submit-app-section-body-description-paragraph"
							dangerouslySetInnerHTML={{
								__html: DOMPurify.sanitize(
									context.profile.description
								),
							}}
						/>
					</div>
				</SubmitSection>

				<SubmitSection required title={i18n.translate('category')}>
					<div className="submit-app-section-body">
						<Tag label={context.profile.categories.label} />
					</div>
				</SubmitSection>

				<SubmitSection
					editNavigate={() => navigate('../profile')}
					required
					title={i18n.translate('areas')}
				>
					<div className="submit-app-section-body-tags">
						{context.profile.areas.map((area, index) => (
							<Tag key={index} label={area.label} />
						))}
					</div>
				</SubmitSection>

				<SubmitSection
					editNavigate={() => navigate('../profile')}
					required
					title={i18n.translate('tags')}
				>
					<div className="submit-app-section-body-tags">
						{context.profile.tags.map((tag, index) => (
							<Tag key={index} label={tag.label} />
						))}
					</div>
				</SubmitSection>

				<SubmitSection
					editNavigate={() => navigate('../pricing')}
					required
					title={i18n.translate('pricing')}
				>
					<div className="border p-4 rounded-lg">
						<div>
							{pricingOption && (
								<>
									<div className="align-items-center d-flex">
										<span className="mr-2 submit-app-pricing-title">
											{pricingOption?.title}
										</span>{' '}
										{pricingOption?.icon && (
											<ClayIcon
												className="submit-app-pricing-icon"
												symbol={pricingOption?.icon}
											/>
										)}
									</div>

									<span className="submit-app-pricing-description">
										{pricingOption?.description}
									</span>
								</>
							)}
						</div>
					</div>
				</SubmitSection>

				<SubmitSection
					editNavigate={() => navigate('../licensing')}
					required
					title={i18n.translate('licensing')}
				>
					<SubmitLicensingList />
				</SubmitSection>

				<SubmitSection
					editNavigate={() => navigate('../storefront')}
					required
					title={i18n.translate('storefront')}
				>
					<div>
						<div className="submit-app-storefront-container">
							<span className="storefront-section-title">
								Images
							</span>

							{context.storefront.images.map((image, index) => (
								<div className="d-flex mt-3" key={index}>
									<img
										draggable={false}
										src={image.preview}
									/>

									<div className="d-flex flex-column ml-4">
										<ClayIcon
											className="icon-image"
											symbol="document-image"
										/>

										<span className="storefront-url-title">
											{image.fileName}
										</span>

										<span className="storefront-description">
											{image.imageDescription}
										</span>
									</div>
								</div>
							))}
						</div>

						<p className="submit-app-storefront-important-note">
							{i18n.translate(
								'important-images-will-be-displayed-following-the-numerical-order-above'
							)}
						</p>

						{context.storefront.video.videoURL && (
							<div className="mt-5 submit-app-storefront-container">
								<span className="storefront-section-title">
									VIDEO
								</span>
								<div className="d-flex mt-3">
									<VideoThumbnail
										videoURL={
											context.storefront.video.videoURL
										}
									/>

									<div className="d-flex flex-column ml-4">
										<ClayIcon
											className="icon-image"
											symbol="video"
										/>

										<span className="storefront-url-title">
											{context.storefront.video.videoURL}
										</span>

										<span className="storefront-description">
											{
												context.storefront.video
													.description
											}
										</span>
									</div>
								</div>
							</div>
						)}
					</div>
				</SubmitSection>

				<SubmitSection
					editNavigate={() => navigate('../support')}
					isLastSection
					required
					title={i18n.translate('support-and-help')}
				>
					<SubmitSupportList />
				</SubmitSection>
			</div>
		</Section>
	);
};

export default Submit;
