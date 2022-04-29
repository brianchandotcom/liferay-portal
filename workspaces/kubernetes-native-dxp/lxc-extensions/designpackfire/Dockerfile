FROM caddy:2.4.6

COPY dist/designpackfire.zip /static/designpackfire.zip
COPY Caddyfile /etc/caddy/Caddyfile

RUN \
  unzip /static/designpackfire.zip -d /static && \
  rm /static/designpackfire.zip

RUN \
	caddy fmt --overwrite /etc/caddy/Caddyfile && \
	caddy validate --config /etc/caddy/Caddyfile && \
	cat /etc/caddy/Caddyfile

EXPOSE 8080/tcp