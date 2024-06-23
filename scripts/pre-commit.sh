#!/usr/bin/env bash
echo "
=======================
|  Running detekt... |
=======================
"

./gradlew --no-daemon --stacktrace -PdisablePreDex detekt

detektStatus=$?

# return 1 exit code if running checks fails
[ $detektStatus -ne 0 ] && exit 1
exit 0