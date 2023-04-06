import accountLogo from '../../assets/icons/mainAppLogo.svg';
import {AccountHeaderButton} from './AccountHeaderButton';

import './AccountDetailsPage.scss';

export function AccountDetailsPage() {
	return (
		<>
			<div className="account-details-container">
				<div className="account-details-header-container">
					<div className="account-details-header-left-content-container">
						<img
							alt="Account Image"
							className="account-details-header-left-content-image"
							src={accountLogo}
						/>

						<div className="account-details-header-left-content-text-container">
							<span className="account-details-header-left-content-title">
								Acme Co
							</span>

							<span className="account-details-header-left-content-description">
								Business account
							</span>
						</div>
					</div>

					<div className="account-details-header-right-container">
						<AccountHeaderButton
							boldText="4"
							text="people"
							title="Apps"
						/>

						<AccountHeaderButton
							boldText="4"
							text="Items"
							title="Members"
						/>

						<AccountHeaderButton
							boldText="4"
							text="Items"
							title="Solutions"
						/>
					</div>
				</div>
			</div>
		</>
	);
}
