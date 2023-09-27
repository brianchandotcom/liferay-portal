/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBadge from '@clayui/badge';
import {useEffect, useState} from 'react';

import {getProductById} from '../../../utils/api';

interface ProductCardProps {
	productId: number | null;
	selectedAccount?: Account;
	setProductToForm: (product: Product) => void;
	showAccount: Boolean;
}

const ProductCard = ({
	productId,
	selectedAccount,
	setProductToForm,
	showAccount,
}: ProductCardProps) => {
	const [product, setProduct] = useState<Product[]>([]);

	useEffect(() => {
		const getProdut = async () => {
			// eslint-disable-next-line promise/catch-or-return
			{
				productId &&
					getProductById({
						nestedFields: 'skus',
						productId,
					}).then((item: Product) => {
						setProduct([item]);
					});
			}
		};
		getProdut();
	}, [productId]);

	setProductToForm(product[0]);

	const currentValue = product[0];

	let setHeight;

	if (showAccount) {
		setHeight = 176;
	}
	else {
		setHeight = 112;
	}

	return (
		<div
			className="d-flex flex-column justify-content-between rounded"
			style={{backgroundColor: '#F7F7F8', height: setHeight, width: 600}}
		>
			{currentValue && (
				<div className="d-flex flex-row justify-content-between">
					<div className="align-items-start align-self-start col-8 d-flex justify-content-start">
						<div className="align-items-center align-self-center d-flex justify-content-center ml-5 mt-4">
							<img
								src={currentValue.thumbnail}
								style={{height: 64, width: 64}}
							/>
						</div>
						<div
							className="align-self-start d-flex flex-column ml-5 mt-4"
							style={{height: 64}}
						>
							<span className="text-7 ttext-weight-semi-bold">
								{Object.values(currentValue.name)}
							</span>
							<span className="text-2">
								{' '}
								{Object.values(currentValue.description)}{' '}
							</span>
						</div>
					</div>
					<div className="col-4 d-flex flex-column mr-4 mt-3">
						<span className="align-self-end d-flex text-4">
							Price
						</span>
						<span className="align-self-end d-flex text-5 text-weight-bolder"></span>
						<div className="align-self-end d-flex text-5 text-center">
							<ClayBadge displayType="secondary" label="TEST" />
						</div>
					</div>
				</div>
			)}

			{showAccount && (
				<div className="d-flex flex-column">
					<div className="align-content-center align-items-center align-self-center d-flex">
						<div
							className="card-divider"
							style={{width: 550}}
						></div>
					</div>

					<div className="d-flex justify-content-between mt-4">
						<div className="col-6 d-flex ml-3">
							<p className="text-3">
								{selectedAccount && selectedAccount.name}
							</p>
						</div>

						<div className="col-6 d-flex flex-row justify-content-between">
							<div className="col-10 d-flex flex-column mb-2">
								<span className="align-self-end d-flex mr-2 text-3"></span>
								<span className="align-self-end d-flex text-2"></span>
							</div>

							<div className="align-items-center col-2 d-flex justify-content-end">
								<span className="sticker sticker-sm sticker-user-icon">
									<span className="sticker-overlay">
										<img
											className="sticker-img"
											src="/images/thumbnail_dock.jpg"
										/>
									</span>
								</span>
							</div>
						</div>
					</div>
				</div>
			)}
		</div>
	);
};
export default ProductCard;
