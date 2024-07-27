FROM vanto/apache-buildr:latest-jruby-jdk8 as builder

WORKDIR /workspace

COPY  bin .

RUN buildr compile

FROM alpine:edge

RUN adduser -D developer

ENV DISPLAY :0

RUN apk update \
    && apk add openjdk11

RUN apk --no-cache add msttcorefonts-installer fontconfig && \
    update-ms-fonts && \
    fc-cache -f

USER developer

WORKDIR /home/developer

COPY --from=builder /workspace/target/classes .

CMD ["java", "example.Main"]