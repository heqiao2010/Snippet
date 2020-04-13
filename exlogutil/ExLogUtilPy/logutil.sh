#!/bin/sh
#查看日志的脚本 heqiao 16-11-09
pod_num=4 #pod个数

#检查参数
if [ $# != 2 ]; then
        echo "此脚本用于查看日志:" 
        echo "使用方法: $0 【服务名】 【pod序号列表】" 
        echo "例如: $0 data 1,2" 
        exit 1;
fi

#分割$2
OLD_IFS="$IFS"
IFS=","
arr=($2)
IFS="$OLD_IFS"
for s in ${arr[@]}
do
        if [ $s -gt $pod_num ]; then
                echo "当前Pod数：$pod_num";
                exit 1;
        elif [ $s -lt "1" ]; then
                echo "pod序号必须大于0";
                exit 1;
        else
                pod_name=`kubectl get pod | grep auth-"$1" | cut -d " " -f 1 | sed -n "$s"p`
                echo -e "\n"$pod_name
                kubectl logs $pod_name
        fi
done
