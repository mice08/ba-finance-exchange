<#macro pageNavigation pageModel params=""> <#-- TODO:第一页不需要传递参数pg，后台处理的时候pg默认为1
输出分页导航 1.引用的页面需要能获取到 pageModel:PageModel 对象 @see com.dianping.avatar.dao.PageModel
2.分页导航中始终需要输出第一页和最后一页 @param pageModel.pageCount : 总的页数 @param pageModel.page ：
当前页 @param params : 传递参数 @author mingxing.ma @create 2010-10-29 -->
    <#if pageModel?exists&& (pageModel.pageCount >= 1)>
        <#assign curPage = pageModel.page>
        <#assign pageCount= pageModel.pageCount>
        <#assign recordCount= pageModel.recordCount>
    <#-- 开始页 : 求最大值(当前页和4的差值 , 1) -->
        <#if ((curPage - 3) > 1)>
            <#assign startPage = (curPage - 3)>
        <#else>
            <#assign startPage = 1>
        </#if> <#--结束页 : 求最小值(开始页+8, 总页数) -->
        <#if ((startPage + 4) < pageCount)>
            <#assign endPage = (startPage + 4)>
        <#else>
            <#assign endPage = pageCount>
        </#if>
    <div>
        <div class="pagination pagination-right">
            <div class="text">

                <strong>${(curPage-1)*pageSize+1}-
                    <#if (curPage*pageSize<=recordCount)>    <#--如果不是最后一页 -->
                    ${curPage*pageSize}
                    </#if>
                    <#if (curPage*pageSize>recordCount)>          <#--最后一页 -->
                    ${recordCount}
                    </#if>
                </strong>条，

                共 <strong>${recordCount}</strong> 条记录
            </div>
            <ul style="">
                <li><a href="?page=1${params}">首页</a></li>
                <#if (curPage > 1) >
                    <li><a href="?page=${curPage - 1}${params}">«</a></li>
                </#if>
                <#if (startPage > 1)>
                    <li><a href="?page=1${params}" class="page-index">1</a></li>
                    <#if (startPage > 2)>
                        <li class=""><a herf="#">...</a></li>
                    </#if>
                </#if>
                <#if (startPage <= endPage)>
                    <#list startPage..endPage as page>
                        <#if curPage == page>
                            <li class="active"><a class="page-index">${page}</a></li>
                        <#else>
                            <li class=""><a href="?page=${page}${params}" class="page-index">${page}</a></li>
                        </#if>
                    </#list>
                </#if>
                <#if (endPage < pageCount)>
                    <#if (endPage < pageCount - 1)>
                        <li class=""><a herf="#">...</a></li>
                    </#if>
                    <li><a href="?page=${pageCount}${params}" class="page-index">${pageCount}</a></li>
                </#if>
                <#if (curPage < pageCount)>
                    <li class=""><a href="?page=${curPage + 1}${params}">»</a></li>
                </#if>
                <li class=""><a href="?page=${pageCount}${params}">尾页</a></li>
            </ul>
            <div class="text">
                共 <strong>${pageCount}</strong> 页
            </div>
        </div>
    </div>
    </#if>
</#macro>