#!/bin/bash

mvn package javadoc:jar source:jar gpg:sign deploy
