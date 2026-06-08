/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {get} from 'lodash';
import React from 'react';
import {connect} from 'react-redux';
import {close, modalTypes, open} from '~/shared/actions/modals';
import Card from '~/shared/components/Card';
import Loading from '~/shared/components/Loading';
import {Modal} from '~/shared/types';
import Constants from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';

import RecommendationPageAssetsQuery from '../queries/RecommendationPageAssetsQuery';
import {EXCLUDE, Filter} from '../utils/utils';
import RuleItem from './RuleItem';

const {
	pagination: {orderDescending},
} = Constants;

interface ITrainingItemProps {
	close: Modal.close;
	name: string;
	open: Modal.open;
	value: string;
}

const TrainingItem: React.FC<ITrainingItemProps> = ({
	close,
	name,
	open,
	value,
}) => (
	<div className="align-items-baseline d-flex training-item-root">
		<ClayButton
			aria-label={Liferay.Language.get('watch')}
			borderless
			className="button-root"
			displayType="secondary"
			onClick={() => {
				open(modalTypes.MATCHING_PAGES_MODAL, {
					itemFilters: [{name, value}],
					onClose: close,
				});
			}}
		>
			<ClayIcon className="icon-root" symbol="view" />
		</ClayButton>

		<RuleItem name={name} value={value} />
	</div>
);

interface ITrainingItemsCardProps {
	close: Modal.close;
	itemFilters: Filter[];
	open: Modal.open;
}

const TrainingItemsCard: React.FC<ITrainingItemsCardProps> = ({
	close,
	itemFilters,
	open,
}) => {
	const {data, loading} = useQuery(RecommendationPageAssetsQuery, {
		variables: {
			propertyFilters: itemFilters.map(({name, value}) => ({
				filter: value,
				negate: name === EXCLUDE,
			})),
			size: 0,
			sort: {
				column: 'title',
				type: orderDescending.toUpperCase(),
			},
			start: 0,
		},
	});

	const renderTotalTrainingUrls = () => {
		if (loading) {
			return <Loading key="LOADING" />;
		}

		return get(data, ['pageAssets', 'total'], 0).toLocaleString();
	};

	return (
		<Card className="training-items-card-root">
			<Card.Header>
				<Card.Title>
					{Liferay.Language.get('training-items')}
				</Card.Title>
			</Card.Header>

			<Card.Body>
				{itemFilters.map(({name, value}) => (
					<TrainingItem
						close={close}
						key={`${name}-${value}`}
						name={name}
						open={open}
						value={value}
					/>
				))}

				<div className="align-items-center d-flex total-training-urls">
					<ClayButton
						aria-label={Liferay.Language.get('watch')}
						borderless
						className="button-root"
						displayType="secondary"
						onClick={() => {
							open(modalTypes.MATCHING_PAGES_MODAL, {
								itemFilters,
								onClose: close,
							});
						}}
					>
						<ClayIcon className="icon-root" symbol="view" />
					</ClayButton>

					{sub(
						Liferay.Language.get('total-training-urls-x'),
						[renderTotalTrainingUrls()],
						false
					)}
				</div>
			</Card.Body>
		</Card>
	);
};

export default connect(null, {close, open})(TrainingItemsCard);
