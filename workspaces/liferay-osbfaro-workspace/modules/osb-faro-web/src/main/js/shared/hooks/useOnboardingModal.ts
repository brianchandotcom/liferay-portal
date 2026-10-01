import SitesDashboardQuery from 'shared/queries/SitesDashboardQuery';
import {close, modalTypes, open} from 'shared/actions/modals';
import {isArray} from 'lodash';
import {OnboardingContext} from 'shared/context/onboarding';
import {useContext, useEffect} from 'react';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useDispatch} from 'react-redux';
import {useQuery} from '@apollo/client';

export const useOnboardingModal = ({groupId}: {groupId: string}) => {
	const dispatch = useDispatch();

	const {onboardingTriggered, setOnboardingTriggered} =
		useContext(OnboardingContext);

	const currentUser = useCurrentUser();

	const {data, loading} = useQuery(SitesDashboardQuery, {
		variables: {type: null},
	});

	useEffect(() => {
		if (
			!onboardingTriggered &&
			currentUser.isAdmin() &&
			!loading &&
			isArray(data?.dataSources) &&
			!data.dataSources.length
		) {
			dispatch(
				open(modalTypes.ONBOARDING_MODAL, {
					groupId,
					onClose: () => dispatch(close()),
				})
			);

			setOnboardingTriggered();
		}
	}, [data]);
};
