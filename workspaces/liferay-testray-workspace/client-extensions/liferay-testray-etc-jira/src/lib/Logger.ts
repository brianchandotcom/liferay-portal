import chalk from 'chalk';

const colors = {
  DEBUG: chalk.cyan,
  INFO: chalk.blue,
  WARNING: chalk.yellow,
};

const getLoggerPrefix = (type: keyof typeof colors) =>
  `🦊 ${new Date().toISOString()} - ${colors[type](`${type}:`)}`;

const logger = {
  ...console,
  debug(...log: any[]) {
    console.log(getLoggerPrefix('DEBUG'), ...log);
  },
  info(...log: any[]) {
    console.log(getLoggerPrefix('INFO'), ...log);
  },
  warning(...log: any[]) {
    console.log(getLoggerPrefix('WARNING'), ...log);
  },
};

export default logger;
