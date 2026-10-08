import InfoPopover from 'shared/components/InfoPopover';
import React, {useId} from 'react';
import {ClayToggle} from '@clayui/form';
import {FieldProps} from 'formik';
import {withField} from 'shared/components/form';

const SegmentSequentialToggle: React.FC<FieldProps> = ({field, form}) => {
	const id = useId();

	return (
		<div className="align-items-center c-gap-2 d-flex">
			<ClayToggle
				containerProps={{className: 'mb-0'}}
				id={id}
				label={Liferay.Language.get('enable-sequential-criteria')}
				name={field.name}
				onBlur={(event) => field.onBlur(event)}
				onToggle={(toggled) => form.setFieldValue(field.name, toggled)}
				toggled={!!field.value}
			/>

			<InfoPopover
				content={Liferay.Language.get(
					'when-this-is-enabled,the-second-event-must-come-after-the-first-event,-with-any-number-of-events-in-between'
				)}
			/>
		</div>
	);
};

export default withField(SegmentSequentialToggle);
