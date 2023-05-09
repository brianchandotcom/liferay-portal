import brightnessEmptyIcon from '../../assets/icons/brightness_empty_icon.svg';
import scheduleIcon from '../../assets/icons/schedule_icon.svg';

export type File = {
	fileDescription: string;
	fileName: string;
	image: string;
};

export type CardInfo = {
	icon: string;
	link: string;
	title: string;
};

export type CardTag = {
	icon: string;
	tags: string[];
	title: string;
};

export type ReviewAndSubmitAppPageUtilProps = {
	cardInfos?: CardInfo[];
	cardTags?: CardTag[];
	description?: string;
	fileName?: string;
	files?: File[];
	icon?: string;
	section: string;
	tags?: string[];
	title?: string;
	version?: string;
};

export const initialReviewAndSubmitAppPageItems: ReviewAndSubmitAppPageUtilProps[] =
	[
		{
			section: 'Categories',
			tags: [
				'Experience Management',
				'Collaboration and Knowledge Sharing',
			],
		},
		{
			section: 'Tags',
			tags: [
				'CRM',
				'Employee Experience',
				'Employee Portal',
				'Knowledge Management',
			],
		},
		{
			fileName: 'a-co-libraries-01.lpkg',
			section: 'Build',
		},
		{
			icon: brightnessEmptyIcon,
			section: 'Pricing',
			title: 'Free',
		},
		{
			description: 'License never expires.',
			icon: scheduleIcon,
			section: 'Licensing',
			title: 'Perpetual License',
		},
		{
			section: 'Storefront',
		},
		{
			description:
				'Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec sed odio dui. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Cras mattison purus sit amet fermentum.',
			section: 'Version',
			title: 'Release Notes',
			version: '0.0.1',
		},
		{
			section: 'Support & Help',
		},
	];
