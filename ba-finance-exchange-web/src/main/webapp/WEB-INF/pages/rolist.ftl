<div class="panel">
    <div class="header">
        <h4>收款单列表</h4>
    </div>
    <div class="body strip">
        <div>
            <div class="alert alert-warning" id="select-all-pageinfo">
                已勾选本页全部数据， <a href="#" id="select-all-page">点击这里勾选全部</a>&nbsp;<i class="recordCount"></i> 条
            </div>
            <div class="alert alert-warning" id="select-cur-pageinfo">
                已勾选全部 <i class="recordCount"></i> 条， <a href="#" id="select-cur-page">取消勾选</a>
            </div>
            <div class="alert alert-info" id="result-info">
                <span class="number-char recordCount">0</span> 条符合条件的结果，总金额
                <span class="number-char totalAmount">0</span> 元
                &nbsp;&nbsp;
                <a id="order-export" class="btn btn-primary btn-fs-normal btn-fs-sm ajaxdisabledbutton"
                   href="###" style="display: none">
                    <span class="glyphicon glyphicon-save"></span>导出</a>

            </div>
        </div>
        <div class="tb-wrapper">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th width="8%" class="fs tb-header">收款单ID</th>

                    <th width="7%" class="fs tb-header">客户名</th>
                    <th width="7%" class="fs tb-header">收款金额</th>
                    <th width="8%" class="fs tb-header">到款日期</th>
                    <th width="8%" class="fs tb-header">系统入账日期</th>
                    <th width="8%" class="fs tb-header">款项类型</th>
                    <th width="8%" class="fs tb-header">业务信息</th>
                    <th width="6%" class="fs tb-header">收款方式</th>
                    <th width="8%" class="fs tb-header">收款通知ID</th>
                    <th width="7%" class="fs tb-header">付款方户名</th>
                    <th width="10%" class="fs tb-header">备注</th>
                    <th width="4%" class="fs tb-header">状态</th>
                    <th width="10%" class="fs tb-header">操作</th>
                </tr>
                </thead>

                <tbody id="receiveorder_list" namespace="receiveOrderModel" page_size="20"
                       table_url="/exchange/ajax/receiveorderlist">
                </tbody>

            </table>
        </div>

    <#include "/WEB-INF/pages/common/paging.ftl">

    </div>
</div>

<script id="NoRowsTemplate" type="text/x-jquery-tmpl">
<tr>
<td colspan="13">没有查询到任何记录</td>
</tr>
</script>

<script id="list_model" type="text/x-jquery-tmpl">
{{each(i,record) records}}
{{if i%2==1}}
    <tr class="even">
    {{else}}
    <tr>
    {{/if}}
        <td class="fs tb-item">{{= record.roId}}</td>

        <td class="fs tb-item auto-break">{{= record.customerName}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveAmount}}</td>
        <td class="fs tb-item auto-break">{{= record.bankReceiveTime}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveTime}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveType}}</td>
        <td class="fs tb-item auto-break">{{= record.bizContent}}</td>
        <td class="fs tb-item auto-break">{{= record.payChannel}}</td>
        <td class="fs tb-item auto-break">{{= record.receiveNotifyId}}</td>
        <td class="fs tb-item auto-break">{{= record.payerName}}</td>
        <td class="fs tb-item auto-break">{{= record.memo}}</td>
        <td class="fs tb-item auto-break">{{= record.status}}</td>
        <td class="fs tb-item auto-break">{{if record.status=="待确认"}}<a href="javascript:void(0)" roId="{{= record.roId}}" class="modify-link">修改</a> {{/if}} <a href="#">作废</a></td>
    </tr>
    {{/each}}

</script>