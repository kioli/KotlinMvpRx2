#!/usr/bin/env bash

if [ "$APPCENTER_BRANCH" == "master" ];
then
    # sudo gem install fastlane
    bundle install
    # bundle update fastlane
    echo "Launch tests on branch $APPCENTER_BRANCH"
    cd .. && bundle exec fastlane test
fi