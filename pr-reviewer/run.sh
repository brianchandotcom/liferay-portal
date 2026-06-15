#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"

function main {
	if [[ $(uname --nodename) == sandbox ]]
	then
		echo "Do not run this inside the AI sandbox."

		exit 1
	fi

	mkdir --parents /tmp/pr-reviewer

	local pr_subdir

	for pr_subdir in /tmp/pr-reviewer/*
	do
		if [ -d ${pr_subdir} ] && _is_older_than ${pr_subdir} $((3 * 24 * 3600))
		then
			rm --force --recursive ${pr_subdir}
		fi
	done

	local closed_prs=""
	local command=${1:-}
	local dry_run=false
	local pr_closed=false
	local pr_locked=false
	local pr_number=${2:-}

	if [[ ${command} == --dry-run ]]
	then
		dry_run=true

		shift

		command=${1:-}
		pr_number=${2:-}
	fi

	local pr_dir=/tmp/pr-reviewer/${pr_number}

	if [[ ${command} == check ]]
	then
		if [[ -z ${pr_number} ]]
		then
			clear

			while true
			do
				local closed_prs_count=0
				local reviewed=false

				for pr_number in $(gh api --paginate "repos/${_REPO}/pulls?state=open" | jq --raw-output ".[].number")
				do
					pr_dir=/tmp/pr-reviewer/${pr_number}

					_check_pr

					if ${pr_closed}
					then
						((++closed_prs_count))
					fi
				done

				if [ ${closed_prs_count} -eq 1 ]
				then
					echo "Closed 1 PR at $(date +"%-l:%M %P")."
				else
					echo "Closed ${closed_prs_count} PRs at $(date +"%-l:%M %P")."
				fi

				if ! ${reviewed}
				then
					sleep $(((5 * 60) + (RANDOM % (5 * 60))))
				fi
			done
		else
			local reviewed=false

			_check_pr
		fi
	elif [[ ${command} == kill ]]
	then
		local pid

		for pid in $(pgrep --full "run\.sh")
		do
			if [ ${pid} -ne $$ ] && [[ $(readlink /proc/${pid}/cwd 2> /dev/null) = ${PWD} ]]
			then
				echo "Killing ${pid}."

				kill ${pid} 2> /dev/null || true
			fi
		done

		for pr_subdir in /tmp/pr-reviewer/*
		do
			if [ -d ${pr_subdir}/.lock ]
			then
				echo "Removing ${pr_subdir}."

				rm --force --recursive ${pr_subdir}
			fi
		done

		local ref

		for ref in $(git for-each-ref --format="%(refname)" refs/pr-reviewer/ 2> /dev/null)
		do
			if [ ! -d /tmp/pr-reviewer/${ref#refs/pr-reviewer/} ]
			then
				echo "Removing orphan Git reference ${ref}."

				git update-ref -d ${ref}
			fi
		done
	elif [[ ${command} == review ]]
	then
		if [[ -z ${pr_number} ]]
		then
			_print_help
		fi

		_get_automatic_code_review_json
	else
		_print_help
	fi
}

function _acquire_pr_lock {
	mkdir --parents ${pr_dir}

	if _is_older_than ${pr_dir}/.lock $(((_REVIEW_TIMEOUT_MINUTES + 1) * 60))
	then
		rmdir ${pr_dir}/.lock 2> /dev/null || true
	fi

	if ! mkdir ${pr_dir}/.lock 2> /dev/null
	then
		return 1
	fi

	pr_locked=true
}

function _check_pr {
	pr_closed=false

	if [[ " ${closed_prs} " == *" ${pr_number} "* ]]
	then
		return 0
	fi

	if [ -f ${pr_dir}/.reviewed ]
	then
		return 0
	fi

	if ! _acquire_pr_lock
	then
		echo "Skipping locked ${pr_number}."

		return 0
	fi

	trap _release_pr_lock RETURN

	echo -n "Checking ${pr_number}."

	local mergeable="" mergeable_state="" pr_json="" rebaseable="" state=""

	_retry 5 _check_pr_mergeability || true

	if [[ -z ${state} ]]
	then
		echo ""
		echo "Unable to fetch: $(echo "${pr_json}" | tr "\n" " " | head --bytes=200)"
		echo ""

		return 0
	fi

	if [[ ${state} != open ]]
	then
		closed_prs+=" ${pr_number}"

		echo ""
		echo "Skipping closed ${pr_number}."
		echo ""

		return 0
	fi

	if [[ ${mergeable} == false ]] || \
	   [[ ${mergeable_state} == dirty ]] || \
	   [[ ${rebaseable} == false ]]
	then
		echo ""
		echo "Closing unmergeable ${pr_number}:"

		local base_ref=$(echo "${pr_json}" | jq --raw-output ".base.ref")

		_fetch_pr || true

		local conflicts

		conflicts=$(git merge-tree --name-only --write-tree origin/${base_ref} refs/pr-reviewer/${pr_number} 2> /dev/null | sed --quiet "2,/^$/{/^$/!p}" | head --lines=30) || true

		if [[ -n ${conflicts} ]]
		then
			echo "${conflicts}" | sed "s/^/    /"
		fi

		echo ""

		if ! ${dry_run}
		then
			gh pr close ${pr_number} --comment "Resend this PR because it has rebase errors." --repo ${_REPO}

			closed_prs+=" ${pr_number}"
		fi

		echo ""

		pr_closed=true

		return 0
	fi

	if gh api "repos/${_REPO}/issues/${pr_number}/comments" \
		--jq ".[].body" 2>&1 | grep --quiet "#bchan-bot-pr-review"
	then
		touch ${pr_dir}/.reviewed

		return 0
	fi

	reviewed=true

	local automatic_code_review_json

	if ! automatic_code_review_json=$(_get_automatic_code_review_json)
	then
		return 0
	fi

	local index
	local max_chance=$(echo "${automatic_code_review_json}" | jq "map(.chance) | max")
	local models_count=$(echo "${automatic_code_review_json}" | jq "length")

	local usernames

	usernames=$(
		{
			gh api "repos/${_REPO}/issues/${pr_number}/comments" --jq ".[].user.login | select(. != null)" 2> /dev/null || true
			gh api "repos/${_REPO}/pulls/${pr_number}/comments" --jq ".[].user.login | select(. != null)" 2> /dev/null || true
			gh api "repos/${_REPO}/pulls/${pr_number}/commits" --jq ".[].author.login | select(. != null)" 2> /dev/null || true
			gh api "repos/${_REPO}/pulls/${pr_number}/reviews" --jq ".[].user.login | select(. != null)" 2> /dev/null || true
		} | grep --invert-match liferay-continuous-integration | sort --unique | tr "[:upper:]" "[:lower:]"
	) || true

	local at_usernames=$(echo "${usernames}" | sed "s/^/@/" | tr "\n" " " | sed "s/ *$//")

	if [ ${models_count} -eq 0 ]
	then
		local body="${at_usernames}"$'\n\n'"Brian will most likely merge this PR."

		echo ""
		echo "${body}"
		echo ""

		if ! ${dry_run}
		then
			gh pr comment ${pr_number} \
				--body "${body}"$'\n\n'"#bchan-bot-pr-review" \
				--repo ${_REPO}

			touch ${pr_dir}/.reviewed

			echo ""
		fi

		return 0
	fi

	local body="${at_usernames}"$'\n\n'"There is $(_get_indefinite_article_for_number "${max_chance}") ${max_chance}% chance that Brian will reject this PR."

	for ((index = 0; index < models_count; index++))
	do
		local chance=$(echo "${automatic_code_review_json}" | jq --raw-output ".[${index}].chance")

		if [ ${chance} -eq 0 ]
		then
			continue
		fi

		local input_tokens=$(echo "${automatic_code_review_json}" | jq --raw-output ".[${index}].input_tokens // 0")
		local model=$(echo "${automatic_code_review_json}" | jq --raw-output ".[${index}].model")
		local output_tokens=$(echo "${automatic_code_review_json}" | jq --raw-output ".[${index}].output_tokens // 0")
		local seconds=$(echo "${automatic_code_review_json}" | jq --raw-output ".[${index}].seconds // 0")
		local violations=$(echo "${automatic_code_review_json}" | jq --raw-output ".[${index}].violations[]" | sed "s/^/- /")

		if [[ -n ${body} ]]
		then
			body+=$'\n\n'
		fi

		body+="${model} (${chance}% chance of rejection, $(_format_tokens ${input_tokens})/$(_format_tokens ${output_tokens}) tokens, ${seconds}s):"$'\n'"${violations}"
	done

	echo ""
	echo "${body}"
	echo ""

	if ! ${dry_run}
	then
		gh pr comment ${pr_number} \
			--body "${body}"$'\n\n'"#bchan-bot-pr-review" \
			--repo ${_REPO}

		touch ${pr_dir}/.reviewed

		echo ""
	fi

	if true
	then
		return 0
	elif [ ${max_chance} -gt 50 ]
	then
		echo "Closing ${pr_number} (${max_chance}% dirty)."
		echo ""

		if ! ${dry_run}
		then
			gh pr close ${pr_number} \
				--comment "Closing PR because ${max_chance}% dirty exceeds the 50% threshold. ${at_usernames} lose 5 points."$'\n\n'"#bchan-bot-pr-review" \
				--repo ${_REPO}

			closed_prs+=" ${pr_number}"

			echo ""

			_update_points -5 "${usernames}"
		fi
	elif [ ${max_chance} -lt 10 ]
	then
		if ! ${dry_run}
		then
			gh pr comment ${pr_number} \
				--body "${at_usernames} gain 1 point for a clean PR."$'\n\n'"#bchan-bot-pr-review" \
				--repo ${_REPO}

			echo ""

			_update_points 1 "${usernames}"
		fi
	fi

	return 0
}

function _check_pr_mergeability {
	if ! pr_json=$(gh api "repos/${_REPO}/pulls/${pr_number}" 2>&1)
	then
		return 1
	fi

	state=$(echo "${pr_json}" | jq --raw-output ".state")

	if [[ ${state} != open ]]
	then
		echo ""

		return 0
	fi

	mergeable=$(echo "${pr_json}" | jq --raw-output ".mergeable")
	mergeable_state=$(echo "${pr_json}" | jq --raw-output ".mergeable_state")
	rebaseable=$(echo "${pr_json}" | jq --raw-output ".rebaseable")

	if [[ ${mergeable} != null ]]
	then
		echo ""

		return 0
	fi

	echo -n .

	return 1
}

function _extract_last_json_block {
	awk '
		BEGIN {
			brace_depth = 0
			current_block = ""
			inside_string = 0
			last_block = ""
			next_char_is_escaped = 0
		}
		{
			if (brace_depth > 0) {
				current_block = current_block "\n"
			}

			line_length = length($0)

			for (position = 1; position <= line_length; position++) {
				character = substr($0, position, 1)

				if (inside_string) {
					if (brace_depth > 0) {
						current_block = current_block character
					}

					if (next_char_is_escaped) {
						next_char_is_escaped = 0
					}
					else if (character == "\\") {
						next_char_is_escaped = 1
					}
					else if (character == "\"") {
						inside_string = 0
					}

					continue
				}

				if (character == "\"") {
					inside_string = 1

					if (brace_depth > 0) {
						current_block = current_block character
					}

					continue
				}

				if (character == "{") {
					if (brace_depth == 0) {
						current_block = ""
					}

					brace_depth = brace_depth + 1
					current_block = current_block character

					continue
				}

				if (character == "}") {
					brace_depth = brace_depth - 1
					current_block = current_block character

					if (brace_depth == 0) {
						last_block = current_block
					}

					continue
				}

				if (brace_depth > 0) {
					current_block = current_block character
				}
			}
		}
		END {
			print last_block
		}
	'
}

function _fetch_pr {
	timeout 60 git fetch --quiet --force origin pull/${pr_number}/head:refs/pr-reviewer/${pr_number} 2> /dev/null
}

function _format_tokens {
	local number=${1}

	if ((number >= 1000))
	then
		echo "$(((number + 500) / 1000))k"
	else
		echo ${number}
	fi
}

function _get_automatic_code_review_json {
	if ! ${pr_locked}
	then
		if ! _acquire_pr_lock
		then
			echo "Another process is reviewing ${pr_number}." >&2

			exit 1
		fi

		trap _release_pr_lock RETURN
	fi

	if ! _retry 5 _fetch_pr
	then
		echo "Unable to fetch ${pr_number}." >&2

		rm --force ${pr_dir}/*

		return 1
	fi

	local diff_file

	local diff_range=refs/pr-reviewer/${pr_number}..refs/pr-reviewer/${pr_number}

	local from_commit=$(git merge-base master refs/pr-reviewer/${pr_number} 2> /dev/null)

	if [[ -n ${from_commit} ]]
	then
		diff_range="${from_commit}..refs/pr-reviewer/${pr_number}"
	fi

	local generated_files=""

	for diff_file in $(git diff --name-only ${diff_range} || true)
	do
		if git grep --ignore-case --quiet "@generated" refs/pr-reviewer/${pr_number} -- ":(top)${diff_file}" 2> /dev/null
		then
			generated_files+="|${diff_file}"
		fi
	done

	rm --force ${pr_dir}/*

	git diff --unified=1 ${diff_range} | awk \
		-v generated_files="${generated_files}|" \
		-v ignored_filenames="${_IGNORED_FILENAMES}" \
		-v ignored_patterns="${_IGNORED_PATTERNS}" \
		-v ignored_suffixes="${_IGNORED_SUFFIXES}" \
		-v name_only_suffixes="${_NAME_ONLY_SUFFIXES}" '
		BEGIN {
			split(ignored_filenames, filenames, " ")
			split(ignored_patterns, patterns, " ")
			split(ignored_suffixes, suffixes, " ")
			split(name_only_suffixes, name_only_list, " ")
		}
		/^diff --git / {
			file = substr($3, 3)

			skip = index(generated_files, "|" file "|") > 0
			name_only = 0

			for (i in filenames) {
				if (file ~ ("(^|/)" filenames[i] "$")) {
					skip = 1
				}
			}

			for (i in patterns) {
				if (file ~ patterns[i]) {
					skip = 1
				}
			}

			for (i in suffixes) {
				if (file ~ ("[.]" suffixes[i] "$")) {
					skip = 1
				}
			}

			for (i in name_only_list) {
				if (file ~ ("[.]" name_only_list[i] "$")) {
					name_only = 1
				}
			}

			if (! skip) {
				print
			}

			next
		}
		! skip && ! name_only
	' > ${pr_dir}/pr.diff

	if [ ! -s ${pr_dir}/pr.diff ]
	then
		echo "[]"

		return 0
	fi

	local pids=()

	for model in "${_MODELS[@]}"
	do
		_write_model_json_file ${model} &

		pids+=($!)
	done

	for pid in "${pids[@]}"
	do
		wait "${pid}" || true
	done

	local automatic_code_review_json="[]"

	for model in "${_MODELS[@]}"
	do
		local model_json=$(cat ${pr_dir}/${model}.json 2> /dev/null) || model_json=""

		if [[ -z ${model_json} ]] || ! echo "${model_json}" | jq . > /dev/null 2>&1
		then
			model_json="{\"chance\": 0, \"seconds\": 0, \"violations\": []}"
		fi

		automatic_code_review_json=$(echo "${automatic_code_review_json}" | jq --arg model "${model}" --argjson model_json "${model_json}" ". + [\$model_json + {model: \$model}]")
	done

	echo "${automatic_code_review_json}"
}

function _get_indefinite_article_for_number {
	local number=${1}

	if [[ ${number} =~ ^(8|11|18|8[0-9])$ ]]
	then
		echo an
	else
		echo a
	fi
}

function _is_older_than {
	local file=${1}
	local seconds=${2}

	local modified_time=$(stat --format=%Y ${file} 2> /dev/null)

	[ $(($(date +%s) - ${modified_time:-0})) -gt ${seconds} ]
}

function _print_help {
	echo "Usage:"
	echo "    ${0} [--dry-run] check [pr]    Check all open PRs or just [pr]"
	echo "    ${0} kill                      Kill running processes and remove locked PR dirs"
	echo "    ${0} [--dry-run] review <pr>   Review <pr> and print JSON"

	exit 1
}

function _release_pr_lock {
	rmdir ${pr_dir}/.lock 2> /dev/null || true

	pr_locked=false
}

function _retry {
	local attempts=${1}

	shift

	local index

	for ((index = 0; index < attempts; index++))
	do
		if "$@"
		then
			return 0
		fi

		sleep $((5 * (index + 1)))
	done

	return 1
}

function _review_in_sandbox {
	timeout \
		--kill-after=10s ${_REVIEW_TIMEOUT_MINUTES}m \
		\
		bwrap \
			--as-pid-1 \
			--bind /home/me/.ai_sandbox/home /home/me \
			--chdir /tmp \
			--clearenv \
			--dev /dev \
			--die-with-parent \
			--hostname sandbox \
			--proc /proc \
			--ro-bind "$(pwd)/STYLE.md" /review/STYLE.md \
			--ro-bind "$(pwd)/rules" /review/rules \
			--ro-bind "$(pwd)/sandbox-bin" /review/sandbox-bin \
			--ro-bind /home/me/dev/projects/liferay-portal /review/liferay-portal \
			--ro-bind ${pr_dir}/pr.diff /review/pr.diff \
			--ro-bind /etc /etc \
			--ro-bind /usr /usr \
			--setenv HOME /home/me \
			--setenv LANG en_US.UTF-8 \
			--setenv PATH /review/sandbox-bin:/home/me/.local/bin:/home/me/.npm-global/bin:/usr/bin:/bin \
			--setenv TERM xterm-256color \
			--setenv USER me \
			--symlink usr/bin /bin \
			--symlink usr/lib /lib \
			--symlink usr/lib64 /lib64 \
			--symlink usr/sbin /sbin \
			--tmpfs /run \
			--tmpfs /tmp \
			--ro-bind $(readlink --canonicalize /etc/resolv.conf) $(readlink --canonicalize /etc/resolv.conf) \
			--unshare-cgroup \
			--unshare-ipc \
			--unshare-pid \
			--unshare-uts \
			"$@"
}

function _update_points {
	local delta=${1}
	local usernames=${2}

	for username in ${usernames}
	do
		local current_points=$(grep "^${username}=" points.properties 2> /dev/null | cut --delimiter== --fields=2)

		if [[ -z ${current_points} ]]
		then
			current_points=80
		fi

		local new_points=$((current_points + delta))

		if [ ${new_points} -lt 0 ]
		then
			new_points=0
		elif [ ${new_points} -gt 100 ]
		then
			new_points=100
		fi

		if grep --quiet "^${username}=" points.properties 2> /dev/null
		then
			sed --in-place "s/^${username}=.*/${username}=${new_points}/" points.properties
		else
			echo "${username}=${new_points}" >> points.properties
		fi
	done

	sort --output points.properties points.properties

	truncate --size=-1 points.properties
}

