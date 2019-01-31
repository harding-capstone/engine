#!/bin/bash
VERSION=$(sentry-cli releases propose-version)

# Create a release
sentry-cli releases new -p engine $VERSION

# Associate commits with the release
sentry-cli releases set-commits --auto $VERSION

