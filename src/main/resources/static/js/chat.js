    //进来直接连接stomp
    var stompClient = null;
    var me = {};
    var chatFlag = false;
    var sendUser = {};
    var unread = [];

    function selectUser(object) {
        $('.btn-success').removeAttr('disabled');
        sendUser.id = $(object).data('id');
        var email = $(object).data('email');
        sendUser.email = email;
        sendUser.username = $(object).data('username');
        sendUser.avatar = $(object).data('avatar');
        $(object).addClass('active');
        $('p.h5').text($(object).data('username'));
        chatFlag = true;
        $('#chat-content').html(" <p class=\"h5 text-center\">"+sendUser.username+"</p><hr>");
        $('textarea.form-control').removeAttr('disabled');
        if (unread.hasOwnProperty(email)) {
            var array = unread[email];
            array.forEach(function(val){
                showReceiveMsg(val.target.avatar,val.message);
            });
            $('a[data-email="'+email+'"] > span').html('');
        }
    }

    function sendMsg() {
        if (!this.chatFlag)
            return;
        var text = $('textarea.form-control').val();
        if (text === null || text == '')
            return;
        var data = {};
        data["user"] = me;
        data["target"] = sendUser;
        data.type = "user";
        data.message = text;
        var formData = JSON.stringify(data);
        console.log("data",formData);
        showSendTxt(me.avatar,text);
        stompClient.send('/app/receive/messgae',{},formData);
        $('textarea.form-control').val("");
    }

    function showSendTxt(avatar,content){
        var text = "<div class='content right-content'>"+
        "<img class='right-avatar' src='"+avatar+"'>"+
        "<div class='plain right'>"+
        "<pre class='ng-binding'>"+content+"</pre></div></div>";
        $('#chat-content').append(text);       
    }

    function showReceiveMsg(avatar,content){
       var text = "<div class='content'>"+
       "<img class='avatar' src='"+avatar+"'>"+
       "<div class='plain left'>"+
       "<pre class='ng-binding'>"+content+"</pre></div></div>";
       $('#chat-content').append(text);
   }

   function connect() {
    var socket = new SockJS('/endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('frame',frame);
        stompClient.subscribe("/topic/user/list",function (message) {
            var users = JSON.parse(message.body);
            showUser(users);
        });
        stompClient.subscribe('/user/queue/notification',function (message) {
           console.log('receive message',message)
           var messageJSON = JSON.parse(message.body);
           var email = messageJSON.target.email;
           if(me !== null && me.email=== email){     
             var avatar = messageJSON.target.avatar;
             var content = messageJSON.message;
             showReceiveMsg(avatar,content);
         }else{
             var array = [];
             if (unread[email] == null) {
                 array.push(messageJSON);
                 unread[email] = array;
             }else{
                 unread[email].push(messageJSON);
                 array = unread[email];
             }
             $('a[data-email="'+email+'"] > span').html(array.length);
         }
     })
    }, function(error) {
           // alert("STOMP error " + error);
       });
}

$(function () {
    $.getJSON('/api/user/loginuser',function (result) {
        me = JSON.parse(JSON.stringify(result));
        $('.username').text(me.username);
    })
    $.getJSON('/api/user/online',function (users) {showUser(users);});
    connect();
});


function showUser(users) {
    var html = "";
    users.forEach(function (user) {
       if (me.email !== user.email)
        html += "<a href='javascript:void(0);' class='nav-link' onclick='selectUser(this)' data-avatar='"+user.avatar+"' data-id='"
    +user.id+"' data-email='"+user.email+"' data-username='"+user.username+"'><img class='user-liset-avatar' src='"+user.avatar+"'>" + user.username + "<span class=\"unread\"></span></a>";
});
    if (html === '')
        html = "<a class=\"nav-link disabled\" href=\"#\">暂无用户在线！</a>";
    $('#left-navbar').html(html);
}