/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Tooltip} from '../../../../../../components/Tooltip/Tooltip';
import {
	SolutionTypes,
	useSolutionContext,
} from '../../../../../../context/SolutionContext';
import i18n from '../../../../../../i18n';

import '../../components/ContentReview/ContentReview.scss';

import {ClayCheckbox} from '@clayui/form';
import DOMPurify from 'dompurify';

import en_US from '../../../../../../i18n/en_US';
import {ContentReview} from '../../components/ContentReview';

const Submit = () => {
	const [context, dispatch] = useSolutionContext();

	const {company, contactUs, details, header, profile} = context;

	return (
		<div className="mb-4 solutions-form-header">
			<span className="align-items-center d-flex">
				<h2 className="mb-0 mr-3">
					{i18n.translate('solution-submission')}
				</h2>
				<Tooltip
					tooltip={i18n.translate('more-info')}
					tooltipText={i18n.translate('more-info')}
				/>
			</span>

			<hr className="mb-5 mt-2" />

			<ContentReview>
				<ContentReview.Section>
					<ContentReview.Header as="h2" path="../profile">
						<div className="align-items-center d-flex">
							<img
								alt=""
								className="mr-4 solution-preview-profile-logo"
								src={profile.file.preview}
							/>
							<h1 className="mb-0">{profile.name}</h1>
						</div>
					</ContentReview.Header>
					<ContentReview.Paragraph
						title={i18n.translate('description')}
					>
						{profile.description}
					</ContentReview.Paragraph>

					<ContentReview.Paragraph
						title={i18n.translate('categories')}
					>
						<div className="d-flex">
							{profile.categories.map((category) => (
								<div
									className="mr-3 solution-preview-profile-tags"
									key={category.value}
								>
									{category.label}
								</div>
							))}
						</div>
					</ContentReview.Paragraph>

					<ContentReview.Paragraph title={i18n.translate('tags')}>
						<div className="d-flex">
							{profile.tags.map((tag) => (
								<div
									className="mr-3 solution-preview-profile-tags"
									key={tag.value}
								>
									{tag.label}
								</div>
							))}
						</div>
					</ContentReview.Paragraph>
				</ContentReview.Section>

				<ContentReview.Separator />

				<ContentReview.Section>
					<ContentReview.Header as="h2" path="../header">
						{i18n.translate('header')}
					</ContentReview.Header>
					<ContentReview.Paragraph
						className="mb-5"
						title={i18n.translate(
							header.title as keyof typeof en_US
						)}
					>
						<p
							dangerouslySetInnerHTML={{
								__html: DOMPurify.sanitize(header.description),
							}}
						/>
					</ContentReview.Paragraph>

					<ContentReview.Paragraph className="mb-6">
						{header.contentType.type === 'upload-images' &&
							header.contentType.content.headerImages.map(
								(image, index) => (
									<ContentReview.ImageInfo
										icon="document-image"
										imageFile={image}
										key={index}
									/>
								)
							)}

						{header.contentType.type === 'embed-video-url' && (
							<div className="d-flex flex-row">
								<ContentReview.Video className="mr-3">
									{header.contentType.content.headerVideoUrl}
								</ContentReview.Video>

								<ContentReview.Paragraph className="mt-3">
									{
										header.contentType.content
											.headerVideoDescription
									}
								</ContentReview.Paragraph>
							</div>
						)}
					</ContentReview.Paragraph>

					<ContentReview.Paragraph>
						{i18n.translate(
							'important-Images-will-be-displayed-following-thenumerical-order-above'
						)}
					</ContentReview.Paragraph>
				</ContentReview.Section>

				<ContentReview.Separator />

				{!!details.length && (
					<ContentReview.Section>
						<ContentReview.Header as="h2" path="../details">
							{i18n.translate('solution-details')}
						</ContentReview.Header>
						{details.map((block, index) => {
							return (
								<ContentReview.Block
									key={index}
									title={i18n.translate(
										block.type as keyof typeof en_US
									)}
								>
									<ContentReview.Paragraph
										title={i18n.translate('title')}
									>
										{i18n.translate(
											block.content
												.title as keyof typeof en_US
										)}
									</ContentReview.Paragraph>
									<ContentReview.Paragraph
										title={i18n.translate('description')}
									>
										<p
											dangerouslySetInnerHTML={{
												__html: DOMPurify.sanitize(
													block.content.description
												),
											}}
										/>
									</ContentReview.Paragraph>

									{block.type === 'text-images-block' &&
										block.content.files.map(
											(file, fileIndex) => (
												<ContentReview.ImageInfo
													icon="document-image"
													imageFile={file}
													key={fileIndex}
												/>
											)
										)}

									{block.type === 'text-video-block' && (
										<div className="d-flex">
											<ContentReview.Video className="mr-3">
												{block.content.videoUrl}
											</ContentReview.Video>

											<ContentReview.Paragraph className="mt-3">
												{block.content.videoDescription}
											</ContentReview.Paragraph>
										</div>
									)}
								</ContentReview.Block>
							);
						})}
					</ContentReview.Section>
				)}

				<ContentReview.Separator />

				<ContentReview.Section>
					<ContentReview.Header as="h2" path="../company">
						<h2 className="mb-0">
							{i18n.translate('company-profile')}
						</h2>
					</ContentReview.Header>

					<ContentReview.Paragraph
						title={i18n.translate('description')}
					>
						<p
							dangerouslySetInnerHTML={{
								__html: DOMPurify.sanitize(company.description),
							}}
						/>
					</ContentReview.Paragraph>
					<ContentReview.Paragraph>
						<ContentReview.SuportLink
							href={company.website}
							linkLabel={i18n.translate('publisher-website-url ')}
							symbol="globe"
						/>

						<ContentReview.SuportLink
							href={company.email}
							linkLabel={i18n.translate('email')}
							symbol="envelope-closed"
						/>

						<ContentReview.SuportLink
							href={company.phone}
							linkLabel={i18n.translate('phone')}
							symbol="phone"
						/>
					</ContentReview.Paragraph>
				</ContentReview.Section>

				<ContentReview.Separator />

				<ContentReview.Section>
					<ContentReview.Header as="h2" path="../contact">
						<h2 className="mb-0">{i18n.translate('contact-us')}</h2>
					</ContentReview.Header>
					<ContentReview.SuportLink
						href={contactUs}
						linkLabel={i18n.translate('email')}
						symbol="envelope-closed"
					/>
				</ContentReview.Section>
			</ContentReview>

			<div className="d-flex my-5">
				<ClayCheckbox
					checked={!!context.submit}
					onChange={(event) => {
						dispatch({
							payload: event.target.checked,
							type: SolutionTypes.SET_SUBMIT,
						});
					}}
				/>
				<p className="ml-4">
					Attention: this cannot be undone. I am aware I cannot edit
					any data or information regarding this solution submission
					until Liferay completes its review process and I agree with
					the Liferay Marketplace&nbsp;<a>terms</a>&nbsp; and &nbsp;
					<a>privacy</a>&nbsp;
				</p>
			</div>
		</div>
	);
};

export default Submit;
