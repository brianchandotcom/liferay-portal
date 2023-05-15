import {useEffect} from 'react';

import {getAccountInfoFromCommerce} from '../../utils/api';
import {showAccountImage} from '../../utils/util';
import {RadioCard} from '../RadioCard/RadioCard';

import './AccountSelector.scss';

export function AccountSelector({
	accounts,
	activeAccounts,
	selectedAccount,
	setActiveAccounts,
	setSelectedAccount,
	userEmail,
}: {
	accounts: AccountBrief[];
	activeAccounts: AccountBrief[];
	selectedAccount?: Partial<AccountBrief>;
	setActiveAccounts: (value: Partial<AccountBrief>[]) => void;
	setSelectedAccount: (value: Partial<AccountBrief>) => void;
	userEmail: string;
}) {
	useEffect(() => {
		const getAccountInfos = async () => {
			const accountInfo = await Promise.all(
				accounts?.map(async (account) => {
					const accountInfo = await getAccountInfoFromCommerce(
						account.id
					);

					return accountInfo;
				})
			);

			const filteredAccounts: CommerceAccount[] = accountInfo.filter(
				(account) => account.active
			);

			if (accounts.length === 1) {
				setSelectedAccount(filteredAccounts[0]);
			}

			setActiveAccounts(filteredAccounts);
		};

		getAccountInfos();
	}, [accounts, setActiveAccounts, setSelectedAccount]);

	return (
		<div className="account-selector">
			<span>
				Accounts available for <b>{userEmail}</b>&nbsp;(you){' '}
			</span>

			<div className="account-selector-cards">
				{activeAccounts?.map((account) => {
					return (
						<RadioCard
							icon={showAccountImage(account.logoURL)}
							onChange={() => {
								setSelectedAccount(account);
							}}
							position="right"
							selected={selectedAccount?.name === account.name}
							title={account.name}
						/>
					);
				})}
			</div>

			<span className="account-selector-contact-support">
				Not seeing a specific Account? <a href="#">Contact Support</a>
			</span>
		</div>
	);
}
