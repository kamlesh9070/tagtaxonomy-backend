@echo off
REM Download Gradle wrapper
powershell -Command "& {(New-Object Net.WebClient).DownloadFile('https://services.gradle.org/distributions/gradle-8.10-bin.zip', 'gradle-setup.zip')}"

REM Extract
powershell -Command "& {Add-Type -AssemblyName 'System.IO.Compression.FileSystem'; [System.IO.Compression.ZipFile]::ExtractToDirectory('gradle-setup.zip', '.')}"

REM Copy wrapper jar
if not exist gradle\wrapper mkdir gradle\wrapper
copy gradle-8.10\lib\gradle-wrapper.jar gradle\wrapper\

REM Cleanup
rmdir /s /q gradle-8.10
del gradle-setup.zip

echo Gradle wrapper setup complete
