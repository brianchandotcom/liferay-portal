import ClayAlert from '@clayui/alert';
import React from 'react';

export function RulesAndGuidelines() {
	return (
		<>
			<div className="create-project-modal-rules-container">
				<strong className="create-project-modal-rules-text-bold">
					This service has the following rules and guidelines:
				</strong>

				<span className="create-project-modal-rules-text">
					· Projects are automatically managed from creation to
					deletion and cleanup. <br />
					· Projects are only available for creation by approved
					publishers in the Marketplace. <br />
					· Accounts take approximately 90-180 minutes to be
					provisioned. You will be notified by email with details on
					how to access it. <br />
					· Each publisher account is allowed to have a maximum of two
					projects simultaneously. <br />
· If you need more than two,
					please contact us at <a>marketplace-admin@liferay.com</a>
				</span>
			</div>

			<ClayAlert
				displayType="warning"
				style={{
					border: 'none',
				}}
				title="Terms & Conditions for Customer Data Upload:"
			>
				If a project is shared with a customer, they must be explicitly
				informed not to upload their data. If a customer needs to upload
				data, they are required to sign an Order Form and agree to the
				Liferay Cloud Appendix 4 Terms and Conditions which includes an
				agreement to the Data Processing Addendum. Please reach out to
				your Account Executive and request them to create and have the
				customer sign a zero price Order Form.
			</ClayAlert>
		</>
	);
}
