#!/bin/sh
set -e

KAFKA_CONFIG_FILE="/opt/kafka/config/kraft/server.properties"
KAFKA_DATA_DIR="/kafka/data"
KAFKA_HOME_BIN="/opt/kafka/bin"

if [ ! -f "$KAFKA_DATA_DIR/meta.properties" ]; then
    echo "Formatting KRaft storage..."
    "$KAFKA_HOME_BIN/kafka-storage.sh" format -t "$("$KAFKA_HOME_BIN/kafka-storage.sh" random-uuid)" -c "$KAFKA_CONFIG_FILE"
fi

echo "Starting Kafka in KRaft mode..."
exec "$KAFKA_HOME_BIN/kafka-server-start.sh" "$KAFKA_CONFIG_FILE"
