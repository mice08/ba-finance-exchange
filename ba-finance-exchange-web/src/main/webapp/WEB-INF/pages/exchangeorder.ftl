<#assign ava=JspTaglibs["/WEB-INF/tld/avatar-tags.tld"]>
<#include "/WEB-INF/pages/util/pageNavigation.ftl" />
<#setting number_format="#.##"/>

<head>
    <title>付款单支付</title>
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/base-css.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/exchangeorder-css.min.css'/>" type="text/css">
    <script type="text/javascript" src="${commonStaticServer}/build/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${commonStaticServer}/build/relation.js"></script>
</head>

<body>
<div id="import-refund" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">导入退票</h4>
    </div>
    <div class="modal-body">
        <label class="import-label">导入文件：</label>
        <div class="uploader">
            <input id="import-file" class="import-file" name="importRefund" type="file" size="31" >
            <div class="input-append">
                <input type="text" name="upload-file" data-bind="value: filename" placeholder="这里是文件名">
                <button class="btn btn-default btn-fs-default btn-fs-sm" type="button">选择文件</button>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-default btn-fs-default btn-fs-xs" data-dismiss="modal" aria-hidden="true">取消</button>
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="confirm-import">确定</button>
    </div>
</div>
<div id="refund-result" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-labelledby="refundResultLabel" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="refundResultLabel">导入退票结果</h4>
    </div>
    <div class="modal-body">
        <div class="failed-result"></div>
        <div class="success-result"></div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="confirm-result" data-dismiss="modal" aria-hidden="true">确定</button>
    </div>
</div>

<div id="relation-result" class="modal hide fade modal-lg" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    </div>
    <div class="modal-body">
    </div>
