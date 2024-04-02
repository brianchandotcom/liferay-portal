/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLink from '@clayui/link';
import {useEffect, useLayoutEffect, useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import {Header} from '../../../components/Header/Header';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import RadioCardList, {
	RadioCardContent,
} from '../../../components/RadioCardList/RadioCardList';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';

const GetSolutionAccount = () => {
	const {accountForm} = useOutletContext<any>();
	const navigate = useNavigate();
	const accountSelected = accountForm.watch('accountSelected');
	const emailAddress = accountForm.watch('emailAddress');

	useLayoutEffect(() => {
		if (accountForm.accountQuantity === 1) {
			navigate('/form', {replace: true});
		}
	}, [accountForm.accountQuantity, accountForm.accountSelected, navigate]);

	const [accounts, setAccounts] = useState<RadioCardContent<Account>[]>([]);

	useEffect(() => {
		setAccounts(
			accountForm.accounts.map((account: Account) => ({
				id: account.id,
				imageURL: account.logoURL,
				selected:
					accountSelected?.externalReferenceCode ===
					account.externalReferenceCode,
				title: account.name,
				value: account,
			}))
		);
	}, [accountForm.accounts, accountSelected?.externalReferenceCode]);

	const handleSelectAccount = (radioOption: RadioOption<Account>) => {
		accountForm.setValue('accountSelected', radioOption.value);

		setAccounts((previousValue) =>
			previousValue.map((account, index) => ({
				...account,
				selected: index === radioOption.index,
			}))
		);
	};

	const handleNextStep = () => navigate('form');

	return (
		<div>
			<span className="d-flex justify-content-center">
				<Header title="Account Selection" />
			</span>

			<p className="mb-4 secondary-text">
				{`Accounts available for `}

				<strong>{emailAddress}</strong>

				{` (you)`}
			</p>

			<ClayForm className="mt-4">
				<ClayForm.Group>
					<RadioCardList
						contentList={accounts}
						leftRadio
						onSelect={handleSelectAccount}
						showImage
					/>

					<span className="mr-1 secondary-text">
						Not seeing a specific Account?
					</span>

					<ClayLink
						className="font-weight-bold"
						href="http://help.liferay.com/"
					>
						Contact Support
					</ClayLink>

					<div className="align-items-center d-flex justify-content-between mt-6 w-100">
						<ClayButton
							className="font-weight-bold"
							displayType="unstyled"
							onClick={() =>
								Liferay.Util.navigate(
									`${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/solutions-marketplace`
								)
							}
						>
							{i18n.translate('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!accountSelected}
							onClick={handleNextStep}
						>
							{i18n.translate('continue')}
						</ClayButton>
					</div>
				</ClayForm.Group>
			</ClayForm>
		</div>
	);
};

export default GetSolutionAccount;
