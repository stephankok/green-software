#!/bin/bash

# Racktivity PDU "rpdu04" with several DAS-5 nodes is at 10.141.0.195

# This is the last known port to node mapping:
# port 0: unused
# port 1: unused
# port 2: DAS-5/node029
# port 3: DAS-5/node028
# port 4: DAS-5/node027
# port 5: DAS-5/node026
# port 6: DAS-5/node025
# port 7: DAS-5/node024

ip="10.141.0.195"
mib="/var/scratch/versto/Racktivity/ES-RACKTIVITY-MIB.txt"

snmpwalk -v 1 -Cc -c public -t 9 -M .:/usr/share/snmp/mibs -m $mib $ip EPowerEntry