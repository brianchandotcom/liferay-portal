/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';

import './LevelChart.css';
import Container from '../../../common/components/dashboard/components/Container';
import ClayIconProvider from '../../../common/components/dashboard/utils/ClayIconProvider';
import PartnershipLevel from './components/PartnershipLevel';
import useGetAccountInformation from './hooks/useAccountInformation';

const LevelChart = () => {
	const {
		aRRResults,
		account,
		checkedProperties,
		currency,
		headcount,
		loading,
		opportunitiesCount,
		partnerLevel,
	} = useGetAccountInformation();

	const BuildPartnershipLevel = () => {
		if (loading) {
			return <ClayLoadingIndicator className="mb-10 mt-10" size="md" />;
		}

		if (!account || !partnerLevel || opportunitiesCount === undefined) {
			return (
				<ClayAlert
					className="mx-auto my-9 text-center w-75"
					displayType="info"
					title="Info:"
				>
					No Data Available
				</ClayAlert>
			);
		}

		return (
			<PartnershipLevel
				aRRResults={aRRResults}
				checkedProperties={checkedProperties}
				currency={currency}
				headcount={headcount}
				opportunitiesCount={opportunitiesCount}
				partnerLevel={partnerLevel}
			/>
		);
	};

	return (
		<ClayIconProvider>
			<Container
				className="dashboard-level-chart justify-content-start"
				title="Partnership Level"
			>
				<BuildPartnershipLevel />
			</Container>
		</ClayIconProvider>
	);
};

export default LevelChart;
