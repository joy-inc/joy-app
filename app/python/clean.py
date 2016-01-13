#filename: expression.py
#coding=utf-8

import shutil
import os
import ConfigParser
#生成config对象
conf = ConfigParser.ConfigParser()
#用config对象读取配置文件
conf.read("../../gradle.properties")
#以列表形式返回所有的section
sections = conf.sections()
#得到指定section的所有option
options = conf.options(sections[0])
#指定section，option读取值
appName = conf.get(sections[0], options[0])

SRC_FILE = appName + "-release.apk"

output_dir = 'outputs_apk/'
if os.path.exists(output_dir):
    shutil.rmtree(output_dir)

if os.path.exists(SRC_FILE):
    os.remove(SRC_FILE)

print "work done."