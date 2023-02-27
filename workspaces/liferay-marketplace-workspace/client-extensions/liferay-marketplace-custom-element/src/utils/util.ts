import {getCatalogs} from './api';

export async function getCatalogId() {
	const response = await getCatalogs();

	return response.items[0].id;
}

export async function submitFile(
	appERC: string,
	fileBase64: string,
	requestFunction: Function,
	title: string
) {
	const response = await requestFunction({
		body: {
			attachment: fileBase64,
			title: {en_US: title},
		},
		externalReferenceCode: appERC,
	});
	const file = await response.json();
}

export function submitBase64EncodedFile(
	appERC: string,
	file: File,
	requestFunction: Function,
	title: string
) {
	const reader = new FileReader();

	reader.addEventListener(
		'load',
		() => {
			let result = reader.result as string;

			if (result?.includes('application/zip')) {
				result = result?.substring(28);
			}
			else if (
				result?.includes('image/gif') ||
				result?.includes('image/png')
			) {
				result = result?.substring(22);
			}
			else if (result?.includes('image/jpeg')) {
				result = result?.substring(23);
			}

			if (result) {
				submitFile(appERC, result, requestFunction, title);
			}
		},
		false
	);

	reader.readAsDataURL(file);
}
