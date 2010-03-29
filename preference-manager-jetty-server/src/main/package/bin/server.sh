#!/bin/sh
#
# Server script.

ARG="$1"
CONFIGDIR=`dirname $0`/../config
CONFIGFILE=$CONFIGDIR/config.properties
LOGDIR=`dirname $0`/../logs
LOGFILE=$LOGDIR/server.log
LIBDIR=`dirname $0`/../lib
CLASSPATH=$LIBDIR/preference-manager-jetty-server.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-deploy.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-jmx.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-security.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-servlets.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-webapp.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-websocket.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jsf-api.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jsf-impl.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jsp-2.1-glassfish.jar
CLASSPATH=$CLASSPATH:$LIBDIR/ant.jar
CLASSPATH=$CLASSPATH:$LIBDIR/ecj.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-client.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-continuation.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-http.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-io.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-server.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-servlet.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-util.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jetty-xml.jar
CLASSPATH=$CLASSPATH:$LIBDIR/jsp-api-2.1-glassfish.jar
CLASSPATH=$CLASSPATH:$LIBDIR/servlet-api.jar
MAINCLASS=org.lazydog.preference.manager.server.EmbeddedServer
SERVER="$JAVA_HOME/bin/java -classpath $CLASSPATH -Dconfig=$CONFIGFILE $MAINCLASS"

if [ ! -d $LOGDIR ] ; then
    mkdir $LOGDIR
fi

case $ARG in

    start)
        nohup $SERVER start > $LOGFILE 2>&1 &
        ;;
    stop)
        $SERVER stop
        ;;
esac

exit 0