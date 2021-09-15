import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;

import java.util.List;

PortalCache<String, String> testPortalCache = PortalCacheHelperUtil.getPortalCache(PortalCacheManagerNames.MULTI_VM, "test.cache");

List<String> keys = testPortalCache.getKeys();

if (keys.isEmpty()) {
	out.println("test.cache is empty");

	return;
}

for (String key : keys) {
	out.println(key + "=" + testPortalCache.get(key));
}