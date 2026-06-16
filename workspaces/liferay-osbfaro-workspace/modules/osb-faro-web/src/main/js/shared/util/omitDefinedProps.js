import {omit} from 'lodash';

export default function omitPropTypes(otherProps, propTypes) {
	return omit(otherProps, Object.keys(propTypes));
}
