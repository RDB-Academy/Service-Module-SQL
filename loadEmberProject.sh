#!/usr/bin/env bash

DIRECTORY="Front-End"

if [ -d "$DIRECTORY" ]; then
    cd ${DIRECTORY}
    git pull
else
    git clone -b develop https://github.com/RDB-Academy/Interface-Module-SQL.git ${DIRECTORY}
    cd ${DIRECTORY}
fi

npm install
bower install
npm build
ember build --environment=production
cd ..
rm -rf public/dist
cp -r ${DIRECTORY}/dist public/dist
