@echo off
title 快速配置Forge脚本WC制作

set INFO=0
::开始画面
:start
echo +=====================+
echo   快速配置Forge脚本
echo                WC制作
echo +=====================+
echo 1.配置JAVA环境变量
echo 2.配置Forge开发环境
echo 3.配置Eclipse工程文件
echo 4.快速构建Mod
echo 5.打开Gradlew GUI
echo 6.未知
if %INFO% EQU 0 echo 7.配置时显示更多信息(当前关闭)
if %INFO% EQU 1 echo 7.配置时显示更多信息(当前开启)
echo 8.退出
echo +=====================+

::选择选项
:select
set select=
set /p select=请选择: 
if "%select%"=="1" goto java
if "%select%"=="2" goto forge
if "%select%"=="3" goto eclipse
if "%select%"=="4" goto build
if "%select%"=="5" goto gui
if "%select%"=="6" goto git
if "%select%"=="7" goto switch
if "%select%"=="8" goto end
goto select

::自动配置JAVA环境变量
:java
:: 在C:\Program Files\Java中搜索Jdk
set JDK=C:\Program Files\Java\
echo +=====================+
echo 执行 1.配置JAVA环境变量
echo +=====================+
echo 正在检查是否存在变量
echo +=====================+
::检查是否存在JAVA环境变量
if defined JAVA_HOME (
  echo 检查到JAVA环境变量
  echo 配置已取消
  echo +=====================+
  pause
  goto start
)
:: 如果存在JDK目录则搜索javac.exe
if exist %JDK% (
  echo 已找到JDK目录...
  echo +=====================+
  pushd %JDK%
  for /r %%i in (*.exe) do (
    if /i %%~nxi equ javac.exe (
      set JAVACPATH=%%i
      set HASJDK=1
    )
  )
  popd
)
:: 如果没找到JDK目录
if %HASJDK% NEQ 1 (
  echo 未找到Jdk目录,可能是未安装或没有安装到默认目录
  echo 手动配置吧QAQ
  echo +=====================+
  pause
  goto start
)
::配置环境变量
set JDKPATH=%JAVACPATH:~0,-14%
echo JDK目录:%JDKPATH%
echo +=====================+
setx JAVA_HOME "%JDKPATH%" /M
setx CLASSPATH ".;%%Java_Home%%\lib\tools.jar;%%Java_Home%%\lib\dt.jar;%%Java_Home%%\jre\lib\rt.jar" /M
set VARPATH=%Path%;%%Java_Home%%\bin;%%Java_Home%%\jre\bin
setx Path "%VARPATH%" /M
::配置完成,测试
echo +=====================+
echo JDK环境变量设置成功,测试一下吧~
echo +=====================+
call java -version
echo +=====================+
pause
goto start

:forge
echo +=====================+
echo 执行 2.配置Forge开发环境
echo +=====================+
if %INFO% EQU 0 call ./gradlew.bat setupDecompWorkspace
if %INFO% EQU 1 call ./gradlew.bat setupDecompWorkspace --info
echo +=====================+
echo 配置Forge开发环境完毕
echo +=====================+
pause
goto start

:eclipse
echo +=====================+
echo 执行 3.配置Eclipse工程文件
echo +=====================+
if %INFO% EQU 0 call ./gradlew.bat eclipse
if %INFO% EQU 1 call ./gradlew.bat eclipse --info
echo +=====================+
echo 配置Eclipse工程文件完毕
echo +=====================+
pause
goto start

:build
echo +=====================+
echo.执行 4.快速构建Mod
echo +=====================+
if %INFO% EQU 0 call ./gradlew.bat build
if %INFO% EQU 1 call ./gradlew.bat build --info
echo +=====================+
echo 构建Mod完毕
echo +=====================+
pause
goto start

:gui
echo +=====================+
echo.执行 5.打开Gradlew GUI
echo +=====================+
call gradlew --gui
echo +=====================+
echo Gradlew GUI结束
echo +=====================+
pause
goto start

:git
echo +=====================+
echo.执行 6.未知
echo +=====================+
echo 尚未完成,敬请期待
echo +=====================+
echo 未知结束
echo +=====================+
pause
goto start

:switch
if %INFO% EQU 0 (
  set INFO=1
  echo +=====================+
  echo 执行 7.切换信息显示
  echo +=====================+
  echo 已切换为开启更多信息
  echo +=====================+
  pause
  goto start
)
if %INFO% EQU 1 (
  set INFO=0
  echo +=====================+
  echo 执行 7.切换信息显示
  echo +=====================+
  echo 已切换为关闭更多信息
  echo +=====================+
  pause
  goto start
)

:end