#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p bin
javac -encoding UTF-8 -d bin src/sistemaprovas/*.java
java -Dfile.encoding=UTF-8 -cp bin sistemaprovas.Main
