<div id="modify-ro-dialog" class="modal hide fade" style="z-index:50;left:auto;width:80%;margin-left: auto;margin-right: auto;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">收款单修改</h4>
    </div>
    <div class="modal-body">
        <div class="form-horizontal" id="modify-ro-form" form_url="/exchange/ajax/addReceiveOrderManually">
            <input type="text" style="display: none" class="form_value" id="m-id" name="roId" validate="+">
            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">产品线</label>
                    <div class="controls">
                        <label id="m-businessType" name="businessType"></label>
                    </div>
                </div>
            <#--<div class="control-group span6">-->
            <#--<label class="control-label">客户名</label>-->
            <#--<div class="controls">-->
            <#--<input type="text" class="input-small" id="customerName" name="customerName">&nbsp;&nbsp;对应客户ID为：<label id="customerId" name="customerId" class="form_value"/>-->
            <#--</div>-->
            <#--</div>-->
                <div class="control-group span6">
                    <label class="control-label">客户ID</label>
                    <div class="controls">
                        <label id="m-customerId" name="customerId"></label>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">收款金额</label>
                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on">¥</span>
                            <label id="m-receiveAmount" name="receiveAmount"></label>

                        </div>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">收款方式</label>
                    <div class="controls">
                        <label id="m-payChannel" name="payChannel"></label>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">业务类型</label>
                    <div class="controls">
                        <select id="m-receiveType" name="receiveType" class="form_value">
                            <option value="0">请选择业务类型</option>
                        </select>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">业务信息</label>
                    <div class="controls">
                        <input type="text" id="m-bizContent" name="bizContent" class="form_value">
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">

                <div class="control-group span6">
                    <label class="control-label">收款银行账户</label>
                    <div class="controls">
                        <label id="m-bankId" name="bankId"></label>
                    </div>
                </div>

                <div class="control-group span6" id="bankReceiveTime-cntr">
                    <label class="control-label">银行到账日期</label>
                    <div class="controls">
                        <div class="input-append">
                            <input type="text" class="span12" id="m-bankReceiveTime" name="bankReceiveTime" datatype="date">
                        <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">备注</label>
                    <div class="controls">
                        <input type="text" id="m-memo" name="memo" class="form_value">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="modify-ro"><span class="glyphicon glyphicon-floppy-disk"></span>确定</button>
    </div>
</div>