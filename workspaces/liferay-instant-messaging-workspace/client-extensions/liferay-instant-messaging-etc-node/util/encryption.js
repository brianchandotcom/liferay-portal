const crypto = require('crypto');

const algorithm = 'aes-256-cbc';

const key = Buffer.from('01234567890123456789012345678901');

const iv = Buffer.from('1234567890123456');

const encrypt = (text) => {

    text = text.toString();

    const cipher = crypto.createCipheriv(algorithm, key, iv);

    let encrypted = cipher.update(text, 'utf8', 'hex');

    encrypted += cipher.final('hex');


    return iv.toString('hex') + ':' + encrypted;
}

const decrypt = (encryptedText) => {

    const [ivHex, encryptedData] = encryptedText.split(':');

    const iv = Buffer.from(ivHex, 'hex');

    const decipher = crypto.createDecipheriv(algorithm, key, iv);

    let decrypted = decipher.update(encryptedData, 'hex', 'utf8');

    decrypted += decipher.final('utf8');

    return decrypted;
}

module.exports = {
    encrypt,
    decrypt
}
