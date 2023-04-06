import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';

import './AccountHeaderButton.scss';

interface AccountHeaderButtonProps {
	title: string;
	boldText: string;
	text: string;
}

export function AccountHeaderButton({
	boldText,
	text,
	title,
}: AccountHeaderButtonProps) {
	return (
		<div className="account-details-header-right-content-container">
			<span className="account-details-header-right-content-title">
				{title}
			</span>

			<ClayButton displayType="unstyled">
				<div className="account-details-header-right-content-button-container">
					<strong className="account-details-header-right-content-button-text-bold">
						{boldText}
					</strong>

					<span className="account-details-header-right-content-button-text">
						{text}
					</span>

					<ClayIcon symbol="angle-right-small" />
				</div>
			</ClayButton>
		</div>
	);
}
