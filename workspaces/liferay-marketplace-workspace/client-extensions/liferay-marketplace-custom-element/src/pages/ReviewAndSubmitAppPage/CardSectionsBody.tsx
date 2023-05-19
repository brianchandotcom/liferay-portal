/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import brightnessEmptyIcon from '../../assets/icons/brightness_empty_icon.svg';
import calendarMonthIcon from '../../assets/icons/calendar_month_icon.svg';
import creditCardIcon from '../../assets/icons/credit_card_icon.svg';
import documentIcon from '../../assets/icons/document_icon.svg';
import scheduleIcon from '../../assets/icons/schedule_icon.svg';
import {CardLink} from '../../components/Card/CardLink';
import {CardView} from '../../components/Card/CardView';
import {LicensePriceChildren} from '../../components/LicensePriceCard/LicensePriceChildren';
import {Tag} from '../../components/Tag/Tag';
import {extractHTMLText, removeUnnecessaryURLString} from '../../utils/string';
import {CardSection} from './CardSection';
import {App} from './ReviewAndSubmitAppPageUtil';

import './CardSectionsBody.scss';

interface CardSectionsBodyProps {
	app: App;
	readonly: boolean;
}

export function CardSectionsBody({app, readonly}: CardSectionsBodyProps) {
	return (
		<>
			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Description"
			>
				<p className="card-section-body-section-paragraph">
					{extractHTMLText(app?.description)}
				</p>
			</CardSection>

			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Categories"
			>
				<div className="card-section-body-section-tags">
					{app?.categories.map((tag, index) => {
						return <Tag key={index} label={tag}></Tag>;
					})}
				</div>
			</CardSection>

			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Tags"
			>
				<div className="card-section-body-section-tags">
					{app?.tags.map((tag, index) => {
						return <Tag key={index} label={tag}></Tag>;
					})}
				</div>
			</CardSection>

			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Build"
			>
				<div className="card-section-body-section-file">
					<div className="card-section-body-section-file-container">
						<img
							alt="Folder Icon"
							className="card-section-body-section-file-container-icon"
							src={documentIcon}
						/>
					</div>

					<img
						alt="Document Icon"
						className="card-section-body-section-file-icon"
						src={documentIcon}
					/>

					<span className="card-section-body-section-file-name">
						{app?.attachmentTitle}
					</span>
				</div>
			</CardSection>

			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Pricing"
			>
				<CardView
					description={
						app?.priceModel === 'Free'
							? 'The app is offered in the Marketplace with no charge.'
							: 'To enable paid apps, you must be a business and enter payment information in your Marketplace account profile.'
					}
					icon={
						app?.priceModel === 'Free'
							? brightnessEmptyIcon
							: creditCardIcon
					}
					title={app?.priceModel as string}
				/>
			</CardSection>

			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Licensing"
			>
				<CardView
					description={
						app?.licenseType === 'Perpetual'
							? 'License never expires.'
							: 'License must be renewed annually.'
					}
					icon={
						app?.licenseType === 'Perpetual'
							? scheduleIcon
							: calendarMonthIcon
					}
					title={
						app?.licenseType === 'Perpetual'
							? 'Perpetual License'
							: 'Non-perpetual License'
					}
				>
					{app?.priceModel === 'Paid' && (
						<LicensePriceChildren
							currency="USD"
							quantity={{
								from: '1',
								to: '1',
							}}
							value={
								app?.price.toLocaleString('en-US', {
									currency: 'USD',
									style: 'currency',
								}) as string
							}
						/>
					)}
				</CardView>
			</CardSection>

			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Storefront"
			>
				<div>
					{app?.storefront?.map(({id, src, title}) => {
						return (
							<div
								className="card-section-body-section-files"
								key={id}
							>
								<div className="card-section-body-section-files-container">
									<img
										className="preview-image"
										src={removeUnnecessaryURLString(src)}
									/>
								</div>

								<div className="card-section-body-section-files-data">
									<img
										alt={title['en_US']}
										className="card-section-body-section-files-data-icon"
										src={documentIcon}
									/>

									<span className="card-section-body-section-files-data-name">
										{title['en_US']}
									</span>

									<span className="card-section-body-section-files-data-description"></span>
								</div>
							</div>
						);
					})}

					<div className="card-section-body-section-files-info">
						Important: Images will be displayed following the
						numerical order above
					</div>
				</div>
			</CardSection>

			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Version"
			>
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

			<CardSection
				enableEdit={!readonly}
				localized
				required
				sectionName="Support & Help"
			>
				{app?.supportAndHelp.map(({icon, link, title}) => (
					<CardLink
						description={link as string}
						icon={icon}
						key={title}
						title={title as string}
					/>
				))}
			</CardSection>
		</>
	);
}
