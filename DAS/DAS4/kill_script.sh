#!/bin/bash

# Get all process with measure from user skok
process=$(pgrep -u skok measure)

# Kill all processes
for i in $process
do
kill $i 2>/dev/null
done
