#!/bin/bash

# Download zips.json
curl -O http://media.mongodb.org/zips.json

# Import into MongoDB
mongoimport --db zipcodes --collection zips --file zips.json --drop
