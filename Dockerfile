FROM node:latest AS builder

COPY client/ /tmp/client/
RUN cd /tmp/client/ && npm install 
RUN cd /tmp/client/ && npm run build

####################################

FROM openjdk:8u171-alpine3.8

ENV SCALA_VERSION 2.12.6
ENV SBT_VERSION 1.2.1

ENV PATH /sbt/bin:$PATH

RUN apk add -U bash docker

# Install Scala
## Piping curl directly in tar
RUN \
  wget -O - https://downloads.typesafe.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz | tar xfz - -C /root/ && \
  echo >> /root/.bashrc && \
  echo "export PATH=~/scala-$SCALA_VERSION/bin:$PATH" >> /root/.bashrc

# Install SBT
RUN wget https://piccolo.link/sbt-$SBT_VERSION.tgz && \
  tar -xzvf sbt-$SBT_VERSION.tgz && \
  sbt sbtVersion

# caching dependencies
COPY ["server/build.sbt", "/tmp/build/"]
COPY ["server/project/plugins.sbt", "server/project/build.properties", "/tmp/build/project/"]
RUN cd /tmp/build && \
  sbt compile && \
  sbt test:compile && \
  rm -rf /tmp/build

COPY server/ /root/app/
WORKDIR /root/app
COPY --from=builder  /tmp/client/public/* public/ 
COPY --from=builder  /tmp/client/public/build/* public/build/ 

EXPOSE 80
CMD sbt "start -Dhttp.port=80"