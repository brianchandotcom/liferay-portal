import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useState} from 'react';

import checkFillIcon from '../../assets/icons/check_fill.svg';
import circleFillIcon from '../../assets/icons/circle_fill.svg';
import {
	createAppSKU,
	getCatalogs,
	getCategoriesRanked,
	getChannels,
	getOrderbyERC,
	patchOrderByERC,
	postCartByChannelId,
	postCheckoutCart,
	postProduct,
} from '../../utils/api';
import {getCustomFieldValue} from '../../utils/customFieldUtil';
import {ProjectDetails} from './ProjectDetails';
import {RulesAndGuidelines} from './RulesAndGuidelines';

import './CreateProjectModal.scss';
interface CreateProjectModalProps {
	handleClose: () => void;
	selectedAccount: Account;
	setShowDashboardNavigation: (value: boolean) => void;
	setShowNextStepsPage: (value: boolean) => void;
}

const multiStepItemsInitialValues = [
	{
		label: 'Rules & Guidelines',
		selected: true,
		completed: false,
	},
	{
		label: 'Project Details',
		selected: false,
		completed: false,
	},
];

export function CreateProjectModal({
	handleClose,
	selectedAccount,
	setShowDashboardNavigation,
	setShowNextStepsPage,
}: CreateProjectModalProps) {
	const [multiStepItems, setMultiStepItems] = useState(
		multiStepItemsInitialValues
	);
	const [selectedStep, setSelectedStep] = useState(
		multiStepItemsInitialValues[0]
	);

	const [projectName, setProjectName] = useState<string>('');
	const [githubUsername, setGithubUsername] = useState(
		getCustomFieldValue(
			selectedAccount.customFields ?? [],
			'Github Username'
		)
	);

	const {observer, onClose} = useModal({
		onClose: () => handleClose(),
	});

	const createNewProject = async () => {
		const catalogs = await getCatalogs();
		const channels = await getChannels();
		const categories = await getCategoriesRanked();

		const marketplaceChannel =
			channels.find(
				(channel) => channel.name === 'Marketplace Channel'
			) ?? channels[0];

		const liferayIncCatalog =
			catalogs.find((catalog) => catalog.name === 'Liferay, Inc') ??
			catalogs[0];

		const requiredCategories: Partial<Category>[] = [];

		categories.forEach((category) => {
			if (
				category.parentTaxonomyVocabulary.name ===
					'Marketplace Product Type' ||
				category.parentTaxonomyVocabulary.name ===
					'Liferay Platform Offering'
			) {
				requiredCategories.push({
					externalReferenceCode: category.externalReferenceCode,
					id: category.id,
					name: category.name,
					siteId: category.siteId,
				});
			}
		});

		const newProduct = {
			active: true,
			catalogId: liferayIncCatalog?.id,
			categories: requiredCategories,
			description: {
				en_US: 'A free project for publishers to create for development, testing and demo purposes.',
			},
			name: {
				en_US:
					projectName === ''
						? 'Liferay Experience Cloud Project - 60 days'
						: projectName,
			},
			productConfiguration: {
				allowBackOrder: true,
				maxOrderQuantity: 1,
				minOrderQuantity: 1,
			},
			productType: 'virtual',
		};

		const productResponse = await postProduct(newProduct);

		const newProductSKU = {
			customFields: [
				{
					customValue: {
						data: '1.0',
					},
					name: 'version',
				},
				{
					customValue: {
						data: 'Default 60-day LXC ',
					},
					name: 'version description',
				},
			],
			neverExpire: false,
			price: 0,
			published: true,
			purchasable: true,
			sku: `${productResponse.id}v${productResponse.version}s`,
			skuSubscriptionConfiguration: {
				enable: true,
				length: 60,
				numberOfLength: 1,
				overrideSubscriptionInfo: true,
				subscriptionType: 'daily',
			},
		};

		const SKUResponse = await createAppSKU({
			appProductId: productResponse.id + 1,
			body: newProductSKU,
		});

		const newCart: Partial<Cart> = {
			accountId: selectedAccount.id,
			cartItems: [
				{
					price: {
						currency: marketplaceChannel.currencyCode,
						discount: 0,
						finalPrice: 0,
						price: 0,
					},
					productId: productResponse.id,
					quantity: 1,
					settings: {
						maxQuantity: 1,
					},
					skuId: SKUResponse.id,
				},
			],
			currencyCode: marketplaceChannel.currencyCode,
		};

		const cartResponse = await postCartByChannelId({
			cartBody: newCart,
			channelId: marketplaceChannel.id,
		});

		await postCheckoutCart({cartId: cartResponse.id});

		const order = await getOrderbyERC(cartResponse.orderUUID);

		const orderCustomFields = {
			...order,
			customFields: {
				'Project Name': projectName,
				'Github username': githubUsername,
			},
			orderStatus: 1,
		};

		await patchOrderByERC(cartResponse.orderUUID, orderCustomFields);

		handleClose();

		setShowDashboardNavigation(false);
		setShowNextStepsPage(true);
	};

	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>Confirm Project Creation</ClayModal.Header>

			<ClayModal.Body>
				<div className="create-project-modal-multi-step-container">
					<div className="create-project-modal-multi-step-divider" />

					{multiStepItems.map((multiStepItem) => (
						<div className="create-project-modal-multi-step-item-container">
							<img
								alt="Circle Icon"
								className={classNames(
									'create-project-modal-multi-step-icon',
									{
										'create-project-modal-multi-step-icon-selected':
											multiStepItem.selected ||
											multiStepItem.completed,
									}
								)}
								src={
									multiStepItem.completed
										? checkFillIcon
										: circleFillIcon
								}
							/>

							<span
								className={classNames(
									'create-project-modal-multi-step-label',
									{
										'create-project-modal-multi-step-label-selected':
											multiStepItem.selected ||
											multiStepItem.completed,
									}
								)}
							>
								{multiStepItem.label}
							</span>
						</div>
					))}

					<div className="create-project-modal-multi-step-divider" />
				</div>

				{selectedStep.label === 'Rules & Guidelines' ? (
					<RulesAndGuidelines />
				) : (
					<ProjectDetails
						githubUsername={githubUsername}
						onGithubUsernameChange={setGithubUsername}
						onProjectNameChange={setProjectName}
						projectName={projectName}
					/>
				)}
			</ClayModal.Body>

			<div className="create-project-modal-button-group">
				<button
					className="create-project-modal-button-cancel"
					onClick={() => onClose()}
				>
					Cancel
				</button>

				<ClayButton
					className="create-project-modal-button-continue"
					disabled={
						selectedStep.label === 'Project Details' &&
						(!projectName || !githubUsername)
					}
					onClick={() => {
						if (selectedStep.label === 'Rules & Guidelines') {
							const newMultiStepsItems = multiStepItems.map(
								(item) => {
									if (item.label === selectedStep.label) {
										return {
											...item,
											completed: true,
											selected: false,
										};
									}

									setSelectedStep(item);

									return {
										...item,
										completed: false,
										selected: true,
									};
								}
							);

							setMultiStepItems(newMultiStepsItems);
						}

						if (selectedStep.label === 'Project Details') {
							createNewProject();
						}
					}}
				>
					Continue
				</ClayButton>
			</div>
		</ClayModal>
	);
}
