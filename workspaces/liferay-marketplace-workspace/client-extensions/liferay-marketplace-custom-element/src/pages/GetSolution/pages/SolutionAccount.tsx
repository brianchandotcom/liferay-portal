/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLink from '@clayui/link';
import {Dispatch, useLayoutEffect} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import {Header} from '../../../components/Header/Header';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import RadioCardList from '../../../components/RadioCardList/RadioCardList';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';

const GetSolutionAccount = () => {
	const {
		accountForm,
		accountSelected,
		accounts,
		setSelectedAccount,
	} = useOutletContext<{
		accountForm: any;
		accountSelected?: Account;
		accounts: Account[];
		setAccounts: Dispatch<Account[]>;
		setSelectedAccount: Dispatch<Account>;
	}>();
	const navigate = useNavigate();
	const emailAddress = accountForm.watch('emailAddress');

	useLayoutEffect(() => {
		if (accountForm.accountQuantity === 1) {
			navigate('/form', {replace: true});
		}
	}, [accountForm.accountQuantity, accountForm.accountSelected, navigate]);

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
						contentList={accounts.map((account) => ({
							id: account.id,
							imageURL: account.logoURL,
							selected:
								accountSelected?.externalReferenceCode ===
								account.externalReferenceCode,
							title: account.name,
							value: account,
						}))}
						leftRadio
						onSelect={(radioOption) =>
							setSelectedAccount(radioOption.value)
						}
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
