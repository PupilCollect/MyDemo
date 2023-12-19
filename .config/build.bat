#快速编译打包 apk 脚本 (Window 系统环境快速脚本)

echo "$$package_begin$$"
sleep 1

#执行打包命令前，需要先定位到项目根目录(根据自己的项目目录去修改)
cd /Users/zhj3746/Documents/JenkinsSpace/MyDemo/

#执行打包命令
gradle assembleRelease

echo -e "$$package success$$"

#桌面右上角弹出通知
notify-send build.sh "package down!"