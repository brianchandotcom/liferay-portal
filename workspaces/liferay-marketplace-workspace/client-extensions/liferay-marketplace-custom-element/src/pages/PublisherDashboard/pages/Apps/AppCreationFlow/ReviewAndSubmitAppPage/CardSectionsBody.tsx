/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import DOMPurify from 'dompurify';

import brightnessEmptyIcon from '../../../../../../assets/icons/brightness_empty_icon.svg';
import {CardLink} from '../../../../../../components/Card/CardLink';
import {CardView} from '../../../../../../components/Card/CardView';
import LicensePriceChildren from '../../../../../../components/LicensePriceCard/LicensePriceChildren';
import {Tag} from '../../../../../../components/Tag/Tag';
import i18n from '../../../../../../i18n';
import {removeUnnecessaryURLString} from '../../../../../../utils/string';
import {CardSection} from './CardSection';
import {App} from './ReviewAndSubmitAppPageUtil';

import './CardSectionsBody.scss';

interface CardSectionsBodyProps {
	app: App;
	isApp?: boolean;
	readonly: boolean;
}

export function CardSectionsBody({
	app,
	isApp = true,
	readonly,
}: CardSectionsBodyProps) {
	const isCloud = app?.type === 'cloud';

	return (
		<>
			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Description"
			>
				<p
					className="card-section-body-section-paragraph"
					dangerouslySetInnerHTML={{
						__html: DOMPurify.sanitize(app?.description),
					}}
				></p>
			</CardSection>

			<CardSection required sectionName="Categories">
				<div className="card-section-body-section-tags">
					{app?.categories?.map((tag, index) => {
						return <Tag key={index} label={tag}></Tag>;
					})}
				</div>
			</CardSection>

			<CardSection required sectionName="Tags">
				<div className="card-section-body-section-tags">
					{app?.tags?.map((tag, index) => {
						return <Tag key={index} label={tag}></Tag>;
					})}
				</div>
			</CardSection>

			{isApp && (
				<CardSection required sectionName="Cloud Compatible">
					<div className="card-section-body-cloud-compatible">
						<CardView
							description={
								isCloud
									? i18n.translate(
											'create-a-cloud-app-to-be-delivered-as-a-live-service'
										)
									: i18n.translate(
											'create-a-dxp-app-to-be-delivered-as-a-download'
										)
							}
							icon={isCloud ? 'check-circle' : 'times-circle'}
							title={isCloud ? 'Yes' : 'No'}
						/>
					</div>
				</CardSection>
			)}
			{isCloud && (
				<CardSection
					enableEdit={readonly}
					required
					sectionName={i18n.translate('resource-requirements')}
				>
					<div className="card-section-body-section-requirements d-flex justify-content-between">
						<CardView
							description={app?.resourceRequirements?.cpu}
							title={i18n.translate('number-of-cpus')}
							tooltip={
								readonly ? '' : i18n.translate('more-info')
							}
						/>

						<CardView
							description={`${app?.resourceRequirements?.ram} GB`}
							title="Ram in GB"
							tooltip={
								readonly ? '' : i18n.translate('more-info')
							}
						/>
					</div>
				</CardSection>
			)}
			{isApp && (
				<>
					<CardSection required sectionName="Build">
						<div className="card-section-body-section-file">
							<div className="card-section-body-section-file-container">
								<ClayIcon
									aria-label="Folder Icon"
									className="card-section-body-section-file-container-icon"
									symbol="document-text"
								/>
							</div>

							<span className="card-section-body-section-file-name ml-3">
								{app?.attachmentTitle}
							</span>
						</div>
					</CardSection>

					<CardSection required sectionName="Pricing">
						<CardView
							description={
								app?.['price-model'] === 'Free'
									? 'The app is offered in the Marketplace with no charge.'
									: 'To enable paid apps, you must be a business and enter payment information in your Marketplace account profile.'
							}
							icon={
								app?.['price-model'] === 'Free'
									? brightnessEmptyIcon
									: 'credit-card'
							}
							title={app?.['price-model'] as string}
						/>
					</CardSection>

					<CardSection required sectionName="Licensing">
						<CardView
							description={
								app?.['license-type'] === 'Perpetual'
									? 'License never expires.'
									: 'App License must be renewed annually.'
							}
							icon={
								app?.['license-type'] === 'Perpetual'
									? 'time'
									: 'calendar'
							}
							title={
								app?.['license-type'] === 'Perpetual'
									? 'Perpetual License'
									: 'Subscription License'
							}
						>
							{app?.['price-model'] === 'Paid' && (
								<LicensePriceChildren
									app={app}
									isCloud={isCloud}
									tierPrices={app.tierPrice}
								/>
							)}
						</CardView>
					</CardSection>
				</>
			)}

			<CardSection required sectionName="Storefront">
				<div>
					{app?.storefront?.map(({id, priority, src, title}) => (
						<div
							className="align-items-center card-section-body-section-files d-flex"
							key={id}
						>
							<strong>{isApp ?? priority}</strong>

							<div className="card-section-body-section-files-container">
								<img
									alt="Image preview"
									className="preview-image"
									src={removeUnnecessaryURLString(src)}
								/>
							</div>

							<div className="card-section-body-section-files-data ml-2">
								<span className="card-section-body-section-files-data-name">
									{title['en_US']}
								</span>

								<span className="card-section-body-section-files-data-description"></span>
							</div>
						</div>
					))}

					<div className="card-section-body-section-files-info">
						Important: Images will be displayed following the
						numerical order above
					</div>
				</div>
			</CardSection>

			{isApp && (
				<>
					<CardSection required sectionName="Version">
						<div className="card-section-body-section-version">
							<div className="card-section-body-section-version-container">
								<div className="card-section-body-section-version-container-icon">
									{app?.version}
								</div>
							</div>

							<div className="card-section-body-section-version-data">
								<span className="card-section-body-section-version-data-name">
									Release Notes
								</span>

								<span className="card-section-body-section-version-data-description">
									{app?.versionDescription}
								</span>
							</div>
						</div>
					</CardSection>
					<CardSection required sectionName="Support & Help">
						{app?.supportAndHelp.map(
							({icon, link, title}, index) => (
								<CardLink
									description={link as string}
									icon={icon}
									key={index}
									title={title as string}
								/>
							)
						)}
					</CardSection>
				</>
			)}
		</>
	);
}
