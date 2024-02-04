/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import brightnessEmptyIcon from '../../assets/icons/brightness_empty_icon.svg';
import calendarMonthIcon from '../../assets/icons/calendar_month_icon.svg';
import cancelIcon from '../../assets/icons/cancel_icon.svg';
import creditCardIcon from '../../assets/icons/credit_card_icon.svg';
import documentIcon from '../../assets/icons/document_icon.svg';
import scheduleIcon from '../../assets/icons/schedule_icon.svg';
import taskCheckedIcon from '../../assets/icons/task_checked_icon.svg';
import {CardLink} from '../../components/Card/CardLink';
import {CardView} from '../../components/Card/CardView';
import {LicensePriceChildren} from '../../components/LicensePriceCard/LicensePriceChildren';
import {Tag} from '../../components/Tag/Tag';
import {extractHTMLText, removeUnnecessaryURLString} from '../../utils/string';
import {CardSection} from './CardSection';
import {App} from './ReviewAndSubmitAppPageUtil';

import './CardSectionsBody.scss';
import i18n from '../../i18n';

interface CardSectionsBodyProps {
	app: App;
	readonly: boolean;
}

export function CardSectionsBody({app, readonly}: CardSectionsBodyProps) {
	const isCloud = app?.type === 'cloud';

	return (
		<>
			<CardSection required sectionName="Description">
				<p className="card-section-body-section-paragraph">
					{extractHTMLText(app?.description)}
				</p>
			</CardSection>

			<CardSection required sectionName="Categories">
				<div className="card-section-body-section-tags">
					{app?.categories.map((tag, index) => {
						return <Tag key={index} label={tag}></Tag>;
					})}
				</div>
			</CardSection>

			<CardSection required sectionName="Tags">
				<div className="card-section-body-section-tags">
					{app?.tags.map((tag, index) => {
						return <Tag key={index} label={tag}></Tag>;
					})}
				</div>
			</CardSection>

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
						icon={isCloud ? taskCheckedIcon : cancelIcon}
						title={isCloud ? 'Yes' : 'No'}
					/>
				</div>
			</CardSection>

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

			<CardSection required sectionName="Build">
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
							: creditCardIcon
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
							? scheduleIcon
							: calendarMonthIcon
					}
					title={
						app?.['license-type'] === 'Perpetual'
							? 'Perpetual License'
							: 'Subscription License'
					}
				>
					{app?.['price-model'] === 'Paid' && (
						<LicensePriceChildren
							currency="USD"
							quantity={{
								from: '1',
								to: '1',
							}}
							value={
								app?.price?.toLocaleString('en-US', {
									currency: 'USD',
									style: 'currency',
								}) as string
							}
						/>
					)}
				</CardView>
			</CardSection>

			<CardSection required sectionName="Storefront">
				<div>
					{app?.storefront?.map(({id, src, title}) => (
						<div
							className="card-section-body-section-files"
							key={id}
						>
							<div>{app?.storefront.length}</div>
							<div className="card-section-body-section-files-container">
								<img
									alt="Image preview"
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
					))}

					<div className="card-section-body-section-files-info">
						Important: Images will be displayed following the
						numerical order above
					</div>
				</div>
			</CardSection>

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
				{app?.supportAndHelp.map(({icon, link, title}, index) => (
					<CardLink
						description={link as string}
						icon={icon}
						key={index}
						title={title as string}
					/>
				))}
			</CardSection>
		</>
	);
}
