<#import "../parts/common.ftl" as c>
<#import "../parts/action_macro.ftl" as am>

<@c.page "Edit User (id: ${user.id})">
    <div>
        <@am.logout />
    </div>
    <h3>User [id: ${user.id}] info</h3>
    <@am.post_form "/admin/edit" "Save">
        <input type="hidden" name="userId" value="${user.id}"/>
        <label>USERNAME: <input type="text" name="username" value="${user.username}"/></label><br>
        <label>PASSWORD: <input type="password" name="password" value="${user.password}"/></label><br>
        <#list roles as role>
            <label><input type="radio" name="role" value="${role}" ${user.roles?seq_contains(role)?string("checked", "")}/>${role}</label>
        </#list><br>
        <!--<label>IS ACTIVE:
            <input type="checkbox" name="active" value=""/>
        </label>
        <label>RESET LAST TIME:
            <input type="checkbox" name="resetLastTime"/>
        </label>-->
    </@am.post_form>
        <label>VOTES LIMIT: ${user.votesLimit}</label>
    <h2>
        User's votes
        <@am.post_form "/admin/edit/clear_votes" "Clear Votes">
            <input type="hidden" name="id" value="${user.id}"/>
        </@>
    </h2>
    <p>last vote time: ${user.lastPollTime!''}</p>
    <div>
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
</@c.page>