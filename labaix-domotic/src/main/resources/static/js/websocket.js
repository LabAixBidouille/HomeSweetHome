
var stompClient = null;

function setConnected(connected) {
    //document.getElementById('connect').disabled = connected;
    //document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('notifications').innerHTML = '';
}

function connect() {
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/temperature', function(message){
            showDataTemperature(JSON.parse(message.body).content);
        });
        stompClient.subscribe('/topic/humidite', function(message){
            showDataHumidite(JSON.parse(message.body).content);
        });
        stompClient.subscribe('/topic/temperature/sn', function(message){
            showDataTemperatureSN(JSON.parse(message.body).content);
        });
        stompClient.subscribe('/topic/humidite/sn', function(message){
            showDataHumiditeSN(JSON.parse(message.body).content);
        });
        stompClient.subscribe('/topic/notifications', function(message){
            console.log(message.body);
            console.log(JSON.parse(message.body));
            addNotification(JSON.parse(message.body));
        });
    });
}

function disconnect() {
    stompClient.disconnect();
    setConnected(false);
    console.log("Disconnected");
}

function showDataTemperature(message) {
    var temperature = document.getElementById('temperature');
    console.log(message);
    temperature.innerHTML = message;
}

function showDataHumidite(message) {
    var hygrometrieAir = document.getElementById('humidite');
    console.log(message);
    hygrometrieAir.innerHTML = message;
}

function showDataTemperatureSN(message) {
    var hygrometrieSol = document.getElementById('temperature-sn');
    console.log(message);
    hygrometrieSol.innerHTML = message;
}

function showDataHumiditeSN(message) {
    var light = document.getElementById('humidite-sn');
    console.log(message);
    light.innerHTML = message;
}

function addNotification(notifications) {

    var response = document.getElementById('notificationsList');
    response.innerHTML = "";
    for (var counter in notifications) {

        //var p = document.createElement('p');
        //p.style.wordWrap = 'break-word';
        //p.appendChild(document.createTextNode(notifications[counter].content));
        //response.appendChild(p);

        var a = document.createElement('a');
        a.className = 'list-group-item';
        a.href = "#";

        var i =  document.createElement("i");
        i.className = "fa fa-twitter fa-fw";
        i.appendChild(document.createTextNode(notifications[counter].content));

        a.appendChild(i);

        response.appendChild(a);
    }


}

