if(com.liferay.portal.kernel.cluster.ClusterMasterExecutorUtil.isMaster()){
	out.println("This node is master node");}
else {
	out.println("This node is slave node");}