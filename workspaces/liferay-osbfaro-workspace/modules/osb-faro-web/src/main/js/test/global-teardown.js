const fs = require('fs');
const path = require('path');

module.exports = () => {
	fs.rmSync(path.dirname(process.env.FARO_TEST_LANG_KEYS_FILE), {
		force: true,
		recursive: true,
	});
};
