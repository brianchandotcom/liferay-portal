/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import i18n from '../../../../../../../../../common/I18n';
import {Skeleton} from '../../../../../../../../../common/components';
import SLACard from './components/SLACard';
import SLACardMessage from './components/SLACardMessage/SLACardMessage';
import SwitchSLACardButton from './components/SwitchSLACardButton';
import useSLACardPosition from './hooks/useSLACardPosition';
import useSLACards from './hooks/useSLACards';

const SLACardsList = ({koroneikiAccount, loading}) => {
	const slaCards = useSLACards(koroneikiAccount);
	const {changePosition, currentPosition, lastPosition} = useSLACardPosition(
		slaCards?.length
	);

	const getSLACards = () =>
		slaCards?.map((slaCard, index) => (
			<SLACard
				{...slaCard}
				active={currentPosition === index}
				key={`${slaCard.title}-${index}`}
				last={lastPosition === index}
				unique={slaCards.length < 2}
			/>
		));

	return (
		<div className="mb-5">
			{loading ? (
				<Skeleton className="mb-4" height={22} width={140} />
			) : (
				<h5 className="mb-4">{i18n.translate('support-level')}</h5>
			)}

			{loading ? (
				<Skeleton height={84} width={200} />
			) : slaCards?.length ? (
				<div
					className={classNames({
						'cp-sla-container ml-3': slaCards.length > 1,
					})}
				>
					<div className="d-flex">{getSLACards()}</div>

					{slaCards.length > 1 && (
						<SwitchSLACardButton
							handleClick={() => changePosition()}
						/>
					)}
				</div>
			) : (
				<SLACardMessage />
			)}
		</div>
	);
};

export default SLACardsList;
