<div class="panel">
    <div class="header">
        <h4>收款通知列表</h4>
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
                    <th width="7%" class="fs tb-header">收款通知ID</th>
                    <th width="15%" class="fs tb-header">客户名</th>
                    <th width="7%" class="fs tb-header">收款金额</th>
                    <th width="8%" class="fs tb-header">打款日期</th>
                    <th width="12%" class="fs tb-header">付款方户名</th>
                    <th width="8%" class="fs tb-header">款项类型</th>
                    <th width="8%" class="fs tb-header">合同号</th>
                    <th width="8%" class="fs tb-header">收款方式</th>
                    <th width="6%" class="fs tb-header">备注</th>
                    <th width="6%" class="fs tb-header">附件</th>
                    <th width="6%" class="fs tb-header">收款公司</th>
                    <th width="8%" class="fs tb-header">状态</th>
                </tr>

                </thead>

                <tbody id="receivenotify_list" namespace="receiveNotifyModel" page_size="20"
                       table_url="/exchange/ajax/receiveNotifyList">
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
    <td class="fs tb-item">{{= record.receiveNotifyId}}</td>

    <td class="fs tb-item auto-break">{{= record.customerId}}</td>
    <td class="fs tb-item auto-break">{{= record.receiveAmount}}</td>
    <td class="fs tb-item auto-break">{{= record.payTime}}</td>
    <td class="fs tb-item auto-break">{{= record.payerName}}</td>
    <td class="fs tb-item auto-break">{{= record.businessType}}</td>
    <td class="fs tb-item auto-break">{{= record.bizContent}}</td>
    <td class="fs tb-item auto-break">{{= record.payChannel}}</td>
    <td class="fs tb-item auto-break">{{= record.memo}}</td>
    <td class="fs tb-item auto-break">
    {{if record.attachment!="nourl"}}
    <a href="{{= record.attachment}}" target="_blank">附件</a>
    {{/if}}
    </td>
    <td class="fs tb-item auto-break">{{= record.bankId}}</td>
    {{if record.status=="待审核"||record.status=="已确认"}}
    <td class="fs tb-item auto-break">
    <a href="javascript:void(0);" data-toggle="popover" data-placement="top"
        roMatchId="{{= record.roMatchId}}"
        class="rn-show"  rel="popover-medium">{{= record.status}}</a>
    </td>
    {{else}}
    <td class="fs tb-item auto-break">{{= record.status}}</td>
    {{/if}}
    </tr>
    {{/each}}

</script>