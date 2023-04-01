import logger from './Logger';

const { JIRA_AUTH_CLOUD_ID } = Bun.env;

class Cache {
  private cache = new Map<string, any>();
  private static instance: Cache;
  static KEYS = {
    JIRA_APP_ID_KEY: 'JIRA_APP_ID_KEY',
  };

  private constructor() {
    if (JIRA_AUTH_CLOUD_ID) {
      this.cache.set(Cache.KEYS.JIRA_APP_ID_KEY, JIRA_AUTH_CLOUD_ID);
    }
  }

  public static getInstance(): Cache {
    if (!Cache.instance) {
      Cache.instance = new Cache();
    }

    return Cache.instance;
  }

  public get<T = any>(key: string): T | undefined {
    const cachedValue = this.cache.get(key);

    logger.debug(`[CACHE]: Get: ${key}, StoredValue: ${cachedValue}}`);

    return cachedValue;
  }

  public set(key: string, value: unknown) {
    logger.debug(`[CACHE]: Set: ${key}, StoredValue: ${value}}`);

    this.cache.set(key, value);
  }
}

export default Cache;
