#filename: expression.py
#coding=utf-8

import zipfile
import shutil
import os
import sys

APP_NAME = "JoyApp"
SRC_FILE = APP_NAME + "-qyer-release.apk"
ORIGIN_SRC_FILE = "../build/outputs/apk/" + SRC_FILE

# 空文件，便于把此文件写入apk包中作为channel文件
src_empty_file = 'channel/czt.txt'
# 创建一个空文件（不存在则创建）
f = open(src_empty_file, 'w')
f.close()

# 创建渠道包的根目录，如果存在则删除后重建
output_dir = 'outputs_apk/'
if os.path.exists(output_dir):
    shutil.rmtree(output_dir)
os.mkdir(output_dir)

if not os.path.exists(SRC_FILE) or not os.path.isfile(SRC_FILE):
	if os.path.exists(ORIGIN_SRC_FILE) and os.path.isfile(ORIGIN_SRC_FILE):
		shutil.copyfile(ORIGIN_SRC_FILE, SRC_FILE)
	else:
		print "找不到源文件：" + ORIGIN_SRC_FILE
		sys.exit(1)

# 获取渠道列表
channel_file = 'channel/channel.txt'
f = open(channel_file)
lines = f.readlines()
f.close()

# 输出渠道数
line_count = 0
for line in lines:
    line_count += 1
print "channel size: " + str(line_count)

# 遍历渠道号并创建对应渠道号的apk文件
for line in lines:
    # 获取当前渠道号，因为从渠道文件中获得带有\n，所以strip一下
    target_channel = line.strip()
    # 拼接对应渠道号的apk
    target_apk = output_dir + APP_NAME + "-" + target_channel + "-release.apk"
    # 拷贝建立新apk
    shutil.copy(SRC_FILE, target_apk)
    # zip获取新建立的apk文件
    zipped = zipfile.ZipFile(target_apk, 'a', zipfile.ZIP_DEFLATED)
    # 初始化渠道信息
    empty_channel_file = "META-INF/cztchannel_{channel}".format(channel = target_channel)
    # 写入渠道信息
    zipped.write(src_empty_file, empty_channel_file)
    print "build successful: " + APP_NAME + "/python/" + target_apk
    # 关闭zip流
    zipped.close()
print "work done."
print "you can run (\"sh install.sh\") to install " + SRC_FILE
