/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useNavigate} from 'react-router-dom';

import {Section} from '../../../../../components/Section/Section';
import {Tag} from '../../../../../components/Tag/Tag';
import VideoThumbnail from '../../../../../components/VideoThumbnail';
import {useNewAppContext} from '../../../../../context/NewAppContext';
import i18n from '../../../../../i18n';
import {currenciesCodeObject} from '../../../../../utils/currencies';
import {LICENSING_OPTIONS, PRICING_OPTIONS} from '../constants';

type PriceOptionsType = {
	description: string;
	icon?: string;
	title: string;
	tooltip: string;
};

const Submit = () => {
	const [appData] = useNewAppContext();
	const navigate = useNavigate();
	const licenseOption = LICENSING_OPTIONS.find(
		(licenseOption) => licenseOption.value === appData.licensing.licenseType
	);
	const pricingOption = PRICING_OPTIONS.find(
		(pricingOption) => pricingOption.title === appData.pricing.priceModel
	) as PriceOptionsType;

	return (
		<Section
			disabled
			label={i18n.translate('app-submission')}
			required
			tooltip="Tooltip"
			tooltipText={i18n.translate('more-info')}
		>
			<hr />
			<div className="border p-5 rounded-lg">
				<div>
					<div className="align-items-center d-flex">
						{appData.profile.file.preview ? (
							<img
								alt="App logo"
								className="submit-logo-icon"
								src={appData.profile.file.preview}
							/>
						) : (
							<ClayIcon
								aria-label="New App logo"
								className="submit-logo-icon text-muted"
								symbol="picture"
							/>
						)}

						<div className="d-flex flex-column pl-5">
							<span className="submit-app-name">
								{appData.profile.name}
							</span>
							<span className="submit-app-version">
								{appData.version.version}
							</span>
						</div>
					</div>
				</div>

				<hr />

				<div className="app-submit-section">
					<div className="d-flex justify-content-between">
						<div className="d-flex">
							<h3>Description</h3>{' '}
							<span>
								<ClayIcon
									className="field-base-required-asterisk-icon ml-1 text-danger"
									symbol="asterisk"
								/>
							</span>
						</div>
						<Button
							displayType="link"
							onClick={() => navigate('../profile')}
							style={{fontWeight: 600}}
						>
							Edit
						</Button>
					</div>
					<div className="card-section-body-description">
						<span>{appData.profile.description}</span>
					</div>
				</div>

				<hr />

				<div className="app-submit-section">
					<div className="d-flex">
						<h3>Category</h3>{' '}
						<span>
							<ClayIcon
								className="field-base-required-asterisk-icon ml-1 text-danger"
								symbol="asterisk"
							/>
						</span>
					</div>
					<div className="card-section-body">
						<Tag label={appData.profile.categories.label}></Tag>
					</div>
				</div>
				<hr />

				<div className="app-submit-section">
					<div className="d-flex justify-content-between">
						<div className="d-flex">
							<h3>Areas</h3>{' '}
							<span>
								<ClayIcon
									className="field-base-required-asterisk-icon ml-1 text-danger"
									symbol="asterisk"
								/>
							</span>
						</div>
						<Button
							displayType="link"
							onClick={() => navigate('../profile')}
							style={{fontWeight: 600}}
						>
							Edit
						</Button>
					</div>
					<div className="card-section-body-section-tags">
						{appData.profile.areas.map((area, index) => (
							<Tag key={index} label={area.label}></Tag>
						))}
					</div>
				</div>
				<hr />

				<div className="app-submit-section">
					<div className="d-flex justify-content-between">
						<div className="d-flex">
							<h3>Tags</h3>{' '}
							<span>
								<ClayIcon
									className="field-base-required-asterisk-icon ml-1 text-danger"
									symbol="asterisk"
								/>
							</span>
						</div>
						<Button
							displayType="link"
							onClick={() => navigate('../profile')}
							style={{fontWeight: 600}}
						>
							Edit
						</Button>
					</div>
					<div className="card-section-body-section-tags">
						{appData.profile.tags.map((tag, index) => (
							<Tag key={index} label={tag.label}></Tag>
						))}
					</div>
				</div>
				<hr />

				<div className="app-submit-section">
					<div className="d-flex justify-content-between">
						<div className="d-flex">
							<h3>Pricing</h3>{' '}
							<span>
								<ClayIcon
									className="field-base-required-asterisk-icon ml-1 text-danger"
									symbol="asterisk"
								/>
							</span>
						</div>
						<Button
							displayType="link"
							onClick={() => navigate('../pricing')}
							style={{fontWeight: 600}}
						>
							Edit
						</Button>
					</div>
					<div className="border card-section-body p-4 rounded-lg">
						<div>
							{pricingOption && (
								<>
									<div className="align-items-center d-flex">
										<span
											className="mr-2"
											style={{
												fontSize: 18,
												fontWeight: 600,
											}}
										>
											{pricingOption?.title}
										</span>{' '}
										{pricingOption?.icon && (
											<ClayIcon
												style={{color: '#377CFF'}}
												symbol={pricingOption?.icon}
											/>
										)}
									</div>

									<span
										style={{color: '#54555F', fontSize: 13}}
									>
										{pricingOption?.description}
									</span>
								</>
							)}
						</div>
					</div>
				</div>
				<hr />

				<div className="app-submit-section">
					<div className="d-flex justify-content-between">
						<div className="d-flex">
							<h3>Licensing</h3>{' '}
							<span>
								<ClayIcon
									className="field-base-required-asterisk-icon ml-1 text-danger"
									symbol="asterisk"
								/>
							</span>
						</div>
						<Button
							displayType="link"
							onClick={() => navigate('../licensing')}
							style={{fontWeight: 600}}
						>
							Edit
						</Button>
					</div>
					<div className="border card-section-body p-4 rounded-lg">
						<div>
							{licenseOption && (
								<>
									<div className="align-items-center d-flex">
										<span
											className="mr-2"
											style={{
												fontSize: 18,
												fontWeight: 600,
											}}
										>
											{licenseOption?.title}
										</span>{' '}
										<ClayIcon
											style={{color: '#377CFF'}}
											symbol={licenseOption.icon}
										/>
									</div>

									<span
										style={{color: '#54555F', fontSize: 13}}
									>
										{licenseOption?.description}
									</span>
								</>
							)}
						</div>
					</div>
					<div className="border card-section-body p-4 rounded-lg">
						<div>
							{Object.keys(appData.licensing.prices).map(
								(key) => (
									<div key={key}>
										<span>
											{key}
											{currenciesCodeObject[
												key as keyof typeof currenciesCodeObject
											].iconSrc ? (
												<img
													className="currency-selector-icon ml-2"
													src={
														currenciesCodeObject[
															key as keyof typeof currenciesCodeObject
														].iconSrc
													}
												/>
											) : (
												<ClayIcon
													className="currency-selector-icon ml-2"
													symbol={
														currenciesCodeObject[
															key as keyof typeof currenciesCodeObject
														].flag
													}
												/>
											)}
										</span>
										<div className="d-flex justify-content-between">
											{Object.entries(
												appData.licensing.prices[key]
											).map(
												(
													[priceType, values],
													index
												) => (
													<div key={index}>
														<h5 className="licesing-price-type pt-2">
															{priceType} License
															price
														</h5>

														{Object.entries(
															values
														).map(
															(
																[unit, price],
																index
															) => (
																<div
																	className="unit-price-please"
																	key={index}
																>
																	Quantity:{' '}
																	<b>
																		{unit}
																	</b>{' '}
																	- Unit
																	Price:{' '}
																	<b>
																		{
																			currenciesCodeObject[
																				key as keyof typeof currenciesCodeObject
																			]
																				.symbol
																		}
																		{
																			price as number
																		}
																	</b>
																</div>
															)
														)}
													</div>
												)
											)}
										</div>
										<hr />
									</div>
								)
							)}
						</div>
					</div>
				</div>
				<hr />

				<div className="app-submit-section">
					<div className="d-flex justify-content-between">
						<div className="d-flex">
							<h3>Storefront</h3>{' '}
							<span>
								<ClayIcon
									className="field-base-required-asterisk-icon ml-1 text-danger"
									symbol="asterisk"
								/>
							</span>
						</div>
						<Button
							displayType="link"
							onClick={() => navigate('../storefront')}
							style={{fontWeight: 600}}
						>
							Edit
						</Button>
					</div>
					<div className="card-section-body">
						<div className="submit-images-container">
							<span className="storefront-section-title">
								IMAGES
							</span>
							{appData.storefront.images.map((image, index) => (
								<div className="d-flex mt-3" key={index}>
									<img src={image.preview} />
									<div className="d-flex flex-column justify-content-center ml-4">
										<ClayIcon
											className="card-link-icon-image"
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
						<p className="important-note">
							Important: Images will be displayed following the
							numerical order above
						</p>
						{appData.storefront.video.videoURL !== '' && (
							<div className="mt-5 submit-images-container">
								<span className="storefront-section-title">
									VIDEO
								</span>
								<div className="d-flex mt-3">
									<VideoThumbnail
										videoURL={
											appData.storefront.video.videoURL
										}
									/>
									<div className="d-flex flex-column justify-content-center ml-4">
										<ClayIcon
											className="card-link-icon-image"
											symbol="video"
										/>
										<span className="storefront-url-title">
											{appData.storefront.video.videoURL}
										</span>
										<span className="storefront-description">
											{
												appData.storefront.video
													.description
											}
										</span>
									</div>
								</div>
							</div>
						)}
					</div>
				</div>
				<hr />

				<div className="app-submit-section">
					<div className="d-flex justify-content-between">
						<div className="d-flex">
							<h3>Support & Help</h3>{' '}
							<span>
								<ClayIcon
									className="field-base-required-asterisk-icon ml-1 text-danger"
									symbol="asterisk"
								/>
							</span>
						</div>
						<Button
							displayType="link"
							onClick={() => navigate('../support')}
							style={{fontWeight: 600}}
						>
							Edit
						</Button>
					</div>
					<div className="border mt-5 p-4 rounded-lg">
						<div className="card-link-main-info">
							<div className="card-link-icon">
								<ClayIcon
									aria-label="Icon"
									className="card-link-icon-image"
									symbol="link"
								/>
							</div>

							<div className="card-link-info">
								<span className="card-link-info-text">
									Support URL
								</span>
								{appData.support.url && (
									<a
										className="card-link-info-description text-truncate"
										href={appData.support.url}
										target="_blank"
										title={appData.support.url}
									>
										{appData.support.url}
									</a>
								)}
							</div>
						</div>
					</div>

					<div className="border mt-5 p-4 rounded-lg">
						<div className="card-link-main-info">
							<div className="card-link-icon">
								<ClayIcon
									aria-label="Icon"
									className="card-link-icon-image"
									symbol="globe"
								/>
							</div>

							<div className="card-link-info">
								<span className="card-link-info-text">
									Publisher website URL
								</span>
								{appData.support.publisherWebsiteURL && (
									<a
										className="card-link-info-description text-truncate"
										href={
											appData.support.publisherWebsiteURL
										}
										target="_blank"
										title={
											appData.support.publisherWebsiteURL
										}
									>
										{appData.support.publisherWebsiteURL}
									</a>
								)}
							</div>
						</div>
					</div>

					<div className="border mt-5 p-4 rounded-lg">
						<div className="card-link-main-info">
							<div className="card-link-icon">
								<ClayIcon
									aria-label="Icon"
									className="card-link-icon-image"
									symbol="envelope-open"
								/>
							</div>

							<div className="card-link-info">
								<span className="card-link-info-text">
									Support Email
								</span>
								{appData.support.email && (
									<a
										className="card-link-info-description text-truncate"
										href={appData.support.email}
										target="_blank"
										title={appData.support.email}
									>
										{appData.support.email}
									</a>
								)}
							</div>
						</div>
					</div>

					<div className="border mt-5 p-4 rounded-lg">
						<div className="card-link-main-info">
							<div className="card-link-icon">
								<ClayIcon
									aria-label="Icon"
									className="card-link-icon-image"
									symbol="phone"
								/>
							</div>

							<div className="card-link-info">
								<span className="card-link-info-text">
									Support Phone
								</span>
								{appData.support.phone && (
									<a
										className="card-link-info-description text-truncate"
										href={appData.support.phone}
										target="_blank"
										title={appData.support.phone}
									>
										{appData.support.phone}
									</a>
								)}
							</div>
						</div>
					</div>

					<div className="border mt-5 p-4 rounded-lg">
						<div className="card-link-main-info">
							<div className="card-link-icon">
								<ClayIcon
									aria-label="Icon"
									className="card-link-icon-image"
									symbol="document"
								/>
							</div>

							<div className="card-link-info">
								<span className="card-link-info-text">
									App usage terms (EULA) URL
								</span>
								{appData.support.appUsageTermsURL && (
									<a
										className="card-link-info-description text-truncate"
										href={appData.support.appUsageTermsURL}
										target="_blank"
										title={appData.support.appUsageTermsURL}
									>
										{appData.support.appUsageTermsURL}
									</a>
								)}
							</div>
						</div>
					</div>

					<div className="border mt-5 p-4 rounded-lg">
						<div className="card-link-main-info">
							<div className="card-link-icon">
								<ClayIcon
									aria-label="Icon"
									className="card-link-icon-image"
									symbol="document"
								/>
							</div>

							<div className="card-link-info">
								<span className="card-link-info-text">
									App documentation URL
								</span>
								{appData.support.documentationURL && (
									<a
										className="card-link-info-description text-truncate"
										href={appData.support.documentationURL}
										target="_blank"
										title={appData.support.documentationURL}
									>
										{appData.support.documentationURL}
									</a>
								)}
							</div>
						</div>
					</div>

					<div className="border mt-5 p-4 rounded-lg">
						<div className="card-link-main-info">
							<div className="card-link-icon">
								<ClayIcon
									aria-label="Icon"
									className="card-link-icon-image"
									symbol="sites"
								/>
							</div>

							<div className="card-link-info">
								<span className="card-link-info-text">
									App installation guide URL
								</span>
								{appData.support.installationGuideURL && (
									<a
										className="card-link-info-description text-truncate"
										href={
											appData.support.installationGuideURL
										}
										target="_blank"
										title={
											appData.support.installationGuideURL
										}
									>
										{appData.support.installationGuideURL}
									</a>
								)}
							</div>
						</div>
					</div>
				</div>
			</div>
		</Section>
	);
};

export default Submit;
