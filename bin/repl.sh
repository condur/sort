#!/bin/bash

# Feel free to change this
APP_NAME=sort

# Comment this to disable the production REPL, otherwise use it to specify the port.
SOCKET_REPL_PORT=50505

# The -Xms JVM argument
INIT_MEMORY=256m

# The -Xmx JVM argument
MAX_MEMORY=1200m

if [[ -n $SOCKET_REPL_PORT ]]; then
    repl_arg=-J-Dclojure.server.myrepl="{:port,$SOCKET_REPL_PORT,:accept,clojure.core.server/repl,:address,\"localhost\"}"
fi

echo "Starting $APP_NAME"

COMMAND="clojure $repl_arg -J-Djava.awt.headless=true -J-Xms$INIT_MEMORY -J-Xmx$MAX_MEMORY"

echo $COMMAND

exec $COMMAND
