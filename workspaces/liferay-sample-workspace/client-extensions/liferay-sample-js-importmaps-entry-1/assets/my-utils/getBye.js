export default function getBye(lang) {
	switch(lang) {
		case 'en': return 'Bye';
		case 'es': return 'Adiós';
		case 'fr': return 'Au revoir';
		case 'it': return 'Arrivederci';
		case 'pt': return 'Até logo';
		default: return '???';
	}
}

