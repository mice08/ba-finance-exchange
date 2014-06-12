<#assign ava=JspTaglibs["/WEB-INF/tld/avatar-tags.tld"]>
<#include "/WEB-INF/pages/util/pageNavigation.ftl" />
<#setting number_format="#.##"/>

<head>
    <title>付款单支付</title>
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/base-css.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/common-css.css'/>" type="text/css">
</head>

<body>
<div id="import-refund" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">导入退票</h4>
    </div>
    <div class="modal-body">
        <label class="import-label">导入文件：</label>
        <div class="uploader">
            <input id="import-file" class="import-file" style="display: none" name="refundFile" type="file" size="31">

            <div class="input-append">
                <input type="text" name="upload-file" data-bind="value: filename" placeholder="这里是文件名">
                <button id="chooseFile" class="btn btn-default btn-fs-default btn-fs-sm" type="button">选择文件</button>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-default btn-fs-default btn-fs-xs" data-dismiss="modal" aria-hidden="true">取消</button>
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="confirm-import">确定</button>
    </div>
</div>
<div id="refund-result" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-labelledby="refundResultLabel"
     aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="refundResultLabel">导入退票结果</h4>
    </div>
    <div class="modal-body">
        <div class="success-result"></div>
        <div class="failed-result"></div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="confirm-result" data-dismiss="modal"
                aria-hidden="true">确定
        </button>
    </div>
</div>

<div id="relation-result" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    </div>
    <div class="modal-body">
    </div>
</div>
<form>
<!-- @ main -->
<!--内容-->
<div class="content">
<div class="padding">
<div>
    <div class="panel">
        <div class="header">
            <h4>付款单查询</h4>
        </div>
        <div class="body">
            <div class="form-horizontal" id="payorder_search">
                <div class="row-fluid label-colon">
                    <div class="control-group span6" id="business-type-selector">
                        <label class="control-label">业务类型</label>

                        <div class="controls">
                            <select id="businesstype" name="businessType" class="form_value" validate="ne[0]"
                                    error_msg="ne[0]:请选择业务类型">
                            </select>
                        </div>
                    </div>
                    <div class="control-group span6" id="shop-id-input">
                        <label class="control-label">付款单号</label>

                        <div class="controls">
                            <input type="text" id="paysequence" name="payCode" class="form_value">
                        </div>
                    </div>
                </div>
                <div class="row-fluid label-colon">
                    <div class="control-group span6">
                        <label class="control-label">产生日期</label>

                        <div class="controls">
                            <select id="select-add-date" class="select-small">
                                <option value="99">自定义</option>
                                <option value="0">截至今天</option>
                                <option value="1">今天</option>
                                <option value="2">昨天</option>
                                <option value="3">前天</option>
                            </select>

                            <div class="input-append">
                                <input type="text" id="addbegindate" datatype="date" name="addBeginTime"
                                       class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                            </div>
                            至
                            <div class="input-append">
                                <input type="text" id="addenddate" validate="ge[addbegindate]" datatype="date"
                                       name="addEndTime" error_msg="ge[addbegindate]:起始日期大于结束日期"
                                       class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                            </div>
                        </div>
                    </div>

                    <div class="control-group span6">
                        <label class="control-label">状态</label>

                        <div class="controls">
                            <select id="status" class="form_value" name="status">
                            </select>
                        </div>
                    </div>
                </div>


                <div class="row-fluid">
                    <div class="control-group span6">
                        <label class="control-label"></label>

                        <div class="controls">
                            <a class="btn btn-primary btn-fs-normal btn-fs-xs" href="#"
                               id="search">
                                <span class="glyphicon glyphicon-search"></span>查询
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel">
        <div class="header">
            <h4>付款单列表</h4>
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
                        <span class="glyphicon glyphicon-save"></span>导出支付</a>
                    &nbsp;&nbsp;
                    <a id="pay-success" class="btn btn-primary btn-fs-normal btn-fs-sm ajaxdisabledbutton" href="#"
                       style="display: none">
                        <span class="glyphicon glyphicon-ok"></span>确认支付</a>
                    <a id="btn-import" href="#import-refund" role="button"
                       style="display: none"
                       class="btn btn-primary btn-fs-normal btn-fs-sm ajaxdisabledbutton"
                       data-toggle="modal">
                        <span class="glyphicon glyphicon-open"></span>
                        导入退票
                    </a>
                </div>
            </div>
            <div class="tb-wrapper">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th width="3%"><input type="checkbox" id="select-all"></th>
                        <th width="10%" class="fs tb-header id">付款单号</th>
                        <th width="15%" class="fs tb-header customer-name">客户名</th>
                        <th width="8%" class="fs tb-header amount">付款单金额</th>
                        <th width="10%" class="fs tb-header date">产生日期</th>
                        <th width="9%" class="fs tb-header bank-account">银行账号</th>
                        <th width="10%" class="fs tb-header paid-time">付款日期</th>
                        <th width="10%" class="fs tb-header sendback-time">退票日期</th>
                        <th width="10%" class="fs tb-header status">状态</th>
                        <th class="fs tb-header memo">备注</th>
                    </tr>
                    </thead>

                    <tbody id="payorder_list" namespace="payOrderModel" page_size="20"
                           table_url="/caiwu/ajax/payorderlist">
                    </tbody>

                </table>
            </div>
        <#include "/WEB-INF/pages/common/tips.ftl">
        <#include "/WEB-INF/pages/common/paging.ftl">

        </div>
    </div>
</div>
<script id="NoRowsTemplate" type="text/x-jquery-tmpl">
<tr>
<td colspan="9">没有查询到任何记录</td>
</tr>
</script>

<script id="list_model" type="text/x-jquery-tmpl">
{{each(i,record) records}}
{{if i%2==1}}
    <tr class="even">
    {{else}}
    <tr>
    {{/if}}
        <td >
              <input type="checkbox" po-id="{{= record.poId}}" class="selected-payorder">
        </td>
        <td class="fs tb-item id number-char">{{= record.payCode}}</td>
        <td class="fs tb-item id number-char">{{= record.customerName}}</td>
        <td class="fs tb-item id number-char">{{= record.payAmount}}</td>
        <td class="fs tb-item add-date">{{= record.addTime}}</td>
        <td class="fs tb-item bank-account">
                                <a href="javascript:void(0);" data-toggle="popover" data-placement="top" bankAccountName="{{= record.bankAccountName}}" bankAccountNo="{{= record.bankAccountNo}}" bankName="{{= record.bankFullBranchName}}" class="bank-show"  rel="popover">查看</a>
                            </td>

             {{if record.queryStatus==0||record.queryStatus==3}}
        <td class="fs tb-item amount number-char">{{= record.paidDate}}</td>
        {{/if}}
        {{if record.queryStatus==0||record.queryStatus==4}}
        <td class="fs tb-item amount number-char">{{= record.sendBackTime}}</td>
        {{/if}}
        <td class="fs tb-item plan-date number-char">{{= record.statusDesc}}</td>
        <td class="fs tb-item status">{{= record.memo}}</td>
    </tr>
    {{/each}}

</script>
<!--内容 end-->
<script>
    // 加载入口模块
    if (ENV.debug) {
        seajs.use("<@ava.extStaticResource resource='/js/payorder'/>");
    } else {
        seajs.use("<@ava.extStaticResource resource='/build/payorder.min'/>");
    }
</script>
</form>


</body>