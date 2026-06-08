/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {Text} from '@clayui/core';
import ClayEmptyState from '@clayui/empty-state';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React, {useState} from 'react';
import {useParams} from 'react-router-dom';
import Card from '~/shared/components/Card';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';
import {toThousands} from '~/shared/util/numbers';

import AccountDetailsModal from './AccountDetailsModal';

export interface IAccount {
	accountName?: string;
	accountType?: string;
	annualRevenue?: number;
	fields?: Array<{
		dataSourceId?: string;
		dataSourceName?: string;
		name: string;
		value?: string;
	}>;
	industry?: string;
	numberOfEmployees?: number;
	website?: string;
}

interface IAccountInfoProps {
	account?: IAccount;
	className?: string;
	loading?: boolean;
}

const infoDataLabels = {
	accountType: Liferay.Language.get('account-type'),
	annualRevenue: Liferay.Language.get('annual-revenue'),
	industry: Liferay.Language.get('industry'),
	numberOfEmployees: Liferay.Language.get('company-size'),
	website: Liferay.Language.get('website'),
};

const normalizeHref = (value: string) =>
	/^https?:\/\//i.test(value) ? value : `https://${value}`;

const infoItem = (label: string, value?: string, link?: boolean) => (
	<div className="d-flex justify-content-between mb-2">
		<Text color="secondary" size={3}>
			{label}
		</Text>
		{link && value ? (
			<ClayLink href={normalizeHref(value)} target="_blank">
				<Text size={3}>{value}</Text>
			</ClayLink>
		) : (
			<Text color="secondary" size={3}>
				{value}
			</Text>
		)}
	</div>
);

const AccountInfo: React.FC<IAccountInfoProps> = ({
	account,
	className,
	loading,
}) => {
	const [isDetailsModalOpen, setIsDetailsModalOpen] = useState(false);
	const {id: accountId} = useParams<{id: string}>();

	const getValue = (key: keyof typeof infoDataLabels): string | undefined => {
		if (!account) {
			return undefined;
		}
		if (key === 'annualRevenue') {
			return account.annualRevenue !== undefined
				? toThousands(account.annualRevenue)
				: undefined;
		}
		if (key === 'numberOfEmployees') {
			return account.numberOfEmployees !== undefined
				? toThousands(account.numberOfEmployees)
				: undefined;
		}

		return account[key];
	};

	const infoKeys = Object.keys(infoDataLabels) as Array<
		keyof typeof infoDataLabels
	>;
	const isEmpty = !loading && infoKeys.every((key) => !getValue(key));

	return (
		<>
			<Card className={classNames(className, 'p-3')} minHeight={260}>
				<Card.Title>
					<Text size={4} weight="semi-bold">
						<span className="text-uppercase">
							{Liferay.Language.get(
								'general-account-information'
							)}
						</span>
					</Text>
				</Card.Title>
				<Card.Body
					alignCenter={loading || isEmpty}
					className={classNames('mt-4', {
						'justify-content-end p-0': !loading && !isEmpty,
					})}
				>
					<StatesRenderer empty={isEmpty} loading={loading}>
						<StatesRenderer.Loading />
						<StatesRenderer.Empty>
							<ClayEmptyState
								className="mt-n5 text-center"
								description={Liferay.Language.get(
									'account-attributes-will-appear-here-when-available'
								)}
								small
								title={Liferay.Language.get(
									'no-account-attributes-available'
								)}
							/>
						</StatesRenderer.Empty>
						<StatesRenderer.Success>
							{infoKeys.map((key) => (
								<React.Fragment key={key}>
									{infoItem(
										infoDataLabels[key],
										getValue(key),
										key === 'website'
									)}
								</React.Fragment>
							))}
							<Button
								borderless
								className="ml-auto rounded-lg"
								onClick={() => setIsDetailsModalOpen(true)}
								size="sm"
							>
								{Liferay.Language.get('view-all')}
							</Button>
						</StatesRenderer.Success>
					</StatesRenderer>
				</Card.Body>
			</Card>

			{isDetailsModalOpen && accountId && (
				<AccountDetailsModal
					accountId={accountId}
					accountName={account?.accountName}
					onClose={() => setIsDetailsModalOpen(false)}
				/>
			)}
		</>
	);
};

export default AccountInfo;
