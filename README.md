# Patcher use
## Prerequesites
Acquire `Patcher.class`, and ensure you are in an environment with the `diff` command, and the ability to run shells scripts and Java programs.

## Instructions
1. Locate and cd to the folder parent to the git repositories `FTCVision` and `ftc_app`. If this folder does not exist, clone the two repos.
2. Run the command `diff -qr ftc_app/FtcRobotController FTCVision/ftc-robotcontroller > diff.txt`. Order matters!
3. Run `java Patcher`, and then `ls`. You will see a new file, `out.sh`.
4. Run `chmod +x out.sh` to give yourself permission to run it.
5. Run `./out.sh` and watch as a folder is generated named `patched_ftc-robotcontroller`. This is the resulting patched folder.

## Directory Tree
```
├── Patcher.class
├── diff.txt
├── out.sh (generated)
├── ftc_app
│   ├── doc
│   ├── FtcRobotController
│   └── gradle
└── FTCVision
    ├── ftc-cameratest
    ├── ftc-robotcontroller
    ├── ftc-visionlib
    ├── gradle
    ├── opencv-java
    └── results
```
