<#import "../parts/common.ftl" as c>
<#import "../parts/action_macro.ftl" as action>

<@c.page "Main Page">
    <div>
        <@action.logout />
    </div>
    <div>
        <#if access_denied??>
            <h3>${access_denied}</h3>
        <#else>
            <div>
                <script type="text/javascript" src="../js/poll.js"></script>
                <table id="table"><h4>Task list</h4>
                    <#list items as item>
                        <tr>
                            <th>id: [ ${item.id} ] </th>
                            <th id="item${item?index}"> content: [ ${item.content} ] </th>
                            <th id="vote${item?index}"> votes: [ 0 ] </th>
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
                <button type="button" onClick='onSaveClick("${_csrf.token}")'>Save votes</button>
            </div>
        </#if>
    </div>
</@c.page>