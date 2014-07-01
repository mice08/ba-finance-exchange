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
<#include "/WEB-INF/pages/teltransfer.ftl">
    <!-- @ main -->
    <!--内容-->
    <div class="content">
        <div class="padding">
            <#include "/WEB-INF/pages/queryro.ftl">
            <#include "/WEB-INF/pages/rolist.ftl">
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

</body>