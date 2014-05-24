/**
 * Created by Crabar on 22.05.2014.
 */
var _action;

function initOAuthIO() {
    OAuth.initialize('w5lnaZLTxqd0EeBl99PrcUK3UBo');
}

function socialLogin(provider, action) {
    _action = action;
    OAuth.popup(provider, getProfile);
}

function onSuccessGetProfile(result) {
    document.client.onSocialLogin(result);
}

function getProfile(error, result) {
    result.get(_action).done(onSuccessGetProfile);
}

function sendRequest(method, address, params, headers, requestID) {
    var xmlhttp = new XMLHttpRequest();
    var target;

    if (params != "")
        target = address + '?' + params;
    else
        target = address;

    xmlhttp.open(method, target, true);

    if (headers != null) {
        for (var i = 0; i < headers.length; i++) {
            if (headers[i] != null)
                xmlhttp.setRequestHeader(headers[i].name, headers[i].value);
        }
    }

    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4) {
            if (xmlhttp.status == 200) {
                document.client.onRequestComplete(xmlhttp.responseText, requestID);
            }
            else if (xmlhttp.status == 401) {
                document.client.processAuth(xmlhttp.getResponseHeader('WWW-Authenticate'), method, address, params, requestID);
            }
            else {
                document.client.onError(xmlhttp.responseText);
            }
        }
    };

    xmlhttp.send(params);
}