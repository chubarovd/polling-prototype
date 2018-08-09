<#import "../parts/common.ftl" as c>
<#import "../parts/action_macro.ftl" as action>

<@c.page "Admin Console">
    <div>
        <@action.logout />
    </div>
    User list
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Role</th>
                <th>Last vote</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <#list users as user>
                <tr>
                    <th>< ${user.id} ></th>
                    <th>< ${user.username} ></th>
                    <th>< <#list user.roles as role>${role}<#sep>, </#list> ></th>
                    <th>< ${user.lastPollTime} ></th>
                    <th><a href="admin/edit/${user.id}">< edit ></a></th>
                </tr>
            </#list>
        </tbody>
    </table>
    <a href="/admin/items"><h4>Edit Items list</h4></a>
</@c.page>