</div>

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
            <form>
            <div class="form-horizontal">
                <div class="row-fluid label-colon">
                <div class="alert alert-error" id="query-status-info"></div>
                    </div>
                <div class="row-fluid label-colon">

                    <div class="control-group span6" id="business-type-selector">
                        <label class="control-label">业务类型</label>

                        <div class="controls">
                            <select id="businesstype">
                                <option value="0">请选择业务类型</option>
                            <#if businessType == 2>
                                <option value="2" selected="true">预约预定</option>
                            <#else>
                                <option value="2">预约预定</option>
                            </#if>
                            <#if businessType == 4>
                                <option value="4" selected="true">储值卡</option>
                            <#else>
                                <option value="4">储值卡</option>
                            </#if>
                            </select>
                        </div>
                    </div>
                    <div class="control-group span6" id="shop-id-input"
                         <#if businessType != 2>style="display: none"</#if>>
                        <label class="control-label">ShopID</label>

                        <div class="controls">
                            <input type="text" id="shopid" value="<#if shopId gt 0 >${shopId}</#if>"
                                   autocomplete="off">
                        </div>
                    </div>
                </div>
                <div class="row-fluid label-colon">
                    <div class="control-group span6">
                        <label class="control-label">付款单ID</label>

                        <div class="controls">
                            <input type="text" id="bizCode" value="${bizCode}" autocomplete="off">
                        </div>
                    </div>
                    <div class="control-group span6">
                        <label class="control-label">状态</label>

                        <div class="controls">
                            <select id="status">
                            <#if status == 0>
                                <option value="0" selected="true">全部</option>
                            <#else>
                                <option value="0">全部</option>
                            </#if>
                            <#if status == 1>
                                <option value="1" selected="true">初始</option>
                            <#else>
                                <option value="1">初始</option>
                            </#if>
                            <#if status == 2>
                                <option value="2" selected="true">支付中</option>
                            <#else>
                                <option value="2">支付中</option>
                            </#if>
                            <#if status == 3>
                                <option value="3" selected="true">支付成功</option>
                            <#else>
                                <option value="3">支付成功</option>
                            </#if>
                            <#if status == 4>
                                <option value="4" selected="true">退票</option>
                            <#else>
                                <option value="4">退票</option>
                            </#if>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row-fluid label-colon">
                    <div class="control-group span6">
                        <label class="control-label">产生日期</label>

                        <div class="controls">
                            <select id="select-date" class="select-small">
                                <option value="99">自定义</option>
                                <option value="0">截至今天</option>
                                <option value="1">今天</option>
                                <option value="2">昨天</option>
                                <option value="3">前天</option>
                            </select>

                            <div class="input-append">
                                <input type="text" id="datebegin" value="${addDateBegin}"
                                       class="input-small">
                                <span class="add-on">
                                    <i class="icon-calendar">
                                    </i>
                                </span>
                            </div>
                            至
                            <div class="input-append">
                                <input type="text" id="dateend" value="${addDateEnd}"
                                       class="input-small">
                                <span class="add-on">
                                    <i class="icon-calendar">
                                    </i>
                                </span>
                            </div>
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
                </form>
        </div>
    </div>
    <div class="panel">
        <div class="header">
            <h4>付款单列表</h4>
        </div>
        <div class="body strip">
            <div>
                <div class="alert alert-warning" id="select-all-pageinfo">
                    已勾选本页全部数据， <a href="#"
                                  id="select-all-page">点击这里勾选全部</a>&nbsp;<i>${exchangeOrderModel.recordCount}</i> 条
                </div>
                <div class="alert alert-warning" id="select-cur-pageinfo">
                    已勾选全部 <i>${exchangeOrderModel.recordCount}</i> 条， <a href="#" id="select-cur-page">取消勾选</a>
                </div>
                <div class="alert alert-info" id="result-info">
                    <span class="number-char">${exchangeOrderModel.recordCount}</span> 条符合条件的结果，总金额
                    <span class="number-char">${totalAmount?string(",##0.00")}</span> 元
                    <#if status == 3>
                    <a id="btn-import" href="#import-refund" role="button"
                       class="btn btn-primary btn-fs-normal btn-fs-sm ajaxdisabledbutton" data-toggle="modal">
                        <span class="glyphicon glyphicon-open"></span>导入退票</a>
                    </#if>
                <#if status == 1 || status == 2>
                    &nbsp;&nbsp;
                    <a id="btn-export" class="btn btn-primary btn-fs-normal btn-fs-sm ajaxdisabledbutton"
                            href="###">
                        <span class="glyphicon glyphicon-save"></span>导出支付</a>
                </#if>
                <#if status == 2>
                    &nbsp;&nbsp;<a class="btn btn-primary btn-fs-normal btn-fs-sm ajaxdisabledbutton" href="#"
                                   id="paysuccess">
                    <span class="glyphicon glyphicon-ok"></span>确认支付</a>
                </#if>
                </div>
            </div>
            <div class="tb-wrapper">
                <table class="table table-hover">
                    <thead>
                    <tr>
                    <#if status == 2>
                        <th width="2%"><input type="checkbox" id="select-all"></th>
                    </#if>
                        <th width="10%" class="fs tb-header id">付款单号</th>
                        <th width="15%" class="fs tb-header customer-name">客户名</th>
                        <#if businessType == 2>
                        <th width="8%" class="fs tb-header shop-id">ShopID</th>
                        </#if>
                        <th width="10%" class="fs tb-header date">产生日期</th>
                        <th width="8%" class="fs tb-header amount">付款单金额</th>
                        <th width="9%" class="fs tb-header bank-account">银行账号</th>
                        <th width="9%" class="fs tb-header relation-link">关系图</th>
                        <th width="10%" class="fs tb-header status">状态</th>
                    <#if status == 3 || status = 0>
                        <th width="10%" class="fs tb-header date">付款日期</th>
                    </#if>
                    <#if bizCode != ""|| status == 4>
                        <th width="10%" class="fs tb-header date">退票日期</th>
                    </#if>
                        <th class="fs tb-header memo">备注</th>
                    </tr>
                    </thead>

                    <tbody>
                    <#if exchangeOrderModel.recordCount == 0>
                    <tr>
                        <td colspan="9" class="no-records">没有查询到任何记录</td>
                    </tr>
                    <#else>
                        <#assign even = false>
                        <#list exchangeOrderModel.records as order>
                            <#if even == true>
                            <tr class="even">
                                <#assign even = false>
                            <#else>
                            <tr>
                                <#assign even = true>
                            </#if>
                            <#if status == 2>
                                <td><input type="checkbox" id="${order.orderId}" class="selected-order"></td>
                            </#if>
                            <td class="fs tb-item id number-char">${order.bizCode}</td>
                            <td class="fs tb-item customer-name">${order.customerName}</td>
                            <#if businessType == 2>
                                <td class="fs tb-item shop-id">${order.shopId}</td>
                            </#if>
                            <td class="fs tb-item date number-char">${order.addDate?string("yyyy-MM-dd")}</td>
                            <td class="fs tb-item amount number-char">${order.orderAmount?string(",##0.00")}</td>
                            <td class="fs tb-item bank-account">
                                <a href="javascript:void(0);" class="lookup-bank-info" data-toggle="popover"
                                   data-placement="top"
                                   data-content="<tr><th style='text-align:left; min-width:60px;'>开户名：</th><td style='text-align:left'>${order.bankAccountName}&#10;</td></tr>
                                               <tr><th style='text-align:left; min-width:60px;'>开户行：</th><td style='text-align:left'>${order.bankName}&#10;</td></tr>
                                               <tr><th style='text-align:left; min-width:60px;'>帐号：</th>><td style='text-align:left'>${order.bankAccountNo}</td></tr>"
                                   title="" data-original-title="银行帐号信息" data-html="true" data-animation="true"
                                   data-trigger="click">查看</a>
                            </td>
                            <td class="fs tb-item relation-link"><a href="#relation-result" data-toggle="modal"
                                                                    class="query-relation" eo-id="${order.orderId}"
                                                                    eo-bizcode="${order.bizCode}">查看</a></td>

                            <td class="fs tb-item status">${order.status}</td>
                            <#if status == 3 || status = 0>
                                <#if order.orderDate??>
                                    <td class="fs tb-item date number-char">${order.orderDate?string("yyyy-MM-dd")}</td>
                                <#else>
                                    <td class="fs tb-item date number-char"></td>
                                </#if>
                            </#if>
                        <#if bizCode !="" || status == 4>
                            <#if order.lastUpdateDate?? && order.status == "退票">
                            <td class="fs tb-item date number-char">${order.lastUpdateDate?string("yyyy-MM-dd")}</td>
                            <#else>
                            <td class="fs tb-item date number-char"></td>
                            </#if>
                        </#if>
                            <td class="fs tb-item memo">${order.memo}</td>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        <@pageNavigation exchangeOrderModel />
        </div>
    </div>
