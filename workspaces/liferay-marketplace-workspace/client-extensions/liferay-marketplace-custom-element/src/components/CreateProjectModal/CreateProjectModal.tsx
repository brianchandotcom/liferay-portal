import ClayButton from '@clayui/button';
import ClayModal, {useModal} from '@clayui/modal';
import classNames from 'classnames';
import {useState} from 'react';

import checkFillIcon from '../../assets/icons/check_fill_icon.svg';
import circleFillIcon from '../../assets/icons/circle_fill_icon.svg';
import {
	getOrderTypes,
	getProductSKU,
	getProducts,
	patchOrderByERC,
	postCartByChannelId,
	postCheckoutCart,
} from '../../utils/api';
import {getCustomFieldValue} from '../../utils/customFieldUtil';
import {ProjectDetails} from './ProjectDetails';
import {RulesAndGuidelines} from './RulesAndGuidelines';

import './CreateProjectModal.scss';
interface CreateProjectModalProps {
	currentChannel: Channel;
	handleClose: () => void;
	selectedAccount: Account;
	setShowDashboardNavigation: (value: boolean) => void;
	setShowNextStepsPage: (value: boolean) => void;
}

const multiStepItemsInitialValues = [
	{
		completed: false,
		label: 'Rules & Guidelines',
		selected: true,
	},
	{
		completed: false,
		label: 'Project Details',
		selected: false,
	},
];

export function CreateProjectModal({
	currentChannel,
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
		const {items} = await getProducts();

		const projectProduct = items.find(({categories}) => {
			return !!categories.find(({name}) => name === 'Project');
		});

		if (projectProduct) {
			const {
				items: [projectSKU],
			} = await getProductSKU({appProductId: projectProduct.productId});
			const orderTypes = await getOrderTypes();

			const projectOrderType = orderTypes.find(
				({name}) => name['en_US'] === 'Project - 60 days'
			);

			const cart: Partial<Cart> = {
				accountId: selectedAccount?.id as number,
				cartItems: [
					{
						price: {
							currency: currentChannel.currencyCode,
							discount: 0,
							finalPrice: 0,
							price: 0,
						},
						productId: projectProduct.id,
						quantity: 1,
						settings: {
							maxQuantity: 1,
						},
						skuId: projectSKU.id as number,
					},
				],
				currencyCode: currentChannel.currencyCode,
				orderTypeExternalReferenceCode:
					projectOrderType?.externalReferenceCode,
				orderTypeId: projectOrderType?.id as number,
			};

			let newCart: Partial<Cart> = {};

			newCart = {
				...cart,
			};

			const cartResponse = await postCartByChannelId({
				cartBody: newCart,
				channelId: currentChannel.id,
			});

			const cartCheckoutResponse = await postCheckoutCart({
				cartId: cartResponse.id,
			});

			const newOrderValues = {
				orderStatus: 1,
			};

			const orderCustomFields = {
				customFields: {
					'Github username': githubUsername,
					'Project Name': projectName,
				},
			};

			await patchOrderByERC(
				cartCheckoutResponse.orderUUID,
				newOrderValues
			);

			await patchOrderByERC(
				cartCheckoutResponse.orderUUID,
				orderCustomFields
			);

			handleClose();

			setShowDashboardNavigation(false);
			setShowNextStepsPage(true);
		}
	};

	return (
		<ClayModal observer={observer} size="lg">
			<ClayModal.Header>Confirm Project Creation</ClayModal.Header>

			<ClayModal.Body>
				<div className="create-project-modal-multi-step-container">
					<div className="create-project-modal-multi-step-divider" />

					{multiStepItems.map((multiStepItem, i) => (
						<div
							className="create-project-modal-multi-step-item-container"
							key={multiStepItem.label + i}
						>
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
