FROM node:17-alpine AS build

RUN mkdir /work
WORKDIR /work

COPY src ./src
COPY public ./public
COPY package.json ./
COPY node_modules ./node_modules
COPY package-lock.json ./
COPY yarn.lock ./

RUN \
    apk add tree && \
    tree -I node_modules && \
    yarn install && \
    yarn build

FROM caddy:2.4.6

COPY --from=build /work/build /static
COPY Caddyfile /etc/caddy/Caddyfile

RUN \
    caddy fmt --overwrite /etc/caddy/Caddyfile && \
    caddy validate --config /etc/caddy/Caddyfile && \
    cat /etc/caddy/Caddyfile

EXPOSE 8080/tcp
