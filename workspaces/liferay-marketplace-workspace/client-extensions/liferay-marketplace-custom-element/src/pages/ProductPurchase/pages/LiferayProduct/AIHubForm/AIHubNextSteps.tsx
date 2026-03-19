/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import useSWR from 'swr';

import {AccountAndAppCard} from '../../../../../components/Card/AccountAndAppCard';
import {Header} from '../../../../../components/Header/Header';
import {PageRenderer} from '../../../../../components/Page';
import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import HeadlessAdminUser from '../../../../../services/rest/HeadlessAdminUser';
import {getSiteURL} from '../../../../../utils/site';
import {getAccountImage} from '../../../../../utils/util';
import checkCircleIcon from '../../.././../../assets/icons/check_circle_icon.svg';

type AIHubNextStepsProps = {
	data: ReturnType<typeof useGetProductByOrderId>['data'];
	error: ReturnType<typeof useGetProductByOrderId>['error'];
	isLoading: ReturnType<typeof useGetProductByOrderId>['isLoading'];
};

const AIHubNextSteps: React.FC<AIHubNextStepsProps> = ({
	data,
	error,
	isLoading,
}: AIHubNextStepsProps) => {
	const placedOrder = data?.placedOrder;
	const product = data?.product;

	const accountId = placedOrder?.accountId;
	const productName = product?.name || '';

	const {data: accountCommerce} = useSWR(
		accountId ? `/next-steps/account-commerce/${accountId}` : null,
		() => HeadlessAdminUser.getAccount(accountId as unknown as string)
	);

	return (
		<PageRenderer
			className="my-8 next-step-page-container"
			error={error}
			isLoading={isLoading}
		>
			<div className="liferay-ai-hub-container my-8">
				<div className="next-step-page-cards">
					<AccountAndAppCard
						category={product?.catalogName as string}
						logo={
							data?.marketplaceDeliveryOrder?.productThumbnail ||
							'catalog'
						}
						title={
							<div className="align-items-center d-flex">
								{productName}{' '}
								<label className="beta-badge-label ml-2 mt-1">
									Private Beta
								</label>{' '}
							</div>
						}
					/>

					<div className="mx-4">
						<ClayIcon
							className="liferay-ai-hub-form-next-step-page-icon m-0 next-step-page-icon"
							symbol="arrow-right-full"
						/>
					</div>

					<AccountAndAppCard
						category="Account"
						logo={getAccountImage(
							accountCommerce?.logoURL as string
						)}
						title={accountCommerce?.name ?? ''}
					/>
				</div>

				<div className="next-step-page-text">
					<div className="next-step-page-text">
						<Header
							description={
								<span className="text-center">
									<p className="mb-1">
										We&apos;ve received your submission and
										will review it. You&apos;ll receive an
										email confirming
									</p>
									<p className="mb-5">
										whether your access to the{' '}
										<strong>
											Liferay AI Hub Private Beta
										</strong>{' '}
										has been approved.
									</p>
								</span>
							}
							icon={
								<span className="d-flex justify-content-center">
									<img
										alt="payment pending icon"
										draggable="false"
										src={checkCircleIcon}
									/>
								</span>
							}
							title={
								<span className="d-flex justify-content-center mb-5 next-step-page-title">
									{i18n.translate(
										'thank-you-for-your-request'
									)}
								</span>
							}
						/>
					</div>
				</div>

				<div className="d-flex justify-content-center mt-4 next-step-page-footer-button-container w-100">
					<ClayButton
						className="mr-3 next-step-page-footer-button-back"
						displayType="secondary"
						onClick={() => {
							Liferay.Util.navigate(
								`${getSiteURL()}/customer-dashboard/#/products`
							);
						}}
					>
						{i18n.translate('go-to-dashboard')}
					</ClayButton>
				</div>
			</div>
		</PageRenderer>
	);
};

export default AIHubNextSteps;
