@echo off
cd /d "%LOCALAPPDATA%\Android\Sdk\platform-tools"
set /p PUERTO="Puerto: "
adb connect 192.168.1.18:%PUERTO%
adb devices
pause   