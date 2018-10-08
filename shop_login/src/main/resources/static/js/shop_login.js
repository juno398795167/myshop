//登录提示条
$(function () {
    $.ajax({
        url:"http://localhost:8084/iflogin",
        dataType:"jsonp"
    })
})
function callback(data) {
    if(data!=null){

        $("#pid").html(data.name+"你好，欢迎来到"+"<a href=\"/\">ShopCZ商城</a>"+"<a href=\"http://localhost:8084/logout\">注销</a>")

    }else {

        $("#pid").html("[<a href=\"javascript:login();\">登录</a>][<a href=\"\">注册</a>]")
    }

}
function login() {
    var resultUrl = location.href;
    /*alert(resultUrl)*/
    resultUrl = encodeURI(resultUrl);
    resultUrl = resultUrl.replace("&","*");
     location.href = "http://localhost:8084/tologin?resultUrl="+resultUrl;


}