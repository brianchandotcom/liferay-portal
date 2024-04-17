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
import IconsBlock from '../../components/Blocks/IconBlock';
import ImagesGrid from '../../components/Blocks/ImagesGrid';
import SingleImage from '../../components/Blocks/SingleImage';
import TextAndImages from '../../components/Blocks/TextAndImages';
import TextAndVideos from '../../components/Blocks/TextAndVideo';
import TextBlock from '../../components/Blocks/TextBlock';

const blocks = [
	{name: 'Text Block', render: TextBlock},
	{name: 'Text & Images Block', render: TextAndImages},
	{name: 'Text & Video Block', render: TextAndVideos},
	{name: 'Single Image Block', render: SingleImage},
	{name: 'Images Grid Block', render: ImagesGrid},
	{name: 'Icons Block', render: IconsBlock},
];

const items = [
	{label: 'Choose an option', value: ''},
	{label: 'Text & Images Block', value: 'text-images-block'},
	{label: 'Text & Video Block', value: 'text-video-block'},
	{label: 'Text Block', value: 'text-block'},
	{label: 'Single Image Block', value: 'single-image-block'},
	{label: 'Icons Block', value: 'icons-block'},
	{label: 'Images Grid Block', value: 'images-grid-block'},
];

const Details = () => {
	const {observer, onOpenChange, open} = useModal();
	const [selectedBlock, setSelectedBlock] = useState('');
	const [selectedBlockList, setSelectedBlockList] = useState<string[]>([]);

	return (
		<div className="solutions-form-details">
			<Form.Label className="mt-3" htmlFor="minimum-blocks" required>
				Add a minimum of 2 blocks
			</Form.Label>

			{selectedBlockList.map((selectedBlock, index) => {
				return (
					<Form.SectionWithControllers
						index={index}
						key={index}
						name={
							items.find(({value}) => value === selectedBlock)
								?.label ?? selectedBlock
						}
						position={selectedBlockList.length}
					>
						{blocks.map((block, index) => {
							if (block.name === selectedBlock) {
								const Component = block.render;

								return <Component key={index} />;
							}

							return null;
						})}
					</Form.SectionWithControllers>
				);
			})}

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
							id="choose-block"
							onChange={({target}) => {
								setSelectedBlock(target.value);
							}}
							value={selectedBlock}
						>
							{items.map((item, index) => (
								<ClaySelect.Option
									key={index}
									label={item.label}
									value={item.value}
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
								disabled={!selectedBlock}
								displayType="primary"
								onClick={() => {
									onOpenChange(false);
									setSelectedBlockList([
										...selectedBlockList,
										selectedBlock,
									]);
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
