/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayLink from '@clayui/link';
import React, {useEffect, useRef, useState} from 'react';
import * as API from '~/shared/api';
import Modal from '~/shared/components/modal';
import {
	useSelectionContext,
	withSelectionProvider,
} from '~/shared/context/selection';
import CrossPageSelect from '~/shared/hoc/CrossPageSelect';
import {useRequest} from '~/shared/hooks/useRequest';
import {useStatefulPagination} from '~/shared/hooks/useStatefulPagination';
import {Sizes} from '~/shared/util/constants';
import {NAME, createOrderIOMap} from '~/shared/util/pagination';
import URLConstants from '~/shared/util/url-constants';

import NoResultsDisplay from '../NoResultsDisplay';

interface ISelectChannelsModalProps {
	groupId: string;
	initialItems: string[];
	onClose: () => {};
	onSelect: (channels: string[]) => {};
}

const SelectChannelsModal: React.FC<ISelectChannelsModalProps> = ({

	/**
	 * const {groupId} = useParams() doesn't work on Modals
	 */
	groupId,
	initialItems = [],
	onClose,
	onSelect,
}) => {
	const {selectedItems, selectionDispatch} = useSelectionContext();

	const {
		delta,
		onDeltaChange,
		onOrderIOMapChange,
		onPageChange,
		onQueryChange,
		orderIOMap,
		page,
		query,
	} = useStatefulPagination(undefined, {
		initialOrderIOMap: createOrderIOMap(NAME),
	});

	const {data, error, loading} = useRequest({
		dataSourceFn: API.channels.search,
		variables: {
			cur: page,
			delta,
			groupId,
			orderIOMap,
			query,
		},
	});

	const [showAlert, setShowAlert] = useState(false);
	const hasAutoSelectedRef = useRef(false);

	useEffect(() => {
		if (selectedItems.keySeq().toArray().length) {
			setShowAlert(false);
		}
	}, [selectedItems]);

	useEffect(() => {
		if (initialItems.length) {
			selectionDispatch?.({
				payload: {items: initialItems.map((id) => ({id}))},
				type: 'add',
			});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [initialItems]);

	useEffect(() => {
		if (data?.items && !hasAutoSelectedRef.current) {
			hasAutoSelectedRef.current = true;

			const channelsWithSites = data.items.filter(
				(item: {groupsCount: number; id: string}) =>
					item.groupsCount > 0 && !initialItems.includes(item.id)
			);

			if (channelsWithSites.length) {
				selectionDispatch?.({
					payload: {items: channelsWithSites},
					type: 'add',
				});
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data]);

	return (
		<Modal size="lg">
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('select-properties')}
			/>

			<ClayForm
				onSubmit={(event) => {
					event.preventDefault();

					if (selectedItems.isEmpty()) {
						setShowAlert(true);

						return;
					}

					onSelect(selectedItems.keySeq().toArray());

					onClose();
				}}
			>
				<Modal.Body className="p-0" inlineScroller>
					{showAlert && (
						<div className="px-4">
							<ClayAlert
								displayType="danger"
								onClose={() => setShowAlert(false)}
								title={Liferay.Language.get('error')}
							>
								{Liferay.Language.get(
									'unable-to-perform-this-action.-select-a-property-to-proceed'
								)}
							</ClayAlert>
						</div>
					)}

					<CrossPageSelect
						columns={[
							{
								accessor: NAME,
								className: 'table-cell-expand',
								label: Liferay.Language.get(
									'available-properties'
								),
								title: true,
							},
							{
								accessor: 'commerceChannelsCount',
								className: 'text-right',
								label: Liferay.Language.get(
									'dxp-commerce-channels'
								),
								sortable: false,
							},
							{
								accessor: 'groupsCount',
								className: 'text-right',
								label: Liferay.Language.get('sites'),
								sortable: false,
							},
						]}
						delta={delta}
						entityLabel={Liferay.Language.get('properties')}
						error={error}
						items={data?.items}
						loading={loading}
						noResultsRenderer={
							<NoResultsDisplay
								description={
									<>
										{Liferay.Language.get(
											'go-to-properties-under-workspace-settings-to-create-a-property'
										)}

										<ClayLink
											className="d-block mb-3"
											href={URLConstants.CreateProperty}
											key="DOCUMENTATION"
											target="_blank"
										>
											{Liferay.Language.get(
												'access-our-documentation-to-learn-more'
											)}
										</ClayLink>
									</>
								}
								icon={{
									border: false,
									size: Sizes.XXXLarge,
									symbol: 'ac_satellite',
								}}
								title={Liferay.Language.get(
									'there-are-no-properties-found'
								)}
							/>
						}
						onDeltaChange={onDeltaChange}
						onOrderIOMapChange={onOrderIOMapChange}
						onPageChange={onPageChange}
						onQueryChange={onQueryChange}
						orderByOptions={[
							{
								label: Liferay.Language.get('property-name'),
								value: NAME,
							},
						]}
						orderIOMap={orderIOMap}
						page={page}
						query={query}
						rowIdentifier="id"
						showCheckbox
						total={data?.total}
					/>
				</Modal.Body>

				<Modal.Footer>
					<ClayButton displayType="secondary" onClick={onClose}>
						{Liferay.Language.get('cancel')}
					</ClayButton>

					<ClayButton displayType="primary" type="submit">
						{Liferay.Language.get('select')}
					</ClayButton>
				</Modal.Footer>
			</ClayForm>
		</Modal>
	);
};

export default withSelectionProvider(SelectChannelsModal);
