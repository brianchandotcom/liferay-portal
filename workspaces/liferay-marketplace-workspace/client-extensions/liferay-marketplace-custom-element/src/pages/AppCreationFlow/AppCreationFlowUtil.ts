export type AppFlowListItemProps = {
	checked: boolean;
	label: string;
	name: string;
	selected: boolean;
};

export const initialFLowListItems: AppFlowListItemProps[] = [
	{
		checked: false,
		label: 'Create',
		name: 'create',
		selected: true,
	},
	{
		checked: false,
		label: 'Profile',
		name: 'profile',
		selected: false,
	},
	{
		checked: false,
		label: 'Build',
		name: 'build',
		selected: false,
	},
	{
		checked: false,
		label: 'Storefront',
		name: 'storefront',
		selected: false,
	},
	{
		checked: false,
		label: 'Version',
		name: 'version',
		selected: false,
	},
	{
		checked: false,
		label: 'Pricing',
		name: 'pricing',
		selected: false,
	},
	{
		checked: false,
		label: 'Licensing',
		name: 'licensing',
		selected: false,
	},
	{
		checked: false,
		label: 'Support',
		name: 'support',
		selected: false,
	},
	{
		checked: false,
		label: 'Submit',
		name: 'submit',
		selected: false,
	},
];
