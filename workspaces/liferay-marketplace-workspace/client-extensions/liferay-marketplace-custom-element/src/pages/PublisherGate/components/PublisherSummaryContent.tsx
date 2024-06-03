/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {Status} from '@clayui/modal/lib/types';
import classNames from 'classnames';

import i18n from '../../../i18n';

type DisplayCardInfoProps = {
	className?: string;
	icon: string;
	iconAlign?: any;
	info: any;
	title: string;
};

type PublisherSummaryContentProps = {
	title?: string;
	userInfo?: PublisherRequestInfo;
};

const STATUS = {
	completed: 'success',
	inProgress: 'info',
	open: 'secondary',
	rejected: 'danger',
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
			<span className="display-card-description">{info}</span>
		</div>
	</div>
);

const PublisherSummaryContent: React.FC<PublisherSummaryContentProps> = ({
	title,
	userInfo,
}) => (
	<div className="border p-5 rounded">
		{title && (
			<>
				<h1>{title}</h1>
				<hr />
			</>
		)}
		<div className="d-flex justify-content-between">
			<span className="mb-3">
				<DisplayCardInfo
					className="mb-5"
					icon="user"
					info={`${userInfo?.firstName} ${userInfo?.lastName}`}
					title={i18n.translate('name')}
				/>
			</span>

			{userInfo?.requestStatus && (
				<DisplayCardInfo
					className="col-6 mb-5"
					icon="warning-full"
					info={
						<ClayLabel
							displayType={
								STATUS[
									userInfo?.requestStatus
										?.key as keyof typeof STATUS
								] as Status
							}
						>
							{userInfo?.requestStatus?.name}
						</ClayLabel>
					}
					title={i18n.translate('status')}
				/>
			)}
		</div>

		<div>
			<div className="d-flex justify-content-between">
				<DisplayCardInfo
					className="mb-5"
					icon="phone"
					info={`${userInfo?.phone?.code ?? ''} ${
						userInfo?.phoneNumber ?? ''
					}`}
					title={i18n.translate('phone')}
				/>

				<DisplayCardInfo
					className="col-6 mb-5"
					icon="envelope-closed"
					info={userInfo?.emailAddress}
					title={i18n.translate('email')}
				/>
			</div>

			<div>
				<DisplayCardInfo
					className="mb-5"
					icon="order-form-tag"
					info={<small>{userInfo?.publisherType.join(', ')}</small>}
					title={i18n.translate('publisher-type')}
				/>
			</div>

			<span>
				<DisplayCardInfo
					icon="document"
					info={userInfo?.requestDescription}
					title={i18n.translate('description')}
				/>
			</span>
		</div>
	</div>
);

export default PublisherSummaryContent;
