<!DOCTYPE html>
<#assign ava=JspTaglibs["/WEB-INF/tld/avatar-tags.tld"]>
<#assign s=JspTaglibs["/WEB-INF/tld/struts-tags.tld"]>
<#assign c=JspTaglibs["/WEB-INF/tld/c.tld"]>
<#assign fn=JspTaglibs["/WEB-INF/tld/fn.tld"]>
<#assign decorator=JspTaglibs["/WEB-INF/tld/sitemesh-decorator.tld"]>
<#assign page=JspTaglibs["/WEB-INF/tld/sitemesh-page.tld"]>
<#assign fm=JspTaglibs["/WEB-INF/tld/fmtag.tld"]>
<#setting number_format="0.##"/>
<html class="theme">
<head>
    <meta charset="utf-8">
    <script charset="utf-8" src="<@ava.extStaticResource resource='/seajs/dist/sea.js'/>"></script>
    <script charset="utf-8" src="<@ava.extStaticResource resource='/sea-config.js'/>"></script>
    <script charset="utf-8" src="<@ava.extStaticResource resource='/build/f-lib.min.js'/>"></script>
    <script charset="utf-8" src="<@ava.extStaticResource resource='/ga.js'/>"></script>
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/f-css.min.css'/>" type="text/css">

    <title>${title}</title>
    <script>
        //页头
        var ENV = {
            debug: true
        };
        ENV.data = {};
        seajs.use('nav');
    </script>
${head}
    <script>
		var _gaq = _gaq || [];
    	_gaq.push(['_setAccount', 'UA-33919587-17']);
    	_gaq.push(['_setDomainName', '${domain}']);
        _gaq.push(['_setAllowHash', false]);
    	var dpga = function (key) {
			_gaq.push(['_trackPageview', key || ''])
		}
		var pageTracker = {_trackPageview: dpga};
	</script>
</head>
<body>
<div class="app">
    <div class="app-header app-header-finance">
        <div class="navbar">
            <div class="navbar-inner">
                <a class="brand logo" href="#">财务新平台</a>
                <ol class="nav" style="">
                    <li class=""><a href="${domain}/#!/pay">应付管理</a></li>
                    <li <#if currentChannel == "receive">class="active"</#if>><a href="${domain}/#!/receipt">应收管理</a>
                    </li>
                    <li class=""><a href="${domain}:/Home/Index#!revenue-cost">收入成本</a></li>
                    <li class=""><a href="${domain}/#!/report">报表管理</a></li>
                    <li <#if currentChannel == "invoice">class="active"</#if>><a href="${domain}/#!/invoice">发票管理</a>
                    </li>
                    <li class=""><a href="${domain}/#!/system">系统管理</a></li>
                    <li class=""><a href="${domain}/prepaidcard/bill/bplist">储值卡管理</a></li>
                    <li class=""><a href="${domain}/caiwu/paymentplan/paymentplanlist">核算管理</a></li>
                </ol>
                <div class="btn-group pull-right" style="font-size: 14px;">
                    <a class="btn" href="#"><i class="icon-user" style="margin-right: 5px;"></i><span
                            class="">${loginName}</span> </a><a class="btn dropdown-toggle" data-toggle="dropdown"
                                                                href="#" id="_optButton">
                    <span class="icon-caret-down"></span></a>
                    <ul class="dropdown-menu" id="_optMenu">
                        <li><a href="#"><i class="icon-pencil"></i>账号设置 </a></li>
                        <li><a href="#"><i class="icon-trash"></i>帮助 </a></li>
                        <li class="divider"></li>
                        <li><a href="http://sys.www.dianping.com/Logout.aspx"><i class="i"></i>退出 </a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
