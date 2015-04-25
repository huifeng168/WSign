#Web Sign(WSign)
###Version 1.0 GA

Blog : http://shimingxy.blog.163.com/

Welcome to the home of  the  Web Sign project, more commonly referred to as WSign.  

Web Sign is the Single Sign On applications for  web applications . 

Web Sign Based on <strong>browser cookie </strong> and  <strong>http post</strong>

Web Sign简称为WSign,是一款基于<strong>浏览器Cookie</strong>和<strong>http post</strong>的Web应用单点登录系统.

WSign采用流行的加密和签名的技术保障单点登录的安全性.


- An open and well-documented/开放具有良好文档
- An open-source Java server component/基于JAVA EE开源产品
- A library of clients for Java, .Net others/支持客户端Java, .Net 其他
- License at Apache License, Version 2.0 /开源免费



###Protocol
- Cookie Based Token(wc),Token transmit by browser cookie /基于Cookie认证，通过浏览器Cookie传递Token
- Token  Based Token(wt),Post token to target via http post method/通过HTTP POST的方式提交Token到目标应用

###Token
  1)Original Token 原始格式
<pre><code>[登录用户名]@@[Token过期时间(UTC)]
[login username]@@[Token expire date time(UTC)]

wsignuser@@2015-04-24T16:56:32.402+08:00</code></pre>
	 
  2)Encryption,then to HEX ,algorithm support DES,DESede,Blowfish and AES 加密格式，加密处理完成后，转换为HEX格式，加密方法DES,DESede,Blowfish and AES
<pre><code>6d4a75efc32a791643fc73686f95a6fe2cc0175756bc4fb7978d7f84c2955f5aeecf991722e641e3</code></pre>
  
  3)Base64URL Token 编码格式Base64URL
<pre><code>NmQ0YTc1ZWZjMzJhNzkxNjQzZmM3MzY4NmY5NWE2ZmUyY2MwMTc1NzU2YmM0ZmI3OTc4ZDdmODRjMjk1NWY1YWVlY2Y5OTE3MjJlNjQxZTM</code></pre>
	 
  4)Signature签名格式
	Signature step 3 Token 
	对第3步得到的数据进行签名
<pre><code>f8p75vJ_4KQpi55nygVp0yZ5lSE_5pcdAP6Eq7LhRax0_wbUK3X5EWfysvNb6V4nfLnGmcxE0hgi7miAZ1UOb92wb2CyTCLwSJQ2YLtQaWW49RVr7JUwvkMASLpWR4FPpRh3UmO87fx_KbxfFVzwsvu-Fy0lcIB-aQ5cl-JOgxU</code></pre>
     
Key Format 证书格式：[JSON Web Key (JWK)](https://tools.ietf.org/html/draft-ietf-jose-json-web-key-39 "JSON Web Key (JWK)")   参见源代码 src/keystore.jwks,含公钥和私钥,WebRoot/key/jwk仅含公钥
	 
Signature Algorithms 签名方法: [JSON Web Algorithms (JWA)](https://tools.ietf.org/html/draft-ietf-jose-json-web-algorithms-39 "JSON Web Algorithms (JWA)") 
<pre><code> RS256        | RSASSA-PKCS-v1_5 using SHA-256    | Recommended    |</code></pre>
	           
  5)Transmit Token传递数据
<pre><code>Format [Base64URL Token].[Signature] or [Base64URL Token].
格式：[Base64URL编码].[签名] or [Base64URL编码].
NmQ0YTc1ZWZjMzJhNzkxNjQzZmM3MzY4NmY5NWE2ZmUyY2MwMTc1NzU2YmM0ZmI3OTc4ZDdmODRjMjk1NWY1YWVlY2Y5OTE3MjJlNjQxZTM.f8p75vJ_4KQpi55nygVp0yZ5lSE_5pcdAP6Eq7LhRax0_wbUK3X5EWfysvNb6V4nfLnGmcxE0hgi7miAZ1UOb92wb2CyTCLwSJQ2YLtQaWW49RVr7JUwvkMASLpWR4FPpRh3UmO87fx_KbxfFVzwsvu-Fy0lcIB-aQ5cl-JOgxU</code></pre>
			   
备注：第2步和第4步为备选，建议同时使用
   
MEM:  step 2 and step 4 is option,but Recommended
   
###Token Name[Token名称] 
<pre><code>WSign_Token</code></pre>

###Token Parse[Token解析]
<pre><code>1)Token split 签名分离
    "."before is Token，after is Signature
    "."前半部分为Token数据，后半部分为签名
2)Validate Signature 验证签名
	
3)Decode Base64URL Token Base64URL Token解码
	
4)Decode encrypted Token 解密加密Token数据
	
5)Parse username & authentication time(UTC format) 解析用户名和Token过期时间(UTC)
	
6)Validate authentication time(UTC format) 验证Token认证时间
    if after now,valid，else invalid 
    大于当前时间,正确;小于当前时间,失效</code></pre>
		
备注:如果未签名，省略第2步;若果未加密省略第4步

MEM :if not Signature,skip step 2; if not Encryption skip step 4

	
###SSO Paramenter[认证参数说明]
<pre><code>http://login.connsec.com:8080/wsign/login?target=http://login.connsec.com:8080/wt/index.jsp&wsign=wt
target     单点登录的目标地址
wsign      协议，可选值wc or wt
relaystate 状态值,由应用提供</code></pre>

###WebApps Setting [目标应用配置]

src/wsignapps.json

<pre><code>{
	"WSignApps":
		[
			{
			  "target": "http://login.connsec.com:8080/wc/index.jsp",//目标地址
			  "client_secret": "KOHaEcb8",                           //加密密钥
			  "method": "wc",                                        //协议
			  "expires": "2",                                        //有效期
			  "encrypt": "true",                                     //是否加密
			  "algorithm": "DES",                                    //加密方法
			  "sign": "true"                                         //是否签名
			}
		]
}</code></pre>

###System Setting[系统配置]
1)wsign.properties
<pre><code>config.datasource.driverclass                        //数据库驱动
config.datasource.url                                //数据库访问地址
config.datasource.username                           //数据库用户名
config.datasource.password                           //数据库密码
config.authentication.provider                       //认证提供者
config.query.userinfo.sql                            //用户查询SQL
config.base.domain                                   //应用Cookie域
config.characterencoding.encoding                    //系统编码</code></pre>	

###Internationalization[国际化支持]
src/i18n
<pre><code>MessageBundle.properties
MessageBundle_en.properties
MessageBundle_zh_CN.properties</code></pre>

###License & Copyright

Apache License, Version 2.0 , see LICENSE for details.
