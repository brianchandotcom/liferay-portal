/* eslint-disable @typescript-eslint/no-unused-vars */
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

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {useState} from 'react';

import emptyPictureIcon from '../../assets/icons/avatar.svg';
import {Header} from '../../components/Header/Header';

import './PurchasedGetAppPage.scss';

import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';

type Steps = {
	page: 'accountCreation' | 'accountSelection' | 'projectCreated';
};

type PurchasedGetAppAccountSelectionProps = {
	currentUserAccount?: UserAccount;
	orderInfo?: any;
	setStep: React.Dispatch<Steps>;
};

const PurchasedGetAppAccountSelection: React.FC<
	PurchasedGetAppAccountSelectionProps
> = ({currentUserAccount, orderInfo, setStep}) => {
	const [radio, setRadio] = useState<any>();

	const onsubmit = () => {
		const payload = {
			accountId: orderInfo?.accountId,
			billingAddress: orderInfo?.userAddress,
			channelId: orderInfo?.channelId,
			currencyCode: 'USD',
			orderItems: [
				{
					id: 0,
					quantity: 1,
					skuId: orderInfo?.skuId,
				},
			],
			orderStatus: 2,
			shippingAddress: orderInfo?.userAddress,
			shippingAmount: 0,
			shippingWithTaxAmount: 0,
		};

		return alert(JSON.stringify(payload));
	};

	return (
		<div className="align-items-center d-flex flex-column justify-content-center purchased-get-app-page-container">
			<div className="border p-8 purchased-get-app-page-body rounded">
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
								{currentUserAccount?.accountBriefs?.map(
									(account, index) => (
										<div
											className={classNames(
												'align-items-center d-flex form-control justify-content-between mb-5 cursor-pointer',
												{
													testchecked:
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
															account.logoURL ??
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
													checked={
														radio?.index === index
													}
													className="mr-5"
													type="radio"
													value="um"
												/>
											</div>
										</div>
									)
								)}
							</div>
						</div>

						<div>
							<span>Not seeing a specific Account? </span>

							<ClayLink>Account? Contact Support</ClayLink>
						</div>

						<div className="mt-6 purchased-get-app-page-button-container">
							<div className="align-items-center d-flex justify-content-between w-100">
								<div>
									<ClayButton
										className="font-weight-bold"
										displayType="unstyled"
										onClick={() => {
											setStep({page: 'accountCreation'});
										}}
									>
										Cancel
									</ClayButton>
								</div>

								<ClayButton
									disabled={!radio?.value}
									onClick={() => onsubmit()}
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

export default PurchasedGetAppAccountSelection;
