# dockerfile to build image for JBoss EAP 7.1

# start from daggerok/jboss-eap-7.1
FROM daggerok/jboss-eap-7.1

# file author / maintainer
MAINTAINER "Deepak Pachauri" "deepak.pachauri@gmail.com"

# set environment
ENV JBOSS_HOME /home/jboss/jboss-eap-7.1

# create JBoss console user
RUN $JBOSS_HOME/bin/add-user.sh admin password --silent

# configure JBoss
RUN echo "JAVA_OPTS=\"\$JAVA_OPTS -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0\"" >> $JBOSS_HOME/bin/standalone.conf

# set permission folder 
RUN chown -R jboss:jboss /home/jboss

# JBoss ports
EXPOSE 8080 9990 9999

# start JBoss
ENTRYPOINT $JBOSS_HOME/bin/standalone.sh -c standalone-full-ha.xml

# deploy app
#ADD myapp.war "$JBOSS_HOME/standalone/deployments/"

USER jboss
CMD  /bin/bash