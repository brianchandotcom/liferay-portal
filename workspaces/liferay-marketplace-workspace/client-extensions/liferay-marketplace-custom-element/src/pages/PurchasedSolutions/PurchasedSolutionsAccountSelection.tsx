/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayRadio} from '@clayui/form';
import ClayLink from '@clayui/link';
import {useEffect, useState} from 'react';

import emptyPictureIcon from '../../assets/icons/avatar.svg';
import {Header} from '../../components/Header/Header';

import './PurchasedSolutions.scss';

import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {Liferay} from '../../liferay/liferay';
import {getOrderTypes, postOrder} from '../../utils/api';

type Steps = {
	page: 'accountCreation' | 'accountSelection' | 'projectCreated';
};

type PurchasedSolutionsccountSelectionProps = {
	accounts: Account[];
	currentUserAccount?: UserAccount;
	orderInfo?: OrderInfo;
	setStep: React.Dispatch<Steps>;
};

const productCustomFields = [
	'Github Username',
	'Project Name',
	'Site Initializer',
];

const PurchasedSolutionsAccountSelection: React.FC<PurchasedSolutionsccountSelectionProps> = ({
	accounts,
	currentUserAccount,
	orderInfo,
	setStep,
}) => {
	const [radio, setRadio] = useState<RadioOption<Account>>();
	const [orderType, setOrderType] = useState<OrderType>();
	const [disabledButton, setDisabledButton] = useState<boolean>(false);
	const {channel} = useMarketplaceContext();

	const trialLenght =
		orderInfo?.specifications &&
		orderInfo?.specifications?.find(
			(specification) =>
				specification?.specificationKey === 'trial-length'
		);

	const findOrderTypeByName = (
		orderTypes: OrderType[],
		nameOrderType = 'SOLUTIONS7'
	) => {
		return orderTypes.find(
			({externalReferenceCode}: OrderType) =>
				externalReferenceCode === nameOrderType
		);
	};

	const fetchDataAndSetState = async () => {
		const orderTypes = await getOrderTypes();

		if (!channel || !orderTypes.length) {
			setDisabledButton(true);

			return Liferay.Util.openToast({
				message:
					'We are unable to start your trial. Please contact our sales team via email - sales@liferay.com',
				type: 'danger',
			});
		}

		const projectOrderType = findOrderTypeByName(
			orderTypes,
			trialLenght?.value?.en_US as string
		);

		setOrderType(projectOrderType);
	};

	useEffect(() => {
		fetchDataAndSetState();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [trialLenght]);

	const customFields =
		orderInfo?.product?.customFields?.filter((item) =>
			productCustomFields.find((field) => item.name === field)
		) || [];

	const getProductCustomFields = () => {
		let data = {};

		productCustomFields.forEach((fieldName) => {
			customFields.forEach((field) => {
				if (field.name === fieldName) {
					data = {...data, [fieldName]: field.customValue.data};
				}
			});
		});

		return data;
	};

	const onsubmit = async () => {
		await postOrder({
			account: {
				id: Number(radio?.value?.id),
				type: radio?.value?.type as string,
			},
			accountExternalReferenceCode: radio?.value?.externalReferenceCode,
			accountId: Number(radio?.value?.id),
			channel: {
				currencyCode: channel?.currencyCode,
				id: channel?.id,
				type: channel?.type,
			},
			channelId: channel?.id,
			currencyCode: 'USD',
			customFields: getProductCustomFields(),
			orderItems: [
				{
					id: 0,
					quantity: 1,
					skuId: Number(orderInfo?.sku),
				},
			],
			orderStatus: 1,
			orderTypeExternalReferenceCode: orderType?.externalReferenceCode,
			orderTypeId: Number(orderType?.id),
			shippingAmount: 0,
			shippingWithTaxAmount: 0,
		});

		setStep({page: 'projectCreated'});
	};

	return (
		<div className="align-items-center d-flex flex-column justify-content-center purchased-solutions-container">
			<div className="border p-8 purchased-solutions-body rounded">
				<span className="d-flex justify-content-center">
					<Header description title="Account Selection" />
				</span>

				<div className="mb-4">
					<span>
						{`Accounts available for `}

						<strong>{currentUserAccount?.emailAddress}</strong>

						{` (you)`}
					</span>
				</div>

				<ClayForm>
					<ClayForm.Group>
						<div className="d-flex justify-content-between">
							<div className="form-group mb-0 pr-3 w-100">
								{accounts.map((account, index) => (
									<div
										className={classNames(
											'align-items-center d-flex form-control justify-content-between mb-5 cursor-pointer',
											{
												fieldchecked:
													radio?.index === index,
											}
										)}
										key={index}
										onClick={() =>
											setRadio({
												index,
												value: account,
											})
										}
									>
										<span className="align-items-center d-flex p-2">
											<ClaySticker
												shape="circle"
												size="lg"
											>
												<ClaySticker.Image
													alt="placeholder"
													src={
														account?.logoURL ??
														emptyPictureIcon
													}
												/>
											</ClaySticker>

											<h5 className="mb-0 ml-3">
												{account?.name}
											</h5>
										</span>

										<div className="pr-4">
											<ClayRadio
												checked={radio?.index === index}
												className="mr-5"
												onChange={() =>
													setRadio({
														index,
														value: account,
													})
												}
												type="radio"
												value="um"
											/>
										</div>
									</div>
								))}
							</div>
						</div>

						<div>
							<span>Not seeing a specific Account? </span>

							<ClayLink href="http://help.liferay.com/">
								Contact Support
							</ClayLink>
						</div>

						<div className="mt-6 purchased-solutions-button-container">
							<div className="align-items-center d-flex justify-content-between w-100">
								<div>
									<ClayButton
										className="font-weight-bold"
										displayType="unstyled"
										onClick={() => {
											setStep({
												page: 'accountCreation',
											});
										}}
									>
										Cancel
									</ClayButton>
								</div>

								<ClayButton
									disabled={
										!radio?.value ||
										disabledButton ||
										!orderInfo?.sku
									}
									onClick={() => orderInfo?.sku && onsubmit()}
								>
									Continue
								</ClayButton>
							</div>
						</div>
					</ClayForm.Group>
				</ClayForm>
			</div>
		</div>
	);
};

export default PurchasedSolutionsAccountSelection;
