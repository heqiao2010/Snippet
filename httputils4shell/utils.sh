#!/bin/sh
#author joel

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
	echo `curl -i "$1" 2>/dev/null  | sed -n 's/^Location://p'`
	return 0
}

# sent http get 
http_get(){
	get_data=`curl -X GET "$1" 2>/dev/null`
	if [ "$get_data" =  "" ]; then #出错了
        	echo "出错了，试试：curl -X GET \"$1\""
        	exit 1
	else 
		echo $get_data
		return 0
	fi
}

# send http post
http_post(){
	post_data=`curl -X POST -d "$1" $2 2>/dev/null`
	if [ "$post_data" =  "" ]; then #出错了
                echo "出错了，试试：curl -X POST \"$1\" \"$2\""
                exit 1
        else
                echo $post_data
                return 0
        fi
}