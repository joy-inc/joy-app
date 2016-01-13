#filename: expression.py
#coding=utf-8

import zipfile
import shutil
import os
import sys

import ConfigParser
#生成config对象
conf = ConfigParser.ConfigParser()
#用config对象读取配置文件
conf.read("../../gradle.properties")
#以列表形式返回所有的section
sections = conf.sections()
#得到指定section的所有option
options = conf.options(sections[0])
#得到指定section的所有键值对
#kvs = conf.items(sections[0])
#print 'keyValues:', kvs
#指定section，option读取值
appName = conf.get(sections[0], options[0])
versionCode = conf.getint(sections[0], options[1])
versionName = conf.get(sections[0], options[2])
print options[0], appName
print options[1], versionCode
print options[2], versionName
channels = conf.options(sections[1])
channelNames = conf.get(sections[1], channels[0])
channelNames = channelNames.replace(',','\n').split()
print 'channel_names:', channelNames
print "channel_size: " + str(len(channelNames))

SRC_FILE = appName + "-release.apk"
ORIGIN_SRC_FILE = "../build/outputs/apk/" + SRC_FILE

# 空文件，便于把此文件写入apk包中作为channel文件
emptyFile = 'czt.txt'
# 以写入的方式打开文件（不存在则创建）
f = open(emptyFile, 'w')
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

# 遍历渠道号并创建对应渠道号的apk文件
for name in channelNames:
    # 拼接对应渠道号的apk
    target_apk = output_dir + appName + "-" + name + "-release" + versionName + ".apk"
    # 拷贝建立新apk
    shutil.copy(SRC_FILE, target_apk)
    # zip获取新建立的apk文件
    zipped = zipfile.ZipFile(target_apk, 'a', zipfile.ZIP_DEFLATED)
    # 初始化渠道信息
    empty_channel_file = "META-INF/cztchannel_{channel}".format(channel = name)
    # 写入渠道信息
    zipped.write(emptyFile, empty_channel_file)
    print "build successful: " + appName + "/python/" + target_apk
    # 关闭zip流
    zipped.close()
print "work done."