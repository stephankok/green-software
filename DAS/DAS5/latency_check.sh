#!/bin/bash

measure_script="/home/skok/PDU/measure_continues.sh"
kill_measurement="/home/skok/PDU/kill_script.sh"

echo "T1 (ms), T2 (ms), T1+T2 (ms), start2 (MM-SS-NNNNNNNNN), end2 (MM-SS-NNNNNNNNN)"
for runcount in `seq 1000`
do
# make sure no measurement is running
ssh skok@fs0.das4.cs.vu.nl $kill_measurement

# Start fake measurement
ssh skok@fs0.das4.cs.vu.nl $measure_script -o "TESTFILE" -p "3" &

# Calculate time, since we we put measurescript in bg we need seperate connenction
start=$(ssh skok@fs0.das4.cs.vu.nl date +%M%S%N)
end=$(date +%M%S%N)

# Here the program will be run

start2=$(date +%M%S%N)
end2=$(ssh skok@fs0.das4.cs.vu.nl "$kill_measurement; date +%M%S%N")

# Log result
echo "$(echo "($end - $start) / 1000000.0" | bc), $(echo "($end2 - $start2) / 1000000.0" | bc), $(echo "($end - $start + $end2 - $start2) / 1000000.0" | bc)", $start2, $end2
done