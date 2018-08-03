<#import "parts/common.ftl" as c>
<#import "parts/action_macro.ftl" as action>

<@c.page "Main Page">
    <div>
        <@action.logout />
    </div>

    <#if access_denied??>
        <h3>${access_denied}</h3>
    <#else>
        <div>
            <script type="text/javascript">
                var votesList = [];
                function onPlusClick(i) {
                    document.getElementById("vote" + i).innerHTML = ' [ ' + (++votesList[i]) + ' ] ';
                };

                function onMinusClick(i) {
                    if (votesList[i] > 0)
                        document.getElementById("vote" + i).innerHTML = ' [ ' + (--votesList[i]) + ' ] ';
                };

                function onSaveClick() {
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", '/polling/save_votes', true)
                    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                    xhr.setRequestHeader("X-CSRF-Token", "${_csrf.token}");
                    var json = {};
                    json.list = votesList;
                    xhr.send(JSON.stringify(json));
                }
            </script>

            <table id="table"><h3>Task list</h3>
                <#list items as item>
                    <tr>
                        <th>id: ${item.id} </th>
                        <th id="item${item?index}">${item.content}</th>
                        <th id="vote${item?index}"> [ 0 ] </th>
                        <th><button type="button" onclick="onPlusClick(${item?index})">+</button>
                            <button type="button" onclick="onMinusClick(${item?index})">-</button></th>
                    </tr>
                    <script>
                        votesList.push(0);
                    </script>
                <#else>
                    <tr>
                        <th>There is no tasks yet.</th>
                    </tr>
                </#list>
            </table>

            <button type="button" onClick="onSaveClick()">Save votes</button>
        </div>
    </#if>
    <@action.debug "add"><input type="text" name="content"/></@>
    <@action.debug "print_summary"/>
    <@action.debug "print_votes"/>
    <@action.debug "print_users"/>
</@c.page>