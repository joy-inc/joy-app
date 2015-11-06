#!/bin/bash
#filename: expression.sh
#coding=utf-8

APP_NAME="JoyApp"
PYTHON_DIR="$APP_NAME/python"
FILE="$PYTHON_DIR/$APP_NAME-qyer-release.apk"

if [ -e "$FILE" ]; then
	echo "目标文件存在，是否使用此文件编译渠道包？"
	read -p "please input (y/n) :" a
	if [ "$a" == "y" ]; then
		echo "开始编译渠道包..."
		cd $PYTHON_DIR && python build.py && open outputs_apk && cd ..
	elif [ "$a" == "n" ]; then
		echo "是否重新编译目标文件？"
		read -p "please input (y/n) :" b
		if [ "$b" == "y" ]; then
			rm $FILE
			echo "开始重新编译目标文件..."
			./gradlew clean assembleQyerRelease
			echo "开始编译渠道包..."
			cd $PYTHON_DIR && python build.py && open outputs_apk && cd ..
		else
			echo "任务结束！"
			exit 0
		fi
	else
		echo "无效输入，任务结束！"
		exit 0
	fi
else
	echo "目标文件不存在，开始编译目标文件..."
	./gradlew clean assembleQyerRelease
	echo "开始编译渠道包..."
	cd $PYTHON_DIR && python build.py && open outputs_apk && cd ..
fi