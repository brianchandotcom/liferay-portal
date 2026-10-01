import ErrorPage from 'shared/pages/ErrorPage';
import React from 'react';
import {Outlet, useParams} from 'react-router-dom';
import {useLDPEnabled} from 'shared/hooks/useLDPEnabled';

interface ILDPElementProps {
	children: React.ReactNode;
	fallback: React.ReactNode;
}

export const LDPElement: React.FC<ILDPElementProps> = ({
	children,
	fallback,
}) => {
	const {groupId = ''} = useParams<{groupId: string}>();

	const LDPEnabled = useLDPEnabled({groupId});

	return <>{LDPEnabled ? children : fallback}</>;
};

const LDPLayout: React.FC = () => (
	<LDPElement fallback={<ErrorPage />}>
		<Outlet />
	</LDPElement>
);

export default LDPLayout;
