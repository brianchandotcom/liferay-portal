/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';
import {
	EntityType,
	ReferencedObjectsContext,
} from '~/segment/segment-editor/dynamic/context/referencedObjects';
import {parseReferencedEntityId} from '~/segment/segment-editor/dynamic/utils/utils';
import {sub} from '~/shared/util/lang';

const ReferencedEntity: React.FC<{
	id: string;
	label: string;
	type: EntityType;
}> = ({id, label, type}) => {
	const {referencedEntities} = useContext(ReferencedObjectsContext);

	const entity = referencedEntities.getIn([
		type,
		parseReferencedEntityId(id, referencedEntities, type),
	]);

	return entity ? (
		<b>{`'${entity.get('name')}'`}</b>
	) : (
		<b className="undefined-entity">
			{sub(Liferay.Language.get('undefined-x'), [label])}
		</b>
	);
};

export default ReferencedEntity;
