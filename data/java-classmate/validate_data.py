import os

path = os.getcwd() + "/validation/"
files = os.listdir(path)
files.sort()

succes = True
for name in files:
    if "batchJob" not in name:
        continue
    
    with open(path + name, 'r') as f:
        lines = f.read().splitlines()
        checkline = lines[-1]
        if (checkline != "Exiting successful"):
            succes = False
            print("name:", name, "\nmessage:", checkline)

if succes:
    print("No error found!")
else:
    print("Errors listed above")