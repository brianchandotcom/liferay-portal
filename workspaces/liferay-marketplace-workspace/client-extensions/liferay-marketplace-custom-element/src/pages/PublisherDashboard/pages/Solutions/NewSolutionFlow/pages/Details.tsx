/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import {useState} from 'react';

import Form from '../../../../../../components/MarketplaceForm';

const items = [
	{label: 'Choose an option'},
	{label: 'Text & Images Block'},
	{label: 'Text & Video Block'},
	{label: 'Text Block'},
	{label: 'Single Image Block'},
	{label: 'Icons Block'},
	{label: 'Images Grid Block'},
];

const Details = () => {
	const {observer, onOpenChange, open} = useModal();
	const [selectedBlock, setSelectedBlock] = useState('Choose an option');
	const [submit, setSubmit] = useState(false);

	return (
		<div className="solutions-form-details">
			<Form.Label className="mt-3" htmlFor="minimum-blocks" required>
				Add a minimum of 2 blocks
			</Form.Label>

			{submit && <Form.Section name={selectedBlock}></Form.Section>}

			<ClayButton
				className="align-items-center content-block d-flex flex-row justify-content-center mt-4 w-100"
				displayType="secondary"
				onClick={() => onOpenChange(true)}
			>
				<span className="d-flex flex-row inline-item inline-item-before">
					<ClayIcon symbol="plus" />
				</span>
				Add Content Block
			</ClayButton>

			{open && (
				<ClayModal center observer={observer}>
					<ClayModal.Body className="mb-1">
						<h1 className="d-flex justify-content-between">
							Select Content Block
							<ClayButtonWithIcon
								aria-label="Close"
								className="inline-item"
								displayType="unstyled"
								onClick={() => onOpenChange(false)}
								size="sm"
								symbol="times"
								title="Close"
							/>
						</h1>

						<p className="text-black-50">
							Choose one of the following content blocks
						</p>
						<Form.Label
							className="mt-5"
							htmlFor="choose-block"
							required
						>
							Choose Block
						</Form.Label>

						<ClaySelect
							aria-label="Select Label"
							id="mySelectId"
							onChange={({target}) => {
								setSelectedBlock(target.value);
							}}
						>
							{items.map((item, index) => (
								<ClaySelect.Option
									key={index}
									label={item.label}
									value={item.label}
								/>
							))}
						</ClaySelect>

						<div className="align-items-end d-flex justify-content-end mt-8">
							<ClayButton
								className="mr-2"
								displayType="secondary"
								onClick={() => onOpenChange(false)}
							>
								Cancel
							</ClayButton>

							<ClayButton
								disabled={
									selectedBlock === 'Choose an option' ||
									selectedBlock === ''
								}
								displayType="primary"
								onClick={() => {
									onOpenChange(false);
									setSubmit(true);
								}}
							>
								Save
							</ClayButton>
						</div>
					</ClayModal.Body>
				</ClayModal>
			)}
		</div>
	);
};

export default Details;
