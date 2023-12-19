#快速编译打包 apk 脚本 (MacOS 系统环境快速脚本)
#MacOS 系统上面使用此脚本时，注意先给脚本添加权限
# 1.打开终端
# 2.进入到脚本根目录
# 3.执行：chmod +x build.command    为脚本添加可执行权限

echo "---package_begin----"
sleep 1

#执行打包命令前，需要先定位到项目根目录(根据自己的项目目录去修改)
cd /Users/zhj3746/Documents/JenkinsSpace/MyDemo/

#  --stacktrace --info 这是为了输出详细日志使用的，可以省略
#./gradlew build --stacktrace

#执行打包命令
gradle assembleRelease --stacktrace --info

echo "---package success---"

#桌面右上角弹出通知
#notify-send build.sh "package down!"