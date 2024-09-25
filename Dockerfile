#поменять среду выполнения ??
FROM ubuntu:latest
LABEL authors="andrey"

ENTRYPOINT ["top", "-b"]