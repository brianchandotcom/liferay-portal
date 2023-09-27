/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {InputHTMLAttributes, useEffect, useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import {Header} from '../../components/Header/Header';
import BaseWrapper from '../../components/Input/base/BaseWrapper';
import zodSchema, {zodResolver} from '../../schema/zod';
import {
	getAccountInfo,
	getListTypeDefinitionByExternalReferenceCode,
	getProductById,
	getProductSKU,
	getProductSpecifications,
	getUserAccount,
	postAccountByERCUserAccountByERC,
} from '../../utils/api';

import './PurchasedSolutions.scss';

import ClayAlert, {DisplayType} from '@clayui/alert';
import ClaySticker from '@clayui/sticker';

import emptyPictureIcon from '../../assets/icons/avatar.svg';
import {getSiteURL} from '../../components/InviteMemberModal/services';
import Select from '../../components/Select/Select';
import {Liferay} from '../../liferay/liferay';
import fetcher from '../../services/fetcher';
import CreatedProjectCard from './CreatedProjectCard';
import PurchasedSolutionsAccountSelection from './PurchasedSolutionsAccountSelection';
import {getPhones} from './PurchasedSolutionsUtil';

type Steps = {
	page:
		| 'accountCreation'
		| 'accountSelection'
		| 'initialStep'
		| 'projectCreated';
};

const accountTypes = {
	BUSINESS: 'business',
	PERSON: 'person',
};

export type UserForm = z.infer<typeof zodSchema.accountCreator>;

type InputProps = {
	boldLabel?: boolean;
	className?: string;
	description?: string;
	disabled?: boolean;
	errors?: any;
	id?: string;
	label?: string;
	name: string;
	options?: {label: string; value: string} | [];
	register?: any;
	required?: boolean;
	type?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const externalReferenceCode = 'INDUSTRIES';

const Input: React.FC<InputProps> = ({
	boldLabel,
	className,
	description,
	disabled = false,
	errors = {},
	label,
	name,
	register = () => {},
	id = name,
	type,
	value,
	required = false,
	onBlur,
	...otherProps
}) => {
	return (
		<BaseWrapper
			boldLabel={boldLabel}
			description={description}
			disabled={disabled}
			error={errors[name]?.message}
			id={id}
			label={label}
			required={required}
		>
			<ClayInput
				className={`rounded-xs ${className}`}
				component={type === 'textarea' ? 'textarea' : 'input'}
				disabled={disabled}
				id={id}
				name={name}
				type={type}
				value={value}
				{...otherProps}
				{...register(name, {onBlur, required})}
			/>
		</BaseWrapper>
	);
};

const PurchasedSolutions: React.FC = () => {
	const queryString = window.location.search;

	const urlParams = new URLSearchParams(queryString);

	const productId = Number(urlParams.get('productId')) + 1;

	const [step, setStep] = useState<Steps>({page: 'initialStep'});
	const [phonesFlags, setPhonesFlags] = useState<PhonesFlags[]>();
	const [currentPhonesFlags, setCurrentPhonesFlags] = useState({
		code: '+1',
		flag: 'en-us',
	});
	const [product, setProduct] = useState<Product>();
	const [sku, setSku] = useState<number>();
	const [currentUserAccount, setCurrentUserAccount] = useState<UserAccount>();
	const [industries, setIndustries] = useState<Industries[]>();
	const [accounts, setAccounts] = useState<Account[]>([]);
	const [order, setOrder] = useState<OrderInfo>();
	const [disabledButton, setDisabledButton] = useState<boolean>(false);
	const [toastItems, setToastItems] = useState<
		{message: string; title?: string; type: DisplayType}[]
	>([]);
	const [specifications, setSpecifications] = useState<
		ProductSpecification[]
	>();

	const renderToast = (message: string, title: string, type: DisplayType) => {
		setToastItems([...toastItems, {message, title, type}]);
	};

	useEffect(() => {
		(async () => {
			setCurrentUserAccount(await getUserAccount());

			const insdustriesListTypeEntries = await getListTypeDefinitionByExternalReferenceCode(
				externalReferenceCode
			);

			setIndustries(insdustriesListTypeEntries?.listTypeEntries);

			const skuProduct = await getProductSKU({
				appProductId: Number(productId),
			});

			const specifications = await getProductSpecifications({
				appProductId: productId,
			});

			setSpecifications(specifications);

			if (!skuProduct.items[0] || productId === 1 || productId === null) {
				setDisabledButton(true);
				renderToast(
					`We are unable to start your trial. Please contact our sales team via email - sales@liferay.com`,
					'',
					'danger'
				);
			}
			else {
				const productById = await getProductById({
					productId: Number(productId),
				});
				setSku(skuProduct.items[0].id);
				setProduct(productById);
			}
		})();

		const flags = getPhones();

		setPhonesFlags(flags);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [productId]);

	const accountBriefs = useMemo(
		() => currentUserAccount?.accountBriefs || [],
		[currentUserAccount?.accountBriefs]
	);

	useEffect(() => {
		const fetchAccount = async () => {
			const fetchedAccounts = [];

			for (const accountBrief of accountBriefs) {
				const accountInfo = await getAccountInfo({
					accountId: Number(accountBrief.id),
				});
				fetchedAccounts.push(accountInfo);
			}

			return fetchedAccounts;
		};

		(async () => {
			const accounts = await fetchAccount();

			setAccounts(accounts);

			const hasPersonAccount = accounts.some(
				(account) => account.type === accountTypes.PERSON
			);

			const getAccountInfo = () => {
				let accountInfo;

				for (const account of accounts) {
					if (account.type === 'person') {
						accountInfo = account;
					}
				}

				return accountInfo;
			};

			const account = getAccountInfo();
			setOrder({account, product, sku, specifications});

			const pageDefault = hasPersonAccount
				? 'accountSelection'
				: 'accountCreation';
			setStep({page: pageDefault});
		})();
	}, [accountBriefs, product, sku, specifications]);

	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<UserForm>({
		defaultValues: {
			agreeToTermsAndConditions: false,
			companyName: '',
			emailAddress: '',
			extension: '',
			familyName: '',
			givenName: '',
			industry: '',
			phone: {code: '+1', flag: 'en-us'},
			phoneNumber: undefined,
		},
		resolver: zodResolver(zodSchema.accountCreator),
	});

	useEffect(() => {
		if (currentUserAccount) {
			const {emailAddress, familyName, givenName} = currentUserAccount;
			setValue('emailAddress', emailAddress || '');
			setValue('givenName', givenName || '');
			setValue('familyName', familyName || '');
		}
	}, [currentUserAccount, setValue]);

	const addUserAccountInAccount = async (data: Account) => {
		if (currentUserAccount) {
			await postAccountByERCUserAccountByERC(
				data?.externalReferenceCode,
				currentUserAccount.externalReferenceCode
			);

			setCurrentUserAccount(await getUserAccount());
			setStep({page: 'accountSelection'});
		}
	};

	const _submit = async (form: UserForm) => {
		const response: Account = await fetcher(`/accounts`, {
			body: JSON.stringify({
				company: form.companyName,
				customFields: [
					{
						customValue: {
							data: form.industry,
						},
						name: 'Industry',
					},
					{
						customValue: {
							data: `${form.phone.code} ${form.phoneNumber} ${form.extension}`,
						},
						name: 'Contact Phone',
					},
					{
						customValue: {
							data: `${form.emailAddress}`,
						},
						name: 'Contact Email',
					},
				],
				externalReferenceCode: `ACCOUNT${form.givenName}${form.familyName}`,
				name: `${form.givenName} ${form.familyName}`,
				type: accountTypes.PERSON,
			}),
			method: 'POST',
		})
			.then(async (response) => {
				return response;
			})

			.catch((error) => console.error(error));

		await addUserAccountInAccount(response);

		setOrder({account: form, product, sku, specifications});
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const agreeToTermsAndConditions = watch('agreeToTermsAndConditions');

	return (
		<>
			<div className="align-items-center d-flex flex-column justify-content-center purchased-solutions">
				{step.page !== 'projectCreated' && (
					<div className="product-card">
						<div className="mr-5">
							{!product ? (
								<img alt="Circle Icon" src={emptyPictureIcon} />
							) : (
								<ClaySticker size="xl">
									<ClaySticker.Image
										alt="placeholder"
										src={product?.thumbnail}
									/>
								</ClaySticker>
							)}
						</div>

						<h2 className="mb-0">
							<span className="mr-2">{product?.name?.en_US}</span>

							<span>
								<ClayLink className="font-weight-bold">
									Trial
								</ClayLink>
							</span>
						</h2>
					</div>
				)}

				<div>
					{step?.page === 'accountCreation' && (
						<div className="align-items-center d-flex flex-column justify-content-center purchased-solutions-container">
							<div className="border p-8 purchased-solutions-body rounded">
								<Header
									description
									title="Marketplace Account Creation"
								/>

								<ClayForm>
									<div className="align-items-baseline d-flex">
										<div className="align-items-center d-flex">
											<label className="font-weight-bold mr-4 title-label">
												Profile Info
											</label>
										</div>
									</div>

									<hr className="solid" />

									<ClayForm.Group>
										<div className="d-flex justify-content-between">
											<div className="form-group mb-0 pr-3 w-50">
												<Input
													{...inputProps}
													boldLabel
													disabled
													label="First Name"
													name="givenName"
												/>
											</div>

											<div className="form-group mb-0 pl-3 w-50">
												<Input
													{...inputProps}
													boldLabel
													disabled
													label="Last Name"
													name="familyName"
												/>
											</div>
										</div>

										<div className="form-group mb-5">
											<Input
												{...inputProps}
												boldLabel
												label="Company name"
												name="companyName"
												placeholder="Enter company name"
											/>
										</div>

										<div className="form-group mb-5">
											<Select
												{...inputProps}
												boldLabel
												className="p-2"
												defaultOption
												defaultOptionLabel="Select an industry"
												label="Industry"
												name="industry"
												options={industries}
												placeholder="Select an industry"
											/>
										</div>

										<ClayForm.Group>
											<div className="align-items-baseline d-flex">
												<div className="align-items-center d-flex">
													<label
														className="font-weight-bold mr-4 title-label"
														htmlFor="emailAddress"
													>
														Contact Info
													</label>
												</div>
											</div>

											<hr className="solid" />

											<div className="form-group mb-5">
												<Input
													{...inputProps}
													boldLabel
													disabled
													label="Email"
													name="emailAddress"
													type="email"
												/>
											</div>

											<label
												className="required"
												htmlFor="phone"
											>
												Phone
											</label>

											<div className="d-flex justify-content-between purchased-solutions-phone">
												<div className="col-3 pl-0">
													<DropDown
														closeOnClick
														trigger={
															<div className="align-items-center custom-select d-flex form-control p-2 rounded-xs">
																<ClayIcon
																	className="mr-2"
																	symbol={
																		currentPhonesFlags.flag
																	}
																/>

																{
																	currentPhonesFlags.code
																}
															</div>
														}
													>
														<DropDown.ItemList
															items={phonesFlags}
														>
															{(item) => {
																const itemList = item as PhonesFlags;

																return (
																	<DropDown.Item
																		onClick={() => {
																			setCurrentPhonesFlags(
																				{
																					code:
																						itemList.code,
																					flag:
																						itemList.flag,
																				}
																			);

																			setValue(
																				'phone',
																				{
																					code:
																						itemList.code,
																					flag:
																						itemList.flag,
																				}
																			);
																		}}
																	>
																		<ClayIcon
																			className="mr-2"
																			symbol={
																				itemList.flag
																			}
																		/>

																		{
																			itemList.code
																		}
																	</DropDown.Item>
																);
															}}
														</DropDown.ItemList>
													</DropDown>

													<div className="form-feedback-group">
														<div className="form-text">
															Intl. code
														</div>
													</div>
												</div>

												<div className="col-6">
													<Input
														{...inputProps}
														className="w-100"
														description="Phone number"
														name="phoneNumber"
														placeholder="___–___–____"
													/>
												</div>

												<div className="col-3">
													<Input
														{...inputProps}
														className="mr-0 pl-1"
														description="Extension (optional)"
														name="extension"
														placeholder="Enter +ext"
													/>
												</div>
											</div>
										</ClayForm.Group>

										<ClayForm.Group>
											<div className="d-flex flex-row-reverse justify-content-end">
												<label
													className="control-label ml-3 pb-1"
													htmlFor="agreeToTermsAndConditions"
												>
													I agree to the
													<ClayLink href="https://www.liferay.com/en/legal/marketplace-terms-of-service">
														Terms & Conditions
													</ClayLink>
												</label>

												<ClayCheckbox
													checked={
														agreeToTermsAndConditions
													}
													className="danger"
													id="newsSubscription"
													onChange={() =>
														setValue(
															'agreeToTermsAndConditions',
															!agreeToTermsAndConditions
														)
													}
												/>
											</div>
										</ClayForm.Group>

										<div className="purchased-solutions-button-container">
											<div className="align-items-center d-flex justify-content-between mb-4 w-100">
												<div>
													<ClayButton
														displayType="unstyled"
														onClick={() => {
															window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/solutions-marketplace`;
														}}
													>
														Cancel
													</ClayButton>
												</div>

												<ClayButton
													disabled={
														!agreeToTermsAndConditions ||
														disabledButton
													}
													onClick={handleSubmit(
														_submit
													)}
												>
													Continue
												</ClayButton>
											</div>
										</div>
									</ClayForm.Group>
								</ClayForm>
							</div>
						</div>
					)}

					{step?.page === 'accountSelection' && (
						<PurchasedSolutionsAccountSelection
							accounts={accounts}
							currentUserAccount={currentUserAccount}
							orderInfo={order}
							setStep={setStep}
						/>
					)}

					{step?.page === 'projectCreated' && (
						<CreatedProjectCard product={product} />
					)}
				</div>
			</div>
			<ClayAlert.ToastContainer>
				{toastItems?.map((alert, index) => (
					<ClayAlert
						autoClose={5000}
						displayType={alert.type}
						key={index}
						onClose={() => {
							setToastItems((prevItems) =>
								prevItems.filter((item) => item !== alert)
							);
						}}
						title={alert.title}
					>
						{alert.message}
					</ClayAlert>
				))}
			</ClayAlert.ToastContainer>
		</>
	);
};

export default PurchasedSolutions;
