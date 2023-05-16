import DOMPurify from 'dompurify';

export function extractHTMLText(html: string) {
	const sanitizedHTML = DOMPurify.sanitize(html);

	const tempElement = document.createElement('div');
	tempElement.innerHTML = sanitizedHTML;

	const textContent = tempElement.textContent || tempElement.innerText;

	return textContent;
}

export function removeUnnecessaryURLString(str: string) {
	const index = str.indexOf('/o');

	return str.substring(index);
}
