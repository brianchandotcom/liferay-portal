/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLink from '@clayui/link';

import {Header} from '../../components/Header/Header';

import './PurchasedSolutions.scss';

import {useState} from 'react';

import {getSiteURL} from '../../components/InviteMemberModal/services';
import RadioCardList, {
	RadioCardContent,
} from '../../components/RadioCardList/RadioCardList';
import {Liferay} from '../../liferay/liferay';
import {StepType} from './PurchasedSolutions';
import useAccountForm from './hooks/useAccountForm';

type AccountSelectionProps = {
	accountForm: ReturnType<typeof useAccountForm>;
	onSubmit: (responeAccount?: Account) => Promise<void>;
	setStep: React.Dispatch<React.SetStateAction<StepType>>;
};

const AccountSelection: React.FC<AccountSelectionProps> = ({
	accountForm,
	onSubmit,
	setStep,
}) => {
	const accountSelected = accountForm.watch('accountSelected');
	const emailAddress = accountForm.watch('emailAddress');
	const [accounts, setAccounts] = useState<RadioCardContent<Account>[]>(
		() => {
			return accountForm.accounts.map((account: Account) => ({
				imageURL: account.logoURL,
				selected:
					accountSelected?.externalReferenceCode ===
					account.externalReferenceCode,
				title: account.name,
				value: account,
			}));
		}
	);

	const handleSelectAccount = (radioOption: RadioOption<Account>) => {
		accountForm.setValue('accountSelected', radioOption.value);

		setAccounts((previousValue) =>
			previousValue.map((account, index) => ({
				...account,
				selected: index === radioOption.index,
			}))
		);
	};

	const handleNextStep = async () => {
		await onSubmit();

		setStep(StepType.CHECKOUT);
	};

	return (
		<div>
			<span className="d-flex justify-content-center">
				<Header description title="Account Selection" />
			</span>

			<div className="mb-4">
				<span>
					{`Accounts available for `}

					<strong>{emailAddress}</strong>

					{` (you)`}
				</span>
			</div>

			<ClayForm>
				<ClayForm.Group>
					<div className="d-flex justify-content-between">
						<div className="form-group mb-0 pr-3 w-100">
							<RadioCardList
								contentList={accounts}
								leftRadio
								onSelect={handleSelectAccount}
								showImage
							/>
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
										window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/solutions-marketplace`;
									}}
								>
									Cancel
								</ClayButton>
							</div>

							<div>
								<ClayButton
									className="mr-4"
									displayType="secondary"
									onClick={() => {
										setStep(StepType.FORM);
									}}
								>
									Back
								</ClayButton>
								<ClayButton
									disabled={!accountSelected}
									onClick={handleNextStep}
								>
									Continue
								</ClayButton>
							</div>
						</div>
					</div>
				</ClayForm.Group>
			</ClayForm>
		</div>
	);
};

export default AccountSelection;