</div>
<!--内容 end-->
<script>
    // 加载入口模块
    if (ENV.debug) {
        seajs.use("<@ava.extStaticResource resource='/js/orderlist'/>");
        seajs.use("<@ava.extStaticResource resource='/js/bootstrap-popover'/>");
        seajs.use("<@ava.extStaticResource resource='/js/bootstrap-tooltip'/>");
        seajs.use("<@ava.extStaticResource resource='/js/dper-common'/>");
    } else {
        seajs.use("<@ava.extStaticResource resource='/build/orderlist.min'/>");
        seajs.use("<@ava.extStaticResource resource='/build/bootstrap-popover.min'/>");
        seajs.use("<@ava.extStaticResource resource='/build/bootstrap-tooltip.min'/>");
        seajs.use("<@ava.extStaticResource resource='/build/dper-common.min'/>");
    }
    ENV.data.query = {
        "bizCode": "${bizCode}",
        "addDateBegin": "${addDateBegin}",
        "addDateEnd": "${addDateEnd}",
        "status": "${status}",
        "businessType":"${businessType}",
        "shopId":"${shopId}"
    }
</script>
<script>
    if (location.href == 'http://fs.sys.www.dianping.com/caiwu/exchangeorder/orderlist' || location.href == 'http://fs.dper.com/caiwu/exchangeorder/orderlist') {
        pageTracker._trackPageview('dp_fs_dper_exchangeorderlist_page');
    }
</script>




</body>
