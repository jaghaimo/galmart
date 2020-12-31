#!/usr/bin/env bash
set -eu

MOD=$(basename "$(pwd)")
VERSION=$(jq -r .version mod_info.json)
RELEASE=$MOD-$VERSION

# copy over mod to release folder
rm -rf $MOD
mkdir $MOD
cp -R data/ $MOD/
cp mod_info.json ${MOD}.jar ${MOD}.version $MOD/

rm -f $RELEASE.zip
zip -r $RELEASE.zip $MOD
rm -rf $MOD
