import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;

PortalCache<String, String> testPortalCache = PortalCacheHelperUtil.getPortalCache(PortalCacheManagerNames.MULTI_VM, "test.cache");

testPortalCache.remove("test.key");