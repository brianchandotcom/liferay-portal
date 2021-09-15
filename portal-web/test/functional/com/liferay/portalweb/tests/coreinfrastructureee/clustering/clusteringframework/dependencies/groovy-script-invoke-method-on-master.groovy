import com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalUtil;
import java.util.concurrent.Future;

MethodKey methodKey = new MethodKey(
	PortalUtil.class, "getPortalLocalPort", boolean.class);

MethodHandler methodHandler = new MethodHandler(methodKey,false);

Future<Integer> future = ClusterMasterExecutorUtil.executeOnMaster(methodHandler);

try {
	out.println("Result of invoke-method-portal-on-master is :" + future.get());
}
catch (Exception e) {
	out.println(e);
}