export default function getHello(lang) {
	switch (lang) {
		case 'en':
			return 'Hello';
		case 'es':
			return 'Hola';
		case 'fr':
			return 'Bonjour';
		case 'it':
			return 'Ciao';
		case 'pt':
			return 'Olá';
		default:
			return '???';
	}
}
