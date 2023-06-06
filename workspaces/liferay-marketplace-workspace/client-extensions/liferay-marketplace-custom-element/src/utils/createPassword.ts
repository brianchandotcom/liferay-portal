let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#0123456789';

const getRandomInteger = (min : number, max: number) => {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

export const createPassword = () => {
    let password = '';
    if (characters.length) {
        for (let i = 0; i < 10; i++) {
            password += characters[getRandomInteger(0, characters.length - 1)];
        }
        return password;
    }
    return password;
}
