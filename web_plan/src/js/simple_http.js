/**
 * Created by basic on 2017/3/16.
 */
function httpAjax(options) {
    options = options || {};
    options.type = (options.type || "GET").toLocaleUpperCase();
    options.dataType = options.dataType || "json";
    var param = formatParams(options.data)
    var xht;
    if (window.XMLHttpRequest) { // 非 ie
        xht = new XMLHttpRequest();
    } else {
        xht = new ActiveXObject('Microsoft.XMLHTTP');
    }
    xht.onreadystatechange = function () {
        if (xht.readyState == 4) {
            var status = xht.status;
            if (status >= 200 && status < 300) {
                options.success && options.success(xht.responseText, xht.responseXML); // 如果回调方法不为null
            } else {
                options.failure && options.failure(status);
            }
        }
    }
    //发送请求
    if (options.type == "GET") {
        xht.open("GET", options.url + "?" + param, true);
        xht.send(null);
    } else { // post
        xhr.open("POST", options.url, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(params); //发送参数
    }
}

function formatParams(param) {
    var arr = [];
    for (var name in param) { //遍历数组
        console.log("name: " + name + "; value: " + param[name]);
        arr.push(name + "=" + param[name]);
    }
    var formatResult = arr.join("&");
    console.log("params: " + formatResult);
    return formatResult;
}
