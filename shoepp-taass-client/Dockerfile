#### STAGE 1: Build ###
FROM node:12.11.1-alpine AS build
#ARG NPM_TOKEN
ARG APPLICATION
WORKDIR /usr/src/app
ENV NODE_OPTIONS=--max_old_space_size=8096
COPY package.json package-lock.json ./
#RUN echo _auth=""${NPM_TOKEN}"" > ~/.npmrc && \
#    echo "always-auth=true" >> ~/.npmrc && \
#    echo "email=dchristilaw@trustwork.com" >> ~/.npmrc && \
#    echo "registry=https://mtech.jfrog.io/mtech/api/npm/mtech-npm-virtual/" >> ~/.npmrc && \
#    cat ~/.npmrc && \
#    npm install && \
#    npm install  @angular/cli && \
#    rm -f /app/.npmrc
RUN npm install 
#&& \
#    npm install  @angular/cli
COPY . .
RUN node_modules/.bin/ng build
RUN ls -ltrh dist


#### STAGE 2: Run ###
FROM nginx:1.20.0-alpine
#ARG APPLICATION
#COPY nginx.conf /etc/nginx/nginx.conf
#COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist/client /usr/share/nginx/html
#COPY dist/${APPLICATION} /usr/share/nginx/html
RUN ls -ltrh /usr/share/nginx/html