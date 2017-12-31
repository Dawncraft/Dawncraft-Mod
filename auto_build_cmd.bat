@echo off
title ��������Forge�ű�WC����

set INFO=0
::��ʼ����
:start
echo +=====================+
echo   ��������Forge�ű�
echo                WC����
echo +=====================+
echo 1.����JAVA��������
echo 2.����Forge��������
echo 3.����Eclipse�����ļ�
echo 4.���ٹ���Mod
echo 5.��Gradlew GUI
echo 6.δ֪
if %INFO% EQU 0 echo 7.����ʱ��ʾ������Ϣ(��ǰ�ر�)
if %INFO% EQU 1 echo 7.����ʱ��ʾ������Ϣ(��ǰ����)
echo 8.�˳�
echo +=====================+

::ѡ��ѡ��
:select
set select=
set /p select=��ѡ��: 
if "%select%"=="1" goto java
if "%select%"=="2" goto forge
if "%select%"=="3" goto eclipse
if "%select%"=="4" goto build
if "%select%"=="5" goto gui
if "%select%"=="6" goto git
if "%select%"=="7" goto switch
if "%select%"=="8" goto end
goto select

::�Զ�����JAVA��������
:java
:: ��C:\Program Files\Java������Jdk
set JDK=C:\Program Files\Java\
echo +=====================+
echo ִ�� 1.����JAVA��������
echo +=====================+
echo ���ڼ���Ƿ���ڱ���
echo +=====================+
::����Ƿ����JAVA��������
if defined JAVA_HOME (
  echo ��鵽JAVA��������
  echo ������ȡ��
  echo +=====================+
  pause
  goto start
)
:: �������JDKĿ¼������javac.exe
if exist %JDK% (
  echo ���ҵ�JDKĿ¼...
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
:: ���û�ҵ�JDKĿ¼
if %HASJDK% NEQ 1 (
  echo δ�ҵ�JdkĿ¼,������δ��װ��û�а�װ��Ĭ��Ŀ¼
  echo �ֶ����ð�QAQ
  echo +=====================+
  pause
  goto start
)
::���û�������
set JDKPATH=%JAVACPATH:~0,-14%
echo JDKĿ¼:%JDKPATH%
echo +=====================+
setx JAVA_HOME "%JDKPATH%" /M
setx CLASSPATH ".;%%Java_Home%%\lib\tools.jar;%%Java_Home%%\lib\dt.jar;%%Java_Home%%\jre\lib\rt.jar" /M
set VARPATH=%Path%;%%Java_Home%%\bin;%%Java_Home%%\jre\bin
setx Path "%VARPATH%" /M
::�������,����
echo +=====================+
echo JDK�����������óɹ�,����һ�°�~
echo +=====================+
call java -version
echo +=====================+
pause
goto start

:forge
echo +=====================+
echo ִ�� 2.����Forge��������
echo +=====================+
if %INFO% EQU 0 call ./gradlew.bat setupDecompWorkspace
if %INFO% EQU 1 call ./gradlew.bat setupDecompWorkspace --info
echo +=====================+
echo ����Forge�����������
echo +=====================+
pause
goto start

:eclipse
echo +=====================+
echo ִ�� 3.����Eclipse�����ļ�
echo +=====================+
if %INFO% EQU 0 call ./gradlew.bat eclipse
if %INFO% EQU 1 call ./gradlew.bat eclipse --info
echo +=====================+
echo ����Eclipse�����ļ����
echo +=====================+
pause
goto start

:build
echo +=====================+
echo.ִ�� 4.���ٹ���Mod
echo +=====================+
if %INFO% EQU 0 call ./gradlew.bat build
if %INFO% EQU 1 call ./gradlew.bat build --info
echo +=====================+
echo ����Mod���
echo +=====================+
pause
goto start

:gui
echo +=====================+
echo.ִ�� 5.��Gradlew GUI
echo +=====================+
call gradlew --gui
echo +=====================+
echo Gradlew GUI����
echo +=====================+
pause
goto start

:git
echo +=====================+
echo.ִ�� 6.δ֪
echo +=====================+
echo ��δ���,�����ڴ�
echo +=====================+
echo δ֪����
echo +=====================+
pause
goto start

:switch
if %INFO% EQU 0 (
  set INFO=1
  echo +=====================+
  echo ִ�� 7.�л���Ϣ��ʾ
  echo +=====================+
  echo ���л�Ϊ����������Ϣ
  echo +=====================+
  pause
  goto start
)
if %INFO% EQU 1 (
  set INFO=0
  echo +=====================+
  echo ִ�� 7.�л���Ϣ��ʾ
  echo +=====================+
  echo ���л�Ϊ�رո�����Ϣ
  echo +=====================+
  pause
  goto start
)

:end