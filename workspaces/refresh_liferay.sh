#!/bin/bash

function main {
	for dir in ./liferay-*workspace
	do
		if [ -e ${dir}.temp ]
		then
			echo "${dir}.temp already exists."

			exit 1
		fi

		mv ${dir} ${dir}.temp

		cp -R sample-minimal-workspace/ ${dir}

		rm -fr ${dir}/client-extensions/

		if [ -e ${dir}.temp/client-extensions ]
		then
			cp -R ${dir}.temp/client-extensions/ ${dir}
		else
			mkdir -p ${dir}/client-extensions
		fi

		rm -fr ${dir}.temp
	done
}

main "${@}"