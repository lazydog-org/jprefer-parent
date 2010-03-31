
set ARG=%1
set DIRNAME=%~dp0
set CONFIGDIR=%DIRNAME%..\config
set CONFIGFILE=%CONFIGDIR%\config.properties
set LOGDIR=%DIRNAME%..\logs
set LOGFILE=%LOGDIR%\server.log
set LIBDIR=%DIRNAME%..\lib
set CLASSPATH=%LIBDIR%\preference-manager-jetty-server.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-deploy.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-jmx.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-security.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-servlets.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-webapp.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-websocket.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jsf-api.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jsf-impl.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jsp-2.1-glassfish.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\ant.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\ecj.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-client.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-continuation.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-http.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-io.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-server.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-servlet.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-util.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jetty-xml.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\jsp-api-2.1-glassfish.jar
set CLASSPATH=%CLASSPATH%;%LIBDIR%\servlet-api.jar
set MAINCLASS=org.lazydog.jprefer.server.EmbeddedServer
set SERVER="%JAVA_HOME%\bin\java" -cp %CLASSPATH% -Dconfig=%CONFIGFILE% %MAINCLASS%

if not exist "%LOGDIR%" mkdir "%LOGDIR%"

goto %ARG%
:return

exit /B 0

:start
%SERVER% start > %LOGFILE% 2>&1
goto return

:stop
%SERVER% stop
goto return
