<#import "../parts/common.ftl" as c>
<#import "../parts/action_macro.ftl" as am>

<@c.page "Your profile">
    <div>
        <@am.logout />
    </div>
    <h3>${user.username} [id: ${user.id}] profile</h3>
    <div>
        <@am.post_form "/user/profile/save" "Save">
            <div>
                <label>  ODL PASSWORD: <input type="password" name="password"/></label><br>
                <label>    NEW PASSWORD: <input type="password" name="new_password"/></label><br>
                <label>    ONCE AGAIN: <input type="password" name="check_password"/></label>
            </div>
            <div>
                <#if messages??>
                    <#list messages as message>
                        <h4><span style="color: #ce0f0d; ">${message}</span></h4><#sep><br>
                    </#list>
                </#if>
            </div>
        </@am.post_form>
        <#list user.roles as role>
            <h4>ROLE: ${role}</h4>
        </#list>
    </div>
    <h2>
        Polling Info
    </h2>
    <p>votes limit: ${user.votesLimit}</p>
    <p>last poll time: ${user.lastPollTime!''}</p>
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