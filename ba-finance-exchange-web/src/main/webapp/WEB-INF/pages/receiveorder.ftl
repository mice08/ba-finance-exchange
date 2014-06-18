<#assign ava=JspTaglibs["/WEB-INF/tld/avatar-tags.tld"]>
<#include "/WEB-INF/pages/util/pageNavigation.ftl" />
<#setting number_format="#.##"/>

<head>
    <title>收款单</title>
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/base-css.min.css'/>" type="text/css">
    <link rel="stylesheet" href="<@ava.extStaticResource resource='/build/common-css.css'/>" type="text/css">
</head>

<body>
<#include "/WEB-INF/pages/common/tips.ftl">
<#include "/WEB-INF/pages/addreceiveorder.ftl">
<form>
    <!-- @ main -->
    <!--内容-->
    <div class="content">
        <div class="padding">
            <div>
                <div class="panel">
                    <div class="header">
                        <h4>收款单查询</h4>
                    </div>
                    <div class="body">
                        <div class="form-horizontal" id="payorder_search">
                            <div class="row-fluid">
                                <div class="control-group span6">
                                    <label class="control-label"></label>

                                    <div class="controls">
                                        <a id="btn-add-br" href="#add-ro-dialog" role="button" class="btn btn-primary btn-fs-normal btn-fs-xs ajaxdisabledbutton" data-toggle="modal">
                                            <span class="glyphicon glyphicon-plus"></span>新增</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            <!--内容 end-->
            <script>
                // 加载入口模块
                if (ENV.debug) {
                    seajs.use("<@ava.extStaticResource resource='/js/receiveorder'/>");
                } else {
                    seajs.use("<@ava.extStaticResource resource='/build/receiveorder.min'/>");
                }
            </script>
</form>


</body>