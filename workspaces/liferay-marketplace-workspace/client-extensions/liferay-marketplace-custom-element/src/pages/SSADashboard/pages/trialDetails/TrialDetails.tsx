/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import DOMPurify from 'dompurify';
import {Link, useNavigate, useOutletContext, useParams} from 'react-router-dom';

import {DetailedCard} from '../../../../components/DetailedCard/DetailedCard';
import {PageRenderer} from '../../../../components/Page';
import QATable, {Orientation} from '../../../../components/QATable';
import {OrderCustomFields, OrderStatus} from '../../../../enums/Order';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';
import i18n from '../../../../i18n';
import {formatDate} from '../../../../utils/date';
import OrderDetailsHeader from '../../../CustomerDashboard/components/OrderDetailsHeader';
import ExtensionStatus from '../../components/ExtensionStatus/ExtensionStatus';
import TrialStatus from '../../components/TrialStatus/TrialStatus';
import TrialActions from './TrialActions';

const TrialDetails = () => {
	const navigate = useNavigate();
	const {orderId} = useParams();
	const {ssaTrialExtend, ssaTrialExtendMutate} = useOutletContext<any>();
	const {
		data,
		error,
		isLoading,
		isValidating,
		mutate: mutatePlacedOrder,
	} = useGetProductByOrderId(orderId as string);

	const placedOrder = data?.placedOrder as PlacedOrder;
	const description = data?.product.description || '';
	const placedOrderItems = placedOrder?.placedOrderItems ?? [];
	const productCreatorAccountName = data?.product?.catalogName || '';

	const placedOrderCustomFields =
		placedOrder?.customFields &&
		JSON.parse(placedOrder?.customFields[OrderCustomFields.TRIAL_SETTINGS]);
	const projectId = placedOrderCustomFields?.projectId || '';

	return (
		<PageRenderer
			className="app-details-header d-flex flex-column w-100"
			error={error}
			isLoading={isLoading && isValidating}
		>
			<Link
				className="align-items-center d-flex text-dark"
				onClick={() => navigate('..')}
				to="../"
			>
				<ClayIcon className="mr-2" symbol="order-arrow-left" />

				<span className="h4 mt-1">
					{i18n.translate('back-to-the-list')}
				</span>
			</Link>

			<div className="d-flex justify-content-between">
				<OrderDetailsHeader
					className="d-flex flex-row justify-content-between pb-3 pt-5"
					hasOrderDetails
					image={placedOrderItems[0]?.thumbnail}
					name={projectId}
					productOwner={productCreatorAccountName}
				/>

				<DropDown
					className="align-items-center cursor-pointer d-flex h-100"
					trigger={
						<ClayButton size="sm">
							{i18n.translate('manage-trial')}

							<ClayIcon
								className="ml-2"
								symbol="angle-down-small"
							/>
						</ClayButton>
					}
				>
					{data?.placedOrder && (
						<TrialActions
							mutatePlacedOrder={mutatePlacedOrder}
							placedOrder={data?.placedOrder}
							ssaTrialExtendMutate={ssaTrialExtendMutate}
						/>
					)}
				</DropDown>
			</div>

			<div className="app-details-page-container mt-6">
				<div className="app-details-body-container d-flex justify-content-between">
					<div className="col-6">
						<DetailedCard
							cardIconAltText="Profile Icon"
							cardTitle={i18n.translate('details')}
							clayIcon="order-form-tag"
						>
							<span>
								<span className="h4 mt-4 text-black-50">
									{i18n.translate('general-info')}
								</span>
								<hr className="my-0" />

								<QATable
									items={[
										{
											title: i18n.translate(
												'account-name'
											),

											value: (
												<div className="mb-3">
													{placedOrder?.account}
												</div>
											),
										},
										{
											title: i18n.translate('created-by'),
											value: (
												<div className="mb-3">
													{placedOrder?.author}
												</div>
											),
										},
										{
											title: i18n.translate(
												'license-type'
											),
											value: (
												<div className="mb-3">
													{placedOrder?.orderType}
												</div>
											),
										},
									]}
									orientation={Orientation.VERTICAL}
								/>
							</span>

							<span>
								<span className="h4 mt-4 text-black-50">
									{i18n.translate('order-info')}
								</span>
								<hr className="my-0" />

								<QATable
									items={[
										{
											title: i18n.translate('order-id'),
											value: (
												<div className="mb-3">
													{placedOrder?.id}
												</div>
											),
										},
										{
											title: i18n.translate('order-date'),
											value: (
												<div>
													{placedOrder?.createDate &&
														formatDate(
															placedOrder?.createDate as string
														)}
												</div>
											),
										},
									]}
									orientation={Orientation.VERTICAL}
								/>
							</span>
						</DetailedCard>
					</div>

					<div className="col-6">
						<DetailedCard
							cardIconAltText="Profile Icon"
							cardTitle={i18n.translate('ssa-trial-summary')}
							clayIcon="date-time"
						>
							<span>
								<span className="h4 mt-4 text-black-50">
									{i18n.translate('trial-info')}
								</span>
								<hr className="my-0" />

								<QATable
									items={[
										{
											title: i18n.translate(
												'trial-start-date'
											),
											value: (
												<div className="mb-3">
													{placedOrder?.customFields[
														'trial-start-date'
													] &&
														formatDate(
															placedOrder
																?.customFields[
																'trial-start-date'
															] as string
														)}
												</div>
											),
										},
										{
											title: i18n.translate(
												'trial-end-date'
											),
											value: (
												<div className="mb-3">
													{placedOrder &&
														formatDate(
															placedOrder
																?.customFields[
																'trial-end-date'
															] as string
														)}
												</div>
											),
										},
										{
											title: i18n.translate(
												'trial-status'
											),
											value: (
												<div className="mb-3">
													<TrialStatus
														trialStatus={
															placedOrder
																?.orderStatusInfo
																?.label as string
														}
													/>
												</div>
											),
										},
										{
											title: i18n.translate(
												'extension-status'
											),
											value: (() => {
												return (
													<div className="my-3">
														<ExtensionStatus
															extensionStatus={
																placedOrder
																	?.orderStatusInfo
																	?.label ===
																OrderStatus.COMPLETED
																	? 'extension-expired'
																	: ssaTrialExtend?.items?.find(
																			({
																				projectId,
																			}: {
																				projectId: string;
																			}) =>
																				projectId ===
																				placedOrderCustomFields?.projectId
																		)
																			?.dueStatus
																			?.key
															}
														/>
													</div>
												);
											})(),
										},
									]}
									orientation={Orientation.VERTICAL}
								/>
							</span>

							<span>
								<span className="h4 mt-4 text-black-50">
									{i18n.translate('description')}
								</span>
								<hr className="my-0" />

								<p
									className="app-review-section-body-description-paragraph mt-3"
									dangerouslySetInnerHTML={{
										__html: DOMPurify.sanitize(description),
									}}
								/>
							</span>
						</DetailedCard>
					</div>
				</div>
			</div>
		</PageRenderer>
	);
};
export default TrialDetails;
