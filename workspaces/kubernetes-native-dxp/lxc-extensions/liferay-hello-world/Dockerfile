FROM caddy:2.4.6

COPY build /static
COPY Caddyfile /etc/caddy/Caddyfile

RUN \
    caddy fmt --overwrite /etc/caddy/Caddyfile && \
    caddy validate --config /etc/caddy/Caddyfile && \
    cat /etc/caddy/Caddyfile

EXPOSE 8080/tcp
