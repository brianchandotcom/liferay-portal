import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;

PortalCache<String, String> portalCache = PortalCacheHelperUtil.getPortalCache(PortalCacheManagerNames.MULTI_VM, "test.cache");

portalCache.removeAll();