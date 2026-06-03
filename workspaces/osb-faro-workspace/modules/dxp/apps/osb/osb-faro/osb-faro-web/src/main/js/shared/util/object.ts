/**
 * Shallowly creates a new Object without the Property at key.
 */
export const deletePropertyFromObject = (
	key: string,
	object: {[key: string]: any}
) => {
	const retVal = Object.assign({}, object);

	delete retVal[key];

	return retVal;
};
