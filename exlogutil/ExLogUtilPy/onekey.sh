#!/bin/bash
# 一键认证测试脚本  by heqiao

#固定账号认证测试数据，注意等号两边不要有空格 
#portal服务器认证地址
portal_host=oasisrdauth.h3c.com
#portal服务器端口
portal_port=80
#重定向URI
redirect_uri=http://www.baidu.com
#MAC
usermac=AA-BB-CC-DD-FF-A8
#IP
userip=1.1.1.5
#userurl
userurl=http://www.sina.com
#nasid
nas_id=cm-0-352331-219801A0WFH133000005
#ssid
ssid=rd_test_heqiao
#template_id
template_id=1528

#URLencode方法
url_encode(){
        echo "$1" | tr -d '\n' | xxd -plain | sed 's/\(..\)/%\1/g' | tr -d '\n'
        return 0
}
#URLdecode方法
url_decode(){
        printf $(echo -n $t | sed 's/\\/\\\\/g;s/\(%\)\([0-9a-fA-F][0-9a-fA-F]\)/\\x\2/g')
        return 0
}

#parse json data
parse_json(){
	json=`echo $1 | sed 's/\"//g'`; #remove quotation mark
	echo $json | sed 's/.*'$2':\([^,}]*\).*/\1/'
	return 0
}

#parse uri parameter
parse_uri_paras(){
	echo $1 | sed 's/.*'$2'=\([[:alnum:]]*\).*/\1/'
	return 0
}

# get the redirect address
request_redirect_url(){
	echo `curl --connect-timeout 10 -m 20 -i "$1" 2>/dev/null  | sed -n 's/^Location://p'`
	return 0
}

# sent http get 
http_get(){
	get_data=`curl --connect-timeout 10 -m 20 -X GET "$1" 2>/dev/null`
	if [ "$get_data" =  "" ]; then #出错了
        	echo "出错了，试试：curl --connect-timeout 10 -m 20 -X GET \"$1\""
        	exit 1
	else 
		echo $get_data
		return 0
	fi
}


# send http post
http_post(){
	post_data=`curl --connect-timeout 10 -m 20 -X POST -d "$1" $2 2>/dev/null`
	if [ "$post_data" =  "" ]; then #出错了
                echo "出错了，试试：curl --connect-timeout 10 -m 20 -X POST \"$1\" \"$2\""
                exit 1
        else
                echo $post_data
                return 0
        fi
}

# wether has barackets
has_brackets(){
        if [ `echo "$1" | grep { | wc -L` -eq "0" ] && [ `echo "$1" | grep } | wc -L` -eq "0" ] ; then
                echo "0"
        else
                echo "1"
        fi
        return 0
}

# let's begin...
url="http://$portal_host:$portal_port/portal/protocol?response_type=code\
&redirect_uri=$(url_encode $redirect_uri)\
&usermac=$(url_encode $usermac)\
&userip=$userip\
&userurl=$(url_encode $userurl)\
&nas_id=$nas_id\
&ssid=$(url_encode $ssid)\
&template_id=$template_id"

echo "1.============== 重定向到登录页面 =============="
echo -e "从AC重定向的URL地址为: "$url
redirect_url=$(request_redirect_url "$url")
echo -e '~ portal服务器重定向到登录页面:'$redirect_url
template_id=$(parse_uri_paras $redirect_url "templateId")
echo -e '~ 获取templateId:'$template_id'\n\n'

loginurl="http://$portal_host:$portal_port/portal/login?operateType=1\
&templateId=$template_id\
&location=null\
&redirect_uri=$(url_encode $redirect_uri)\
&nas_id=$nas_id\
&ssid=$(url_encode $ssid)\
&usermac=$(url_encode $usermac)\
&userip=$userip\
&userurl=$(url_encode $userurl)\
&_ts=`date +%s`000"

echo "2.============== 从portal服务器获取Code =============="
echo "一键认证登录URL地址: $loginurl" 
return_data=$(request_redirect_url "$loginurl")
redirect_uri=$(parse_json $return_data "redirect_uri")
echo "portal服务器返回的json数据: $return_data"
echo "~ 从返回数据中获取到重定向URI: $redirect_uri";
code=$(parse_uri_paras $redirect_uri "code")
if [ "$(has_brackets $code)" -eq "0" ]; then
        echo -e "从重定向的URI中获取到Code: $code\n\n"
else
        echo -e "获取code失败!\n\n"
        exit 1
fi

echo "3.============== 从portal服务器获取AccessToken =============="
accesstoken_url="http://$portal_host:$portal_port/portal/protocol?response_type=access_token&usermac=$usermac&userip=$userip&code=$code"
echo "请求URL地址为: $accesstoken_url"
return_data=$(http_get $accesstoken_url)
echo "portal服务器返回的json数据: $return_data"
accesstoken=$(parse_json $return_data "access_token")
expired_time=$(parse_json $return_data "expire_in")
if [ $(has_brackets $accesstoken) -eq "0" ]; then
        echo -e "~ 从返回数据中获取到AccessToken: $accesstoken 以及过期时间: "$expired_time"\n\n"
else
        echo -e "获取accessToken失败\n\n"
        exit 1
fi

echo "4.============== 从portal服务器获取用户信息 =============="
userinfo_url="http://$portal_host:$portal_port/portal/protocol?response_type=userinfo&access_token=$accesstoken"
echo "请求用户信息URL地址: $userinfo_url"
return_data=$(http_get $userinfo_url)
echo -e "~ portal服务器返回用户信息: "$return_data
online_user_name=$(parse_json $return_data "username")
if [ $(has_brackets $online_user_name) -eq "0" ]; then
        echo -e "用户"$online_user_name"上线成功!\n\n"
else
        echo -e "用户上线失败!\n\n"
fi
