FROM tomcat:alpine

USER root
RUN rm -rf /usr/local/tomcat/webapps/
COPY ./target/tomcat-sample-1.0.0.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]
