import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterNodeResponses;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalUtil;

ClusterNode localClusterNode = ClusterExecutorUtil.getLocalClusterNode();

ClusterNode targetClusterNode = null;

for (ClusterNode clusterNode : ClusterExecutorUtil.getClusterNodes()) {
	if (!clusterNode.equals(localClusterNode)) {
		targetClusterNode = clusterNode;

		break;
	}
}

if (targetClusterNode == null) {
	out.println("Unable to find another cluster node");

	return;
}

MethodKey methodKey = new MethodKey(
	PortalUtil.class, "getPortalLocalPort", boolean.class);

MethodHandler methodHandler = new MethodHandler(methodKey,false);

ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
	methodHandler, targetClusterNode.getClusterNodeId());

FutureClusterResponses futureClusterResponses =
	ClusterExecutorUtil.execute(clusterRequest);

try {
	ClusterNodeResponses clusterNodeResponses =
		futureClusterResponses.get();

	ClusterNodeResponse clusterNodeResponse =
		clusterNodeResponses.getClusterResponse(
			targetClusterNode.getClusterNodeId());

	out.println(
		"Result of invoke-method-portal is :" + clusterNodeResponse.getResult());
}
catch (Exception e) {
	out.println(e);
}