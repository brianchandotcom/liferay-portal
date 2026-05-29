/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayButton from '@clayui/button';
import useSWR from 'swr';

import {AccountAndAppCard} from '../../../../../components/Card/AccountAndAppCard';
import {Header} from '../../../../../components/Header/Header';
import {PageRenderer} from '../../../../../components/Page';
import useGetProductByOrderId from '../../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../../i18n';
import HeadlessAdminUser from '../../../../../services/rest/HeadlessAdminUser';
import {getAccountImage} from '../../../../../utils/util';
import checkCircleIcon from '../../.././../../assets/icons/check_circle_icon.svg';
import {Liferay} from '../../../../../liferay/liferay';
import {getSiteURL} from '../../../../../utils/site';

type LDPNextStepsProps = {
	data: ReturnType<typeof useGetProductByOrderId>['data'];
	error: ReturnType<typeof useGetProductByOrderId>['error'];
	isLoading: ReturnType<typeof useGetProductByOrderId>['isLoading'];
};

const LDPNextSteps: React.FC<LDPNextStepsProps> = ({
	data,
	error,
	isLoading,
}: LDPNextStepsProps) => {
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
						category={
							<div className="d-flex flex-column">
								<label className="align-self-start beta-badge-label p-1 text-nowrap">
									Private Beta
								</label>{' '}
								<span>{product?.catalogName as string}</span>
							</div>
						}
						logo={
							data?.marketplaceDeliveryOrder?.productThumbnail ||
							'catalog'
						}
						title={
							<div className="align-items-center text-4 d-flex">
								<span>{productName}</span>
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
									<p className="next-step-page-description">
										Your request has been successfully
										received. You will shortly receive an
										email with all the details required to
										configure your{' '}
										<strong>
											Liferay Data Platform environment
										</strong>
										. Please follow the instructions
										provided to complete your setup. If the
										email does not appear in your inbox
										within a few minutes, kindly check your
										spam folder.
									</p>
								</span>
							}
							icon={
								<span className="d-flex justify-content-center mb-4">
									<img
										alt="check circle icon"
										draggable="false"
										src={checkCircleIcon}
									/>
								</span>
							}
							title={
								<span className="d-flex justify-content-center mb-5 next-step-page-title text-center">
									{i18n.translate(
										'thank-you-for-your-purchase'
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

export default LDPNextSteps;
