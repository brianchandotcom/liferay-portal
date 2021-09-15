import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;

import java.lang.reflect.Constructor;

try {
	ClassLoader testModuleClassLoader = ClassLoaderPool.getClassLoader("com.liferay.cluster.test.module_3.0.0");

	Class<?> clazz = testModuleClassLoader.loadClass("com.liferay.cluster.test.module.internel.ClusterTestClass");

	Constructor<?> constructor = clazz.getConstructor(String.class);

	PortalCache testPortalCache = PortalCacheHelperUtil.getPortalCache(PortalCacheManagerNames.MULTI_VM, "test.cache");

	testPortalCache.put("test.key", constructor.newInstance("test.value"));
}
catch (Exception e) {
	out.println(e);
}