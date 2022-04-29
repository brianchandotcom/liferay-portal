FROM curlimages/curl:7.82.0

COPY ca.crt curl.sh ./

USER root
RUN apk --no-cache add jq
USER curl_user

ENTRYPOINT [ "./curl.sh" ]