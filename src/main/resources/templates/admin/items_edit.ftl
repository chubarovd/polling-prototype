<#import "../parts/common.ftl" as c>
<#import "../parts/action_macro.ftl" as am>

<@c.page "Edit Items">
    <div>
        <a href="/admin/items"></a>
    </div>
    <div>
        Items list
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Content</th>
                <th>Votes</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list items as item>
            <tr>
                <th> ${item.id} </th>
                <th> ${item.content} </th>
                <th> ${summary[item_index]} </th>
                <th>
                    <@am.post_form "/admin/items/delete/${item.id}" "delete">
                        <input type="hidden" name="itemId" value="${item.id}"/>
                    </@am.post_form>
                </th>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <div>
        <@am.post_form "/admin/items/add" "Add Item">
            <label>SET CONTENT: <input type="text" name="content" value=""/></label>
        </@am.post_form>
    </div>
</@c.page>