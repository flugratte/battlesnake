FROM golang:alpine

RUN go version && go get github.com/BattlesnakeOfficial/rules/cli/battlesnake

COPY ./wait-for.sh /wait-for.sh

ENTRYPOINT [ "/bin/sh" ]