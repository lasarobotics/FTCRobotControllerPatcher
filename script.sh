#!/bin/bash

#first parameter - FTCVision folder elsewhere on disk or folder in this dir

git reset --hard
git clean -dxf -e script.sh *
javac Patcher.java
git clone --depth 1 https://github.com/ftctechnh/ftc_app.git
git clone --depth 1 --branch dev https://github.com/lasarobotics/FTCVision
diff -qr ftc_app/FtcRobotController FTCVision/ftc-robotcontroller > diff.txt
java Patcher
chmod +x out.sh
bash out.sh

#change ftc-vision to your folder ($1)

echo $1
rm -rf $1/ftc-robotcontroller
cp -r patched_ftc-robotcontroller/ $1/
mv $1/patched_ftc-robotcontroller/ $1/ftc-robotcontroller
echo "All done!"
