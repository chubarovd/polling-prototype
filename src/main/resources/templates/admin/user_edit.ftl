<#import "../parts/common.ftl" as c>
<#import "../parts/action_macro.ftl" as am>

<@c.page "Edit User (id: ${user.id})">
    <div>
        <@am.logout />
    </div>
    <h3>User [id: ${user.id}] info</h3>
    <@am.post_form "/admin/edit" "Save">
        <input type="hidden" name="userId" value="${user.id}"/>
        <div>
            <label>USERNAME: <input type="text" name="username" value="${user.username}"/></label><br>
            <label>PASSWORD: <input type="password" name="password" value="${user.password}"/></label>
        </div>
        <div>
            <#if message??>
                <h4><span style="color: #ce0f0d">${message}</span></h4>
            </#if>
        </div>
        <#list roles as role>
            <label><input type="radio" name="role" value="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</label>
        </#list><br>
    </@am.post_form>
    <label>VOTES LIMIT: ${user.votesLimit}</label>
    <div>
        <h2>
            Last user's votes
        <@am.post_form "/admin/edit/clear_votes" "Clear Votes">
            <input type="hidden" name="id" value="${user.id}"/>
        </@>
        </h2>
        <p>last vote time: ${user.lastPollTime!''}</p>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Item</th>
                    <th>Votes</th>
                </tr>
            </thead>
            <tbody>
                <#if votes??>
                    <#list votes as vote>
                        <tr>
                            <th><p> ${vote.id} </p></th>
                            <th><p> ${vote.item} [id: ${vote.item.id}] </p></th>
                            <th><p> ${vote.count} </p></th>
                        </tr>
                    </#list>
                <#else>
                    There is no votes yet.
                </#if>
            </tbody>
        </table>
    </div>
    <div>
        <h2>
            User's polling history
        </h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Item</th>
                <th>Votes</th>
            </tr>
            </thead>
            <tbody>
                <#if oldVotes??>
                    <#list oldVotes as vote>
                        <tr>
                            <th><p> ${vote.id} </p></th>
                            <th><p> ${vote.content} </p></th>
                            <th><p> ${vote.count} </p></th>
                        </tr>
                    </#list>
                <#else>
                    There is no votes yet.
                </#if>
            </tbody>
        </table>
    </div>
</@c.page>