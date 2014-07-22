<div id="add-ro-dialog" class="modal hide fade" style="z-index:50;left:auto;width:80%;margin-left: auto;margin-right: auto;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">收款单录入</h4>
    </div>
    <div class="modal-body">
        <div class="form-horizontal" id="add-ro-form" form_url="/exchange/ajax/addReceiveOrderManually">
            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">产品线<span class="required">*</span></label>
                    <div class="controls">
                        <select id="businessType" name="businessType" class="form_value" validate="ne[0]"
                                error_msg="ne[0]:请选择产品线">
                            <option value="0">请选择产品线</option>
                                </select>
                    </div>
                </div>
                <#--<div class="control-group span6">-->
                    <#--<label class="control-label">客户名</label>-->
                    <#--<div class="controls">-->
                        <#--<input type="text" class="input-small" id="customerName" name="customerName">&nbsp;&nbsp;对应客户ID为：<label id="customerId" name="customerId" class="form_value"/>-->
                    <#--</div>-->
                <#--</div>-->
                <div class="control-group span6">
                    <label class="control-label">客户名<span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="customerName" name="customerName" class="form_value"  validate="+"
                               error_msg="+:请输入客户名">
                        <input type="hidden" id="customerId" name="customerId" class="form_value" validate="+ n[0,11]"
                               error_msg="+:请输入有效的客户名称">
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">收款金额<span class="required">*</span></label>
                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on">¥</span>
                            <input type="text" id="receiveAmount" name="receiveAmount" class="span12 form_value"
                                   validate="gt[0] + f[0,2]"
                                   error_msg="gt[0]:金额必须大于0 f[0,2]:金额格式不正确，必须为数值且整数位数不大于10位，小数位数不大于2位">
                        </div>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">收款通知ID<span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="receiveNotifyId" name="receiveNotifyId" class="form_value"
                               validate="+ n[0,11]"
                               error_msg="+:请输入收款通知ID">
                    </div>
                </div>

            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">款项类型<span class="required">*</span></label>
                    <div class="controls">
                        <select id="receiveType" name="receiveType" class="form_value"  validate="ne[0]" error_msg="ne[0]:请选择业务类型">
                            <option value="0">请选择款项类型</option>
                        </select>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">业务信息<span class="required">*</span></label>
                    <div class="controls">
                        <input type="text" id="bizContent" name="bizContent" class="form_value"  validate="+">
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">

                <div class="control-group span6">
                    <label class="control-label">收款银行账户<span class="required">*</span></label>
                    <div class="controls">
                        <select id="bankId" name="bankId" validate="ne[0]" class="form_value" error_msg="ne[0]:请选择收款银行账户">
                            <option value="0">请选择收款银行账户</option>
                        </select>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">收款方式<span class="required">*</span></label>
                    <div class="controls">
                        <select id="payChannel" name="payChannel" class="form_value" validate="ne[0]"
                                error_msg="ne[0]:请选择收款方式">
                            <option value="0">请选择收款方式</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">备注</label>
                    <div class="controls">
                        <input type="text" id="memo" name="memo" class="form_value">
                    </div>
                </div>

                <div class="control-group span6" id="bankReceiveTime-cntr">
                    <label class="control-label">银行到账日期<span class="required">*</span></label>
                    <div class="controls">
                        <div class="input-append">
                            <input type="text" class="span12" id="bankReceiveTime" name="bankReceiveTime" validate="+"
                                   datatype="date">
                            <span class="add-on">
                                <i class="icon-calendar"></i>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="add-ro"><span class="glyphicon glyphicon-floppy-disk"></span>确定</button>
    </div>
</div>