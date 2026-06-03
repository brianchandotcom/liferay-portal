import Label from 'shared/components/Label';
import React from 'react';

const MetadataTag: React.FC<{value: string}> = ({value}) => (
	<Label className='metadata-tag-root' size='lg'>
		{value}
	</Label>
);

export default MetadataTag;
