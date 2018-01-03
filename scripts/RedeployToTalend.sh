#!/bin/bash

# This script is used for redeploying the components during the development.
#
# Deploy process can be found at https://help.talend.com/reader/99uNhyKAYtzK~Gc29xeUSQ/xjeUGCLFdPIkR46ha78wxA
#
# It actually automates the recurring steps:
#
# #3: Copies the component definition inside the <Studio home>/plugins/ folder.
# #6: Copies the component runtime into the Talend Studio's Maven repository: <Studio home>/configuration/.m2/
# #7: Deletes any folders which start their names with "org.eclipse" from <Studio home>/configuration
#
# How to use this scrip
#
# Set the variables in the "Public section" below according to your environment settings and run.
#
# Note: It only works if the recommended component structure and naming are followed
#
# Author:	Zoltán Takács
# Updated:	2017.12.20

echo "Started:" `date`
echo

### --- ### ### --- ### Public section ### --- ### ### --- ### 
#
# Set the variables according to your environment settings

TOS_HOME="/d/Engineering/TOS/TOS_ESB-20170623_1246-V6.4.1/Studio/"

COMPONENT_NAME="components-liferay"
COMPONENT_GROUP_ID_TO_PATH="com/liferay/consumer/"
COMPONENT_VERSION="0.1.0-SNAPSHOT"

MAVEN_LOCAL_HOME="/c/Users/zolta/.m2/repository/"
MAVEN_TOS_HOME="$TOS_HOME""configuration/.m2/repository/"

### --- ### ### --- ### End of public section ### --- ### ### --- ### 

_TOS_M2_COMPONENT_PATH="$MAVEN_TOS_HOME""$COMPONENT_GROUP_ID_TO_PATH"
_LOCAL_M2_COMPONENT_PATH="$MAVEN_LOCAL_HOME""$COMPONENT_GROUP_ID_TO_PATH"

# Delete the deployed artifacts from the TOS Maven repo

if test "$(ls -A "$_TOS_M2_COMPONENT_PATH")"; 
	then
		find "$_TOS_M2_COMPONENT_PATH"/* -print0  | xargs -0  rm -rf
	else
		echo "The directory ""$_TOS_M2_COMPONENT_PATH"" is empty (or non-existent)"
fi

# Copy the built artifacts from Local Maven to Talend's Maven

cp -rf "$_LOCAL_M2_COMPONENT_PATH" "$(dirname "$_TOS_M2_COMPONENT_PATH")"

# Copy the component definition to the plugins folder

cp -f "$MAVEN_LOCAL_HOME""$COMPONENT_GROUP_ID_TO_PATH""$COMPONENT_NAME""-definition/""$COMPONENT_VERSION""/""$COMPONENT_NAME""-definition-""$COMPONENT_VERSION"".jar" "$TOS_HOME""/plugins/"

# Delete the org.eclipse directories from the configuration folder

find "$TOS_HOME""configuration/org.eclipse."* -print0  | xargs -0  rm -rf

echo
echo "Finished:" `date`
echo