function _write_model_json_file {
	local model=${1}

	local prompt='Review the PR diff thoroughly. For any naming, ordering, or convention question, run `git grep --cached <pattern>` against /review/liferay-portal before deciding — never short circuit by returning empty after only reading the diff. Always pass --cached: plain `git grep` hangs the VM (a wrapper auto rewrites it, but pass --cached yourself).

For any claim about where a line sits in the diff (between X and Y, after X, before Y, at line N), name X and Y exactly as they appear on the lines immediately above and below the `+` line in the diff. Re-read the diff before writing the claim; do not name neighbors from memory or paraphrase. Position claims that misname the neighbors are hallucinations — the alphabetical reasoning can be right while the position is fabricated, and that combination reads as well supported when it is not.

Flag every rule violation you find. When confidence in a flag is partial, still include it and append `verify against <source>` so the human can confirm — do not drop the catch.

Output ONLY valid JSON, with no Markdown code fence and no surrounding prose: {"chance": <0-100>, "violations": ["one description per violation"]} where chance is your confidence that Brian Chan would close this PR for these violations.'
	local input_tokens=0
	local output_tokens=0
	local raw
	local response
	local seconds=$(date +%s)

	if [ ${model} = sonnet-4.6 ]
	then
		raw=$(_review_in_sandbox \
			env \
				HTTPS_PROXY=localhost:8118 \
				HTTP_PROXY=localhost:8118 \
				\
				claude \
					--add-dir /review \
					--dangerously-skip-permissions \
					--model sonnet \
					--output-format json \
					--print "Read the PR diff at /review/pr.diff, every rule file under /review/rules, and the style guide /review/STYLE.md, then review the diff against every rule. ${prompt}" || true)

		response=$(echo "${raw}" | jq --raw-output ".result // empty" 2> /dev/null || true)
		input_tokens=$(echo "${raw}" | jq --raw-output "(.usage.input_tokens // 0) + (.usage.cache_creation_input_tokens // 0)" 2> /dev/null || echo 0)
		input_tokens=${input_tokens:-0}
		output_tokens=$(echo "${raw}" | jq --raw-output ".usage.output_tokens // 0" 2> /dev/null || echo 0)
		output_tokens=${output_tokens:-0}
	else
		raw=$(_review_in_sandbox \
			opencode run \
				--dangerously-skip-permissions \
				--file /review/STYLE.md \
				--file /review/pr.diff \
				--file /review/rules \
				--format json \
				--model "opencode-go/${model}" \
				"Review this PR diff against every attached rule. ${prompt}" || true)

		response=$(echo "${raw}" | jq --raw-output --slurp "map(select(.type == \"text\")) | last | .part.text // empty" 2> /dev/null || true)
		input_tokens=$(echo "${raw}" | jq --raw-output --slurp "[.[] | select(.type == \"step_finish\") | (.part.tokens.input // 0) + (.part.tokens.cache.write // 0)] | add // 0" 2> /dev/null || echo 0)
		input_tokens=${input_tokens:-0}
		output_tokens=$(echo "${raw}" | jq --raw-output --slurp "[.[] | select(.type == \"step_finish\") | (.part.tokens.output // 0) + (.part.tokens.reasoning // 0)] | add // 0" 2> /dev/null || echo 0)
		output_tokens=${output_tokens:-0}
	fi

	echo "${raw}" > "${pr_dir}/${model}.raw"

	local model_json=$(echo "${response}" | _extract_last_json_block)

	if model_json=$(echo "${model_json}" | jq \
		--argjson input_tokens "${input_tokens}" \
		--argjson output_tokens "${output_tokens}" \
		--argjson seconds "$(($(date +%s) - seconds))" \
		--slurp "(.[-1] // empty) | {chance: (.chance // 0), input_tokens: \$input_tokens, output_tokens: \$output_tokens, seconds: \$seconds, violations: (.violations // [])}" 2> /dev/null) && [[ -n ${model_json} ]]
	then
		echo "${model_json}" > "${pr_dir}/${model}.json"
	else
		echo "${model} failed after $(($(date +%s) - seconds))s: $(echo "${response}" | tr "\n" " " | head --bytes=300)" >&2

		jq --null-input \
			--arg error "${response}" \
			--argjson input_tokens "${input_tokens}" \
			--argjson output_tokens "${output_tokens}" \
			--argjson seconds "$(($(date +%s) - seconds))" \
			"{chance: 0, error: \$error, input_tokens: \$input_tokens, output_tokens: \$output_tokens, seconds: \$seconds, violations: []}" > "${pr_dir}/${model}.json"
	fi
}

_IGNORED_FILENAMES="CHANGELOG.md package-lock.json package.json"
_IGNORED_PATTERNS="(^|/)Language_.*[.]properties$"
_IGNORED_SUFFIXES="css js jsx lock lockfile macro path scss snap testcase ts tsx"
#_MODELS=(deepseek-v4-flash deepseek-v4-pro glm-5 mimo-v2-5 minimax-m2-7)
_MODELS=(sonnet-4.6)
_NAME_ONLY_SUFFIXES="bmp gif ico jpeg jpg png svg webp"
_REPO=brianchandotcom/liferay-portal
_REVIEW_TIMEOUT_MINUTES=20

main "${@}"