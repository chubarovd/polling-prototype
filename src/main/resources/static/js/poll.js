var votesList = [];
function onPlusClick(i) {
    document.getElementById("vote" + i).innerHTML = ' votes: [ ' + (++votesList[i]) + ' ] ';
}

function onMinusClick(i) {
    if (votesList[i] > 0)
        document.getElementById("vote" + i).innerHTML = ' votes: [ ' + (--votesList[i]) + ' ] ';
}

function onSaveClick(token) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", '/polling/save_votes', true);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.setRequestHeader("X-CSRF-Token", token);
    var json = {};
    json.list = votesList;
    xhr.send(JSON.stringify(json));
}