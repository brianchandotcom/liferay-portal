export function removeUnnecessaryURLString(str: string) {
	const index = str.indexOf('/o');

	return str.substring(index);
}
