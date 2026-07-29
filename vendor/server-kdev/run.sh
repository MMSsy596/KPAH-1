#!/usr/bin/env bash
set -u

APP_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$APP_DIR" || exit 1

JAR_FILE="${JAR_FILE:-kpahpau.jar}"
LOG_FILE="${LOG_FILE:-game.log}"
WATCHDOG_LOG="${WATCHDOG_LOG:-watchdog.log}"
PID_DIR="${PID_DIR:-run}"
WATCHDOG_PID_FILE="$PID_DIR/watchdog.pid"
SERVER_PID_FILE="$PID_DIR/server.pid"
RESTART_DELAY="${RESTART_DELAY:-10}"
JAVA_BIN="${JAVA_BIN:-java}"
JAVA_OPTS="${JAVA_OPTS:-}"

mkdir -p "$PID_DIR"

log_watchdog() {
    printf '[%s] %s\n' "$(date '+%Y-%m-%d %H:%M:%S')" "$*" >> "$WATCHDOG_LOG"
}

is_pid_running() {
    local pid="${1:-}"
    [ -n "$pid" ] && kill -0 "$pid" 2>/dev/null
}

read_pid_file() {
    local file="$1"
    [ -f "$file" ] && sed -n '1p' "$file"
}

watchdog_loop() {
    server_pid=""
    trap 'log_watchdog "Nhan lenh dung watchdog"; if is_pid_running "${server_pid:-}"; then kill "$server_pid" 2>/dev/null || true; wait "$server_pid" 2>/dev/null || true; fi; rm -f "$WATCHDOG_PID_FILE" "$SERVER_PID_FILE"; exit 0' INT TERM

    if [ ! -f "$JAR_FILE" ]; then
        log_watchdog "Khong tim thay file jar: $JAR_FILE"
        printf 'Khong tim thay file jar: %s\n' "$JAR_FILE" >&2
        exit 1
    fi

    echo "$$" > "$WATCHDOG_PID_FILE"
    log_watchdog "Bat dau watchdog cho $JAR_FILE"

    while true; do
        log_watchdog "Khoi dong server"
        # shellcheck disable=SC2086
        "$JAVA_BIN" $JAVA_OPTS -jar "$JAR_FILE" >> "$LOG_FILE" 2>&1 &
        server_pid=$!
        echo "$server_pid" > "$SERVER_PID_FILE"

        wait "$server_pid"
        exit_code=$?
        rm -f "$SERVER_PID_FILE"

        log_watchdog "Server da thoat voi ma $exit_code, tu dong chay lai sau ${RESTART_DELAY}s"
        sleep "$RESTART_DELAY"
    done
}

start_server() {
    local watchdog_pid
    watchdog_pid="$(read_pid_file "$WATCHDOG_PID_FILE")"
    if is_pid_running "$watchdog_pid"; then
        printf 'Server watchdog dang chay voi PID %s\n' "$watchdog_pid"
        return 0
    fi

    rm -f "$WATCHDOG_PID_FILE" "$SERVER_PID_FILE"
    nohup bash "$0" foreground >> "$WATCHDOG_LOG" 2>&1 &
    printf 'Da bat watchdog. Xem log: %s va %s\n' "$WATCHDOG_LOG" "$LOG_FILE"
}

stop_server() {
    local server_pid watchdog_pid
    server_pid="$(read_pid_file "$SERVER_PID_FILE")"
    watchdog_pid="$(read_pid_file "$WATCHDOG_PID_FILE")"

    if is_pid_running "$watchdog_pid"; then
        kill "$watchdog_pid" 2>/dev/null || true
    fi

    if is_pid_running "$server_pid"; then
        kill "$server_pid" 2>/dev/null || true
    fi

    rm -f "$WATCHDOG_PID_FILE" "$SERVER_PID_FILE"
    printf 'Da gui lenh dung server/watchdog\n'
}

status_server() {
    local server_pid watchdog_pid
    server_pid="$(read_pid_file "$SERVER_PID_FILE")"
    watchdog_pid="$(read_pid_file "$WATCHDOG_PID_FILE")"

    if is_pid_running "$watchdog_pid"; then
        printf 'Watchdog dang chay: PID %s\n' "$watchdog_pid"
    else
        printf 'Watchdog dang tat\n'
    fi

    if is_pid_running "$server_pid"; then
        printf 'Server dang chay: PID %s\n' "$server_pid"
    else
        printf 'Server dang tat\n'
    fi
}

case "${1:-start}" in
    start)
        start_server
        ;;
    stop)
        stop_server
        ;;
    restart)
        stop_server
        sleep 2
        start_server
        ;;
    status)
        status_server
        ;;
    foreground)
        watchdog_loop
        ;;
    *)
        printf 'Dung lenh: %s {start|stop|restart|status|foreground}\n' "$0" >&2
        exit 2
        ;;
esac
