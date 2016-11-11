#!/usr/bin/env bash

DIRECTORY="Front-End"

if [ -d "$DIRECTORY" ]; then
    cd ${DIRECTORY}
    git pull
else
    git clone https://github.com/RDB-Academy/Interface-Module-SQL.git ${DIRECTORY}
fi

npm install
bower install
npm build
ember build
cd ..
mv ${DIRECTORY}/dist public/dist
