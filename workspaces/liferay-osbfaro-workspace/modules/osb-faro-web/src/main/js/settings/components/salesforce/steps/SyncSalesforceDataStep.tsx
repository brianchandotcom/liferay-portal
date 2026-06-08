/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayForm from '@clayui/form';
import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import {WizardPageButtonGroup} from '~/settings/components/base-page/WizardPageButtonGroup';
import SalesforceAccountsAndIndividuals from '~/settings/components/salesforce/SalesforceAccountsAndIndividuals';
import {addAlert} from '~/shared/actions/alerts';
import {updateSalesforce} from '~/shared/api/data-source';
import {Alert} from '~/shared/types';

import {useWizardPage} from '../../base-page/WizardPageContext';

interface ISyncSalesforceDataStepProps {
	onNext: () => void;
	onPrev: () => void;
}

const SyncSalesforceDataStep = ({
	onNext,
	onPrev,
}: ISyncSalesforceDataStepProps) => {
	const [loading, setLoading] = useState(false);
	const {dataSource} = useWizardPage();
	const {groupId = ''} = useParams<{groupId: string}>();
	const [enabledAccount, setEnabledAccount] = useState(false);
	const [enabledIndividual, setEnabledIndividual] = useState(false);

	useEffect(() => {
		if (dataSource) {
			const accounts = dataSource.provider?.getIn([
				'accountsConfiguration',
				'enableAllAccounts',
			]);

			const contactsConfiguration = dataSource.provider?.get(
				'contactsConfiguration'
			);

			const individuals =
				contactsConfiguration?.get('enableAllContacts') &&
				contactsConfiguration?.get('enableAllLeads');

			setEnabledAccount(accounts);
			setEnabledIndividual(individuals);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<ClayForm
			onSubmit={async (event) => {
				event.preventDefault();

				if (!dataSource) {
					return;
				}

				try {
					setLoading(true);

					await updateSalesforce({
						accountsConfiguration: {
							enableAllAccounts: enabledAccount,
						},
						contactsConfiguration: {
							enableAllContacts: enabledIndividual,
							enableAllLeads: enabledIndividual,
						},
						groupId,
						id: dataSource.id,
					} as any);
				}
				catch (error) {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get(
							'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
						),
					});
				}
				finally {
					setLoading(false);

					onNext();
				}
			}}
		>
			<div className="mb-2">
				<Text size={2} weight="semi-bold">
					{Liferay.Language.get('connection-status').toUpperCase()}
				</Text>
			</div>

			{dataSource && (
				<SalesforceAccountsAndIndividuals
					enabledAccount={enabledAccount}
					enabledIndividual={enabledIndividual}
					onAccountChange={() => setEnabledAccount(!enabledAccount)}
					onIndividualChange={() =>
						setEnabledIndividual(!enabledIndividual)
					}
					type="checkbox"
				/>
			)}

			<WizardPageButtonGroup
				nextButtonLabel={Liferay.Language.get('continue')}
				nextButtonLoading={loading}
				onCancel={onPrev}
				prevButtonLabel={Liferay.Language.get('previous')}
			/>
		</ClayForm>
	);
};

export {SyncSalesforceDataStep};
