FROM gradle:5.2.1-jdk8-alpine

USER root

RUN apk add postgresql curl

RUN curl -s -S -f -o "/usr/local/share/ca-certificates/NMRootCA2.pem" "https://nexus.nmlv.nml.com/repository/nmlv-artifacts/NMRootCA2.pem" \
    && update-ca-certificates \
    && keytool -list -alias nmrootca2 -storepass 'changeit' -keystore "${JAVA_HOME}/jre/lib/security/cacerts"
RUN curl -s -S -f -o "/usr/local/share/ca-certificates/NMTestRootCA2.pem" "https://nexus.nmlv.nml.com/repository/nmlv-artifacts/NMTestRootCA2.pem" \
    && update-ca-certificates \
    && keytool -list -alias nmtestrootca2 -storepass 'changeit' -keystore "${JAVA_HOME}/jre/lib/security/cacerts"

USER gradle
