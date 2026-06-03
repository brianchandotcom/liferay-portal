interface PendoAPI {
	account: {
		id?: string; // Highly recommended, required if using Pendo Feedback
		name: string;
		// eslint-disable-next-line camelcase
		is_paying?: boolean; // Recommended if using Pendo Feedback
		// eslint-disable-next-line camelcase
		monthly_value?: boolean; // Recommended if using Pendo Feedback
		planLevel: string;
		planPrice?: string;
		creationDate?: string;
		// You can add any additional account level key-values here,
		// as long as it's not one of the above reserved names.
	};
	visitor: {
		id: string; // Required if user is logged in
		email: string; // Recommended if using Pendo Feedback, or NPS Email
		// eslint-disable-next-line camelcase
		full_name: string; // Recommended if using Pendo Feedback
		role: string;
		// You can add any additional visitor level key-values here,
		// as long as it's not one of the above reserved names.
	};
}

declare const pendo: {
	identify: (data: PendoAPI) => void;
	initialize: (data: PendoAPI) => void;
	isReady?: () => boolean;
};
