import getCN from 'classnames';
import React from 'react';
import {ProfileTypes} from 'segment/segment-editor/dynamic/utils/constants';

interface IMembershipChanges {
	className?: string;
	data: {
		profileType: ProfileTypes;
	};
}

const PROFILE_TYPE_LABEL_MAP: Record<ProfileTypes, string> = {
	ANONYMOUS: Liferay.Language.get('anonymous'),
	KNOWN: Liferay.Language.get('known')
};

const ProfileType: React.FC<IMembershipChanges> = ({
	className,
	data: {profileType}
}) => (
	<td className={getCN('text-capitalize', className)}>
		{PROFILE_TYPE_LABEL_MAP[profileType]}
	</td>
);

export default ProfileType;
