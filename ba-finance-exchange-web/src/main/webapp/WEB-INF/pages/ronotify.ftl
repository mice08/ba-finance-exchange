<div id="ro-notify-dialog" class="modal hide fade" style="z-index:50;left:auto;width:80%;margin-left: auto;margin-right: auto;top:5%;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">收款单与收款通知关联</h4>
    </div>
    <div class="modal-body" style="max-height: 530px">
        <div class="tb-wrapper">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th width="10%" class="fs tb-header">收款通知ID</th>
                    <th width="12%" class="fs tb-header">客户名</th>
                    <th width="7%" class="fs tb-header">收款金额</th>
                    <th width="8%" class="fs tb-header">打款日期</th>
                    <th width="10%" class="fs tb-header">付款方户名</th>
                    <th width="10%" class="fs tb-header">款项类型</th>
                    <th width="10%" class="fs tb-header">业务信息</th>
                    <th width="8%" class="fs tb-header">收款方式</th>
                    <th width="18%" class="fs tb-header">备注</th>
                    <th width="4%" class="fs tb-header">附件</th>
                    <th width="12%" class="fs tb-header">操作</th>
                </tr>
                </thead>

                <tbody id="ronotify_list" namespace="" roId=""
                       table_url="/exchange/ajax/findNotifiesByROId">
                </tbody>

            </table>
        </div>
        <div class="tb-wrapper">
            <table class="table table-hover">
                <thead>
                <tr bgcolor="#d9edf7">
                    <th width="10%" class="fs tb-header">收款单ID</th>
                    <th width="12%" class="fs tb-header">客户名</th>
                    <th width="7%" class="fs tb-header">收款金额</th>
                    <th width="8%" class="fs tb-header">到款日期</th>
                    <th width="10%" class="fs tb-header">付款方户名</th>
                    <th width="10%" class="fs tb-header">款项类型</th>
                    <th width="10%" class="fs tb-header">业务信息</th>
                    <th width="8%" class="fs tb-header">收款方式</th>
                    <th width="18%" class="fs tb-header">备注</th>
                    <th width="4%" class="fs tb-header"></th>
                    <th width="12%" class="fs tb-header"></th>
                </tr>
                </thead>

                <tbody id="rnreceiveorder_list" namespace="">
					<td class="fs tb-header" id="rn_roId"></td>
					<td class="fs tb-header" id="rn_customerName"></td>
					<td class="fs tb-header" id="rn_receiveAmount"></td>
					<td class="fs tb-header" id="rn_receiveTime"></td>
					<td class="fs tb-header" id="rn_payerName"></td>
					<td class="fs tb-header" id="rn_receiveType"></td>
					<td class="fs tb-header" id="rn_bizContent"></td>
					<td class="fs tb-header" id="rn_payChannel"></td>
					<td class="fs tb-header" id="rn_memo"></td>
					<td class="fs tb-header" ></td>
					<td class="fs tb-header" ></td>
                </tbody>

            </table>
        </div>
    </div>
    <div class="modal-footer">
    </div>
</div>

<script id="NoNotifiesTemplate" type="text/x-jquery-tmpl">
<tr>
<td colspan="13">没有对应的收款通知</td>
</tr>
</script>

<script id="ronotifylist_model" type="text/x-jquery-tmpl">
{{each(i,record) records}}
{{if i%2==1}}
    <tr class="even">
    {{else}}
    <tr>
    {{/if}}
        <td class="fs tb-item">{{= record.applicationId}}</td>

        <td class="fs tb-item auto-break">{{= record.customerName}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveAmount}}</td>
        <td class="fs tb-item auto-break">{{= record.payTime}}</td>
        <td class="fs tb-item auto-break">{{= record.payerName}}</td>
        <td class="fs tb-item auto-break">{{= record.businessType}}</td>
        <td class="fs tb-item auto-break">{{= record.bizContent}}</td>
        <td class="fs tb-item auto-break">{{= record.payChannel}}</td>
        <td class="fs tb-item auto-break">{{= record.memo}}</td>
        <td class="fs tb-item auto-break">{{if record.attachment != ""}}<a href="{{= record.attachment}}" target="blank">附件</a>{{/if}}</td>
        <td class="fs tb-item auto-break">
        <a href="javascript:void(0)" rnId="{{= record.receiveNotifyId}}" class="rorn-confirm-link">确认</a>
        <a href="javascript:void(0)" rnId="{{= record.receiveNotifyId}}" class="rorn-cancel-link">取消关联</a>
        </td>
    </tr>
    {{/each}}

</script>

<script id="rnreceiveorder_model" type="text/x-jquery-tmpl">
{{each(i,record) records}}
{{if i%2==1}}
    <tr class="even">
    {{else}}
    <tr>
    {{/if}}
        <td class="fs tb-item">{{= record.roId}}</td>

        <td class="fs tb-item auto-break">{{= record.customerName}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveAmount}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveTime}}</td>
        <td class="fs tb-item auto-break">{{= record.payerName}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveType}}</td>
        <td class="fs tb-item auto-break">{{= record.bizContent}}</td>
        <td class="fs tb-item auto-break">{{= record.payChannel}}</td>
        <td class="fs tb-item auto-break">{{= record.memo}}</td>
        </td>
    </tr>
    {{/each}}

</script>