FROM ghcr.io/graalvm/native-image:22.3.1 AS builder
WORKDIR /project
COPY Echo.java Echo.java
RUN ["javac", "-d", "classes", "--release", "17", "Echo.java"]
RUN ["native-image", "--static", "-cp", "classes", "Echo"]

FROM alpine:3.17.2
WORKDIR /app
EXPOSE 8080
COPY --from=builder /project/echo echo
CMD ["/app/echo"]