<#if currentChannel == "invoice">
    <div class="app-body">
        <div class="sidebar app-sidebar">
            <div class="scroller">
                <ol class="sidebar-nav" style="">
                    <li class="lv1 active expand">
                        <a onclick="toggleExpand(event)" href="#" title="团购开票">
                            <span data-bind="text: text">团购开票</span><i class="icon-chevron-down"></i><i
                                class="icon-chevron-right"></i>
                        </a>
                        <ol>
                            <li class="lv2 leaf">
                                <a title="应开发票" href="${domain}/#!/invoice/group-invoice/should-be-invoice">应开发票</a>
                            </li>
                            <li class="lv2 leaf">
                                <a href="${domain}:/Home/Index#!invoice/group-invoice/real-invoiced"
                                   title="已开发票">已开发票</a>
                            </li>
                        </ol>
                    </li>
                    <li class="lv1 active expand">
                        <a onclick="toggleExpand(event)" href="#" title="预订开票">
                            <span data-bind="text: text">预订开票</span><i class="icon-chevron-down"></i><i
                                class="icon-chevron-right"></i>
                        </a>
                        <ol>
                            <li class="lv2 leaf active">
                                <a title=" 查询/开票" href="/caiwu/invoice/invoicelist">查询/开票</a>
                            </li>
                        </ol>
                    </li>
                </ol>
            </div>
        </div>
        <div class="collapse-handle">
            <i class="icon-chevron-left"></i><i class="icon-chevron-right"></i>
        </div>
        <div class="main">
		${body}
        </div>
    </div>
<#elseif currentChannel == "receive">
    <div class="app-body">
        <div class="sidebar app-sidebar">
            <div class="scroller">
                <ol class="sidebar-nav">
                    <li class="lv1 leaf">
                        <a title="应收款" href="${domain}/#!/receipt/a-r">应收款</a>
                    </li>
                    <li class="lv1 leaf">
                        <a title="收款" href="${domain}/#!/receipt/receipt">收款</a>
                    </li>
                    <li class="lv1 active expand">
                        <a onclick="toggleExpand(event)" href="#" title="客户查询">
                            <span data-bind="text: text">客户查询</span><i class="icon-chevron-down"></i><i
                                class="icon-chevron-right"></i>
                        </a>
                        <ol>
                            <li class="lv2 leaf active">
                                <a title="预约预订" href="/caiwu/accountinfo/accountinfolist">预约预订</a>
                            </li>
                        </ol>
                    </li>
                </ol>
            </div>
            <div class="collapse-handle">
                <i class="icon-chevron-left"></i><i class="icon-chevron-right"></i>
            </div>
        </div>
        <div class="collapse-handle">
            <i class="icon-chevron-left"></i><i class="icon-chevron-right"></i>
        </div>
        <div class="main">
		${body}
        </div>
    </div>
<#elseif currentChannel == "prepaidcard">
    <div class="app-body">
        <div class="sidebar app-sidebar">
            <div class="scroller">
                <ol class="sidebar-nav">
                    <li class="lv1 active expand">
                        <a onclick="toggleExpand(event)" href="#" title="应付管理">
                            <span data-bind="text: text">应付管理</span><i class="icon-chevron-down"></i><i
                                class="icon-chevron-right"></i>
                        </a>
                        <ol>
                            <li class="lv2 leaf" id="menu-bp">
                                <a title="结算单查询" href="${domain}/prepaidcard/bill/bplist">结算单查询</a>
                            </li>
                            <li class="lv2 leaf" id="menu-pp">
                                <a title="付款计划查询" href="${domain}/caiwu/paymentplan/paymentplanlist">付款计划查询</a>
                            </li>
                            <li class="lv2 leaf" id="menu-order">
                                <a title="付款单查询" href="${domain}/caiwu/exchangeorder/orderlist">付款单查询</a>
                            </li>
                        </ol>
                    </li>
                </ol>
            </div>
            <div class="collapse-handle">
                <i class="icon-chevron-left"></i><i class="icon-chevron-right"></i>
            </div>
        </div>
        <div class="collapse-handle">
            <i class="icon-chevron-left"></i><i class="icon-chevron-right"></i>
        </div>
        <div class="main">
		${body}
        </div>
    </div>
</#if>

</div>
<@ava.contentPlaceHolder id="PlaceHolderScripts"/>
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
</body>
</html>
