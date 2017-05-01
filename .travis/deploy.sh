#!/bin/bash
cd `dirname $0`/..

if [ -z "$SONATYPE_USERNAME" ]
then
    echo "error: please set SONATYPE_USERNAME and SONATYPE_PASSWORD environment variable"
    exit 1
fi

if [ -z "$SONATYPE_PASSWORD" ]
then
    echo "error: please set SONATYPE_PASSWORD environment variable"
    exit 1
fi

openssl aes-256-cbc -K $encrypted_a65b4a5a53ac_key -iv $encrypted_a65b4a5a53ac_iv -in .travis/codesigning.asc.enc -out .travis/codesigning.asc -d
gpg --fast-import .travis/codesigning.asc

if [ ! -z "$TRAVIS_TAG" ]
then
    echo "on a tag -> set pom.xml <version> to $TRAVIS_TAG"

    mvn --settings .travis/settings.xml org.codehaus.mojo:versions-maven-plugin:2.1:set -DnewVersion=$TRAVIS_TAG 1>/dev/null 2>/dev/null
else
    echo "not on a tag -> keep snapshot version in pom.xml"
fi

mvn deploy --settings .travis/settings.xml -P sign,build-extras -DskipTests=true -B -U