import ClayModal, {useModal} from '@clayui/modal';
import React, {useState} from 'react';

import checkFillIcon from '../../assets/icons/check_fill.svg';
import circleFillIcon from '../../assets/icons/circle_fill.svg';

import './CreateProjectModal.scss';

import classNames from 'classnames';

import {ProjectDetails} from './ProjectDetails';
import {RulesAndGuidelines} from './RulesAndGuidelines';

interface CreateProjectModalProps {
	handleClose: () => void;
}

const multiStepItemsInitialValues = [
	{
		label: 'Rules & Guidelines',
		selecetd: true,
		completed: false,
	},
	{
		label: 'Project Details',
		selecetd: false,
		completed: false,
	},
];

export function CreateProjectModal({handleClose}: CreateProjectModalProps) {
	const [multiStepItems, setMultiStepItems] = useState(
		multiStepItemsInitialValues
	);
	const [selectedStep, setSelectedStep] = useState(
		multiStepItemsInitialValues[0]
	);

	const {observer, onClose} = useModal({
		onClose: () => handleClose(),
	});

	console.log(multiStepItems);

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
											multiStepItem.selecetd ||
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
											multiStepItem.selecetd ||
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
					<ProjectDetails />
				)}
			</ClayModal.Body>

			<div className="create-project-modal-button-group">
				<button
					className="create-project-modal-button-cancel"
					onClick={() => onClose()}
				>
					Cancel
				</button>

				<button
					className="create-project-modal-button-continue"
					onClick={() => {
						const newMultiStepsItems = multiStepItems.map(
							(item) => {
								if (item.label === selectedStep.label) {
									return {
										...item,
										completed: true,
										selecetd: false,
									};
								}

								setSelectedStep(item);

								return {
									...item,
									completed: false,
									selecetd: true,
								};
							}
						);

						setMultiStepItems(newMultiStepsItems);
					}}
				>
					Continue
				</button>
			</div>
		</ClayModal>
	);
}
