/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLoadingIndicator from '@clayui/loading-indicator';

import Container from '../../../common/components/dashboard/components/Container';
import ClayIconProvider from '../../../common/components/dashboard/utils/ClayIconProvider';
import PartnershipLevel from './components/PartnershipLevel';
import useGetAccountInformation from './hooks/useAccountInformation';

const LevelChart = () => {
	const {
		aRRResults,
		account,
		checkedProperties,
		headcount,
		loading,
		partnerLevel,
	} = useGetAccountInformation();

	const BuildPartnershipLevel = () => {
		if (loading) {
			return <ClayLoadingIndicator className="mb-10 mt-9" size="md" />;
		}

		if (!account || !partnerLevel) {
			return (
				<ClayAlert
					className="mb-8 mt-8 mx-auto text-center w-50"
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
				account={account}
				checkedProperties={checkedProperties}
				headcount={headcount}
				partnerLevel={partnerLevel}
			/>
		);
	};

	return (
		<ClayIconProvider>
			<Container title="Partnership Level">
				<BuildPartnershipLevel />
			</Container>
		</ClayIconProvider>
	);
};

export default LevelChart;
