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
                    <th width="7%" class="fs tb-header">收款通知ID</th>
                    <th width="15%" class="fs tb-header">客户名</th>
                    <th width="7%" class="fs tb-header">收款金额</th>
                    <th width="8%" class="fs tb-header">打款日期</th>
                    <th width="12%" class="fs tb-header">付款方户名</th>
                    <th width="12%" class="fs tb-header">业务类型</th>
                    <th width="12%" class="fs tb-header">业务信息</th>
                    <th width="8%" class="fs tb-header">收款方式</th>
                    <th width="8%" class="fs tb-header">备注</th>
                    <th width="8%" class="fs tb-header">附件</th>
                    <th width="12%" class="fs tb-header">操作</th>
                </tr>
                </thead>

                <tbody id="ronotify_list" namespace="" roId=""
                       table_url="/exchange/ajax/findNotifiesByROId">
                </tbody>

            </table>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="modify-ro"><span class="glyphicon glyphicon-floppy-disk"></span>确定</button>
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
        <td class="fs tb-item">{{= record.receiveNotifyId}}</td>

        <td class="fs tb-item auto-break">{{= record.customerName}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveAmount}}</td>
        <td class="fs tb-item auto-break">{{= record.payTime}}</td>
        <td class="fs tb-item auto-break">{{= record.payerName}}</td>
        <td class="fs tb-item auto-break">{{= record.businessType}}</td>
        <td class="fs tb-item auto-break">{{= record.bizContent}}</td>
        <td class="fs tb-item auto-break">{{= record.payChannel}}</td>
        <td class="fs tb-item auto-break">{{= record.memo}}</td>
        <td class="fs tb-item auto-break">{{= record.attachment}}</td>
        <td class="fs tb-item auto-break">
        <a href="javascript:void(0)" rnId="{{= record.receiveNotifyId}}" class="rorn-confirm-link">确定</a>
        <a href="javascript:void(0)" rnId="{{= record.receiveNotifyId}}" class="rorn-cancel-link">取消关联</a>
        </td>
    </tr>
    {{/each}}

</script>