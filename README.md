## 这个项目主要使用技术

- Spring MVC

- Spring Security

- Spring Stomp

- Spring webflux

- H2 database

- Spring Security OAuth2 Client

- Jetty embedding 


  

  ## 主要功能

- 用户账号注册，账号密码登陆认证

- 单用户登陆控制

- github，google  授权登陆

- 实时接收，发送信息，用户实时在线列表

- API 接口访问权限控制

- 订阅频道信息权限控制

  如果想在线查看项目效果，可以在浏览器直接访问  [http://shenyifeng.tk/static/html/jetty-chat.html](http://shenyifeng.tk/static/html/jetty-chat.html)

  

  ## 怎么在本地运行项目

1. 克隆仓库

   `git clone https://github.com/xiaowu6666/jetty-embed-spring-web.git`

2. 进入项目

   `cd jetty-embed-spring-web`

###### 选择打包方式

[maven-jar-plugin](https://maven.apache.org/plugins/maven-jar-plugin/) 

将所有的依赖jar 分离出来放到lib中，所有的代码逻辑放到一个jar ，运行时依赖和配置文件都要放到同等级目录下

[maven-assembly-plugin](http://maven.apache.org/plugins/maven-assembly-plugin/)

这个将所有依赖jar，配置文件放放入同一个jar中运行，比较方便，但是体积有点大

两种配置我都写在`pom.xml`中,看个人喜欢，我个人更喜欢第一种

3. 编译项目

`mvn package`

4. 运行项目

`java -jar jetty-embed-spring-web.jar`

###### 访问项目

`http://localhost:9999/static/html/jetty-chat.html` 

初始化密码： `123456`


  ### Nginx 部署静态文件反向代理websocket
  
````
 map $http_upgrade $connection_upgrade {
    default upgrade;
    ''  close;
    }

    server {
        listen       80;
        server_name  localhost;

        location / {
            root   html;
            index  index.html index.htm;
            proxy_pass http://localhost:9999;
            #websocket
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

            proxy_http_version 1.1;
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header Connection $connection_upgrade;
        }
        
````