/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {useEffect} from 'react';
import useIntersectionObserver from '~/hooks/useIntersectionObserver';
import IKoroneikiAccount from '~/interfaces/koroneikiAccount';
import {Liferay} from '~/services/liferay';
import i18n from '~/utils/I18n';
import routerPath from '~/utils/routerPath';

import ProjectCard from './components/ProjectCard';

interface IProps {
	compressed: boolean;
	fetching: boolean;
	koroneikiAccounts: {
		items: IKoroneikiAccount[];
		lastPage: number;
		page: number;
		totalCount: number;
	} | null;
	loading: boolean;
	maxCardsLoading?: number;
	onIntersect: (page: number) => void;
}

const ProjectList: React.FC<IProps> = ({
	compressed,
	fetching,
	koroneikiAccounts,
	loading,
	maxCardsLoading = 4,
	onIntersect,
}) => {
	const [trackedRefCurrent, isIntersecting] = useIntersectionObserver();
	const isLastPage = koroneikiAccounts?.page === koroneikiAccounts?.lastPage;

	const allowFetching = !isLastPage && !fetching;

	interface IRenderResultsProps {
		compressed: boolean;
		koroneikiAccounts: {
			items: IKoroneikiAccount[];
			lastPage: number;
			page: number;
			totalCount: number;
		} | null;
		loading: boolean;
	}

	const RenderResults: React.FC<IRenderResultsProps> = ({
		compressed,
		koroneikiAccounts,
		loading,
	}) => {
		const pageRoutes = routerPath();

		if (!koroneikiAccounts) {
			return (
				<p className="mx-auto">
					{i18n.translate('sorry-there-are-no-results-found')}
				</p>
			);
		}

		if (koroneikiAccounts.totalCount) {
			return (
				<>
					{koroneikiAccounts?.items.map((koroneikiAccount, index) => (
						<ProjectCard
							compressed={compressed}
							key={`${koroneikiAccount.accountKey}-${index}`}
							koroneikiAccount={koroneikiAccount}
							loading={loading}
							onClick={() =>
								Liferay.Util.navigate(
									pageRoutes.project(
										koroneikiAccount.accountKey
									)
								)
							}
						/>
					))}

					{loading && (
						<div className="mx-auto">
							<ClayLoadingIndicator size="sm" />
						</div>
					)}
				</>
			);
		}

		return (
			<p className="mx-auto">
				{i18n.translate('no-projects-match-these-criteria')}
			</p>
		);
	};

	useEffect(() => {
		if (isIntersecting && allowFetching) {
			onIntersect(koroneikiAccounts?.page || 1);
		}
	}, [isIntersecting, koroneikiAccounts?.page, onIntersect, allowFetching]);

	return (
		<div
			className={classNames('d-flex', {
				'flex-column': compressed,
				'flex-wrap pl-3': !compressed,
			})}
		>
			{loading ? (
				<>
					{[...new Array(maxCardsLoading)].map((_, index) => (
						<ProjectCard
							compressed={compressed}
							key={index}
							koroneikiAccount={undefined}
							loading={loading}
						/>
					))}
				</>
			) : (
				<RenderResults
					compressed={compressed}
					koroneikiAccounts={koroneikiAccounts}
					loading={loading}
				/>
			)}

			<div ref={trackedRefCurrent as any}></div>
		</div>
	);
};

export default ProjectList;
