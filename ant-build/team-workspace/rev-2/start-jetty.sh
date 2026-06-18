#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$SCRIPT_DIR"
WAR_FILE="$PROJECT_DIR/build/libs/lab3-1.0-SNAPSHOT.war"
JETTY_BASE="${JETTY_BASE:-$PROJECT_DIR/jetty-base}"
JETTY_HOME="${JETTY_HOME:-}"
JETTY_RUNNER_VERSION="${JETTY_RUNNER_VERSION:-9.4.53.v20231009}"
JETTY_RUNNER_JAR="${JETTY_RUNNER_JAR:-$PROJECT_DIR/tools/jetty-runner-${JETTY_RUNNER_VERSION}.jar}"
JETTY_PORT="${JETTY_PORT:-8080}"

if [ ! -f "$WAR_FILE" ]; then
    echo "WAR не найден, запускаю сборку..."
    "$PROJECT_DIR/gradlew" clean war
fi

if [ -z "$JETTY_HOME" ]; then
    mkdir -p "$(dirname "$JETTY_RUNNER_JAR")"
    if [ ! -f "$JETTY_RUNNER_JAR" ]; then
        echo "Скачиваю jetty-runner ${JETTY_RUNNER_VERSION}..."
        curl -fsSL "https://repo1.maven.org/maven2/org/eclipse/jetty/jetty-runner/${JETTY_RUNNER_VERSION}/jetty-runner-${JETTY_RUNNER_VERSION}.jar" -o "$JETTY_RUNNER_JAR"
    fi
    echo "Запуск через jetty-runner на порту ${JETTY_PORT}"
    exec java -jar "$JETTY_RUNNER_JAR" --port "$JETTY_PORT" "$WAR_FILE"
fi

if [ ! -f "$JETTY_HOME/start.jar" ]; then
    echo "Ошибка: start.jar не найден в $JETTY_HOME"
    exit 1
fi

mkdir -p "$JETTY_BASE/webapps"
cp "$WAR_FILE" "$JETTY_BASE/webapps/lab3.war"

echo "Запуск Jetty из $JETTY_HOME"
echo "JETTY_BASE: $JETTY_BASE"
export JETTY_HOME
export JETTY_BASE
exec java -jar "$JETTY_HOME/start.jar"

