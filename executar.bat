@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist bin mkdir bin
javac -encoding UTF-8 -d bin src\sistemaprovas\*.java
if errorlevel 1 (
  echo Falha ao compilar. Verifique se o JDK esta instalado e configurado no PATH.
  pause
  exit /b 1
)
java -Dfile.encoding=UTF-8 -cp bin sistemaprovas.Main
pause
