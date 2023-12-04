/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {UseFormReturn} from 'react-hook-form';

import {Header} from '../../../components/Header/Header';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import {PublisherForm, StepType} from './PublisherGateSteps';

type PublisherGateSummaryProps = {
	form: UseFormReturn<
		{
			emailAddress: string;
			extension?: string | undefined;
			firstName: string;
			lastName: string;
			phone?: {
				code: string;
				flag: string;
			};
			phoneNumber: string;
			requestDescription: string;
		},
		any,
		undefined
	>;
	setStep: React.Dispatch<React.SetStateAction<StepType>>;
	submit: (form: PublisherForm) => Promise<void>;
};

type DisplayCardInfoProps = {
	className?: string;
	icon: string;
	iconAlign?: any;
	info: any;
	title: string;
};
const DisplayCardInfo: React.FC<DisplayCardInfoProps> = ({
	className,
	icon,
	info,
	title,
}) => (
	<div
		className={classNames('d-flex ', className, {
			'align-items-center': info?.length < 60,
			'align-items-start': info?.length >= 60,
		})}
	>
		<span className="align-items-center d-flex icon-container justify-content-center mr-4">
			<ClayIcon
				className="detailed-card-header-clay-icon"
				symbol={icon}
			/>
		</span>
		<div className="d-flex flex-column text-wrap">
			<span className="font-weight-bold">{title}</span>
			<span className="display-card-description text-secondary">
				{info}
			</span>
		</div>
	</div>
);

const PublisherGateSummary: React.FC<PublisherGateSummaryProps> = ({
	form,
	setStep,
	submit,
}) => {
	const userInfo = form.watch();

	return (
		<div className="publisher-gate-page-container">
			<div className="publisher-gate-page-body">
				<Header
					description={i18n.translate(
						'review-the-new-publisher-info-and-the-liferay-marketplace-terms-before-proceeding'
					)}
					title={i18n.translate('complete-publisher-account-request')}
				/>

				<div className="border mt-8 p-5 rounded">
					<h3>{i18n.translate('request-details')}</h3>

					<hr className="mb-5" />

					<span className="mb-3">
						<DisplayCardInfo
							className="mb-5"
							icon="user"
							info={`${userInfo.firstName} ${userInfo.lastName}`}
							title={i18n.translate('name')}
						/>
					</span>

					<div>
						<div className="d-flex justify-content-between">
							<DisplayCardInfo
								className="mb-5"
								icon="phone"
								info={`${userInfo?.phone?.code} ${userInfo.phoneNumber}`}
								title={i18n.translate('phone')}
							/>

							<DisplayCardInfo
								className="mb-5"
								icon="envelope-closed"
								info={userInfo.emailAddress}
								title={i18n.translate('email')}
							/>
						</div>
						<span>
							<DisplayCardInfo
								icon="document"
								info={userInfo.requestDescription}
								title={i18n.translate('description')}
							/>
						</span>
					</div>
				</div>
				<div className="mt-5">
					<span>
						<p className="privacy-text text-justify">
							{i18n.translate(
								'by-requesting-a-publisher-account-you-agree-to-the'
							)}
							&nbsp;
							<strong>{i18n.translate('content-policy')}</strong>
							.&nbsp;{i18n.translate('liferay-s')}&nbsp;
							<strong>
								{i18n.translate('terms-of-service')}
							</strong>
							{` ${i18n.translate('and')} `}&nbsp;
							<strong>{i18n.translate('privacy-policy')}</strong>
							&nbsp;
							{i18n.translate(
								'apply-to-your-use-of-this-service-the-name-on-your-liferay-account-will-be-used-in-this-liferay-marketplace-publisher-profile-it-may-appear-where-you-contribute-and-be-changed-at-any-time'
							)}
							.
						</p>
					</span>
				</div>

				<hr className="mb-5 mt-8" />

				<div className="mb-8 purchased-solutions-button-container">
					<div className="align-items-center d-flex justify-content-between mb-4 w-100">
						<ClayButton
							className="p-3"
							displayType="unstyled"
							onClick={() => {
								window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/home`;
							}}
						>
							{i18n.translate('cancel')}
						</ClayButton>

						<div>
							<ClayButton
								className="mr-4"
								displayType="secondary"
								onClick={() => setStep(StepType.FORM)}
							>
								{i18n.translate('back')}
							</ClayButton>

							<ClayButton onClick={form.handleSubmit(submit)}>
								{i18n.translate('request-account')}
							</ClayButton>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default PublisherGateSummary;
