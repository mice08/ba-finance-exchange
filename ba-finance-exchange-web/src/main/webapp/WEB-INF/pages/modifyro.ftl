<div id="modify-ro-dialog" class="modal hide fade" style="z-index:50;left:auto;width:80%;margin-left: auto;margin-right: auto;top:5%;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header section-title">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h4 id="myModalLabel">收款单修改</h4>
    </div>
    <div class="modal-body" style="max-height: 530px">
        <div class="form-horizontal" id="modify-ro-form" load_url="/exchange/ajax/getReceiveOrderById" form_url="/exchange/ajax/updateReceiveOrder">
            <input type="text" style="display: none" class="form_value load_value" id="m-id" name="roId" validate="+" error_msg="收款单信息异常">
            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">收款单ID</label>
                    <div class="controls">
                        <label id="m-roId" style="padding-top: 5px" class="load_value" name="roId"></label>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">产品线</label>
                    <div class="controls">
                        <label id="m-businessType" style="padding-top: 5px" class="load_value" name="businessType"></label>
                    </div>
                </div>
            <#--<div class="control-group span6">-->
            <#--<label class="control-label">客户名</label>-->
            <#--<div class="controls">-->
            <#--<input type="text" class="input-small" id="customerName" name="customerName">&nbsp;&nbsp;对应客户ID为：<label id="customerId" name="customerId" class="form_value"/>-->
            <#--</div>-->
            <#--</div>-->

            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">收款金额</label>
                    <div class="controls">
                        <div class="input-prepend">
                            <label id="m-receiveAmount" class="load_value" style="padding-top: 5px" name="receiveAmount"></label>
                        </div>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">收款方式</label>
                    <div class="controls">
                        <label id="m-payChannel" class="load_value" name="payChannel" style="padding-top: 5px"></label>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">分店ID</label>
                    <div class="controls">
                        <div class="input-prepend">
                            <label id="m-shopId" class="load_value" style="padding-top: 5px" name="shopId"></label>
                        </div>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">到款日期</label>
                    <div class="controls">
                        <label id="m-bankReceiveTime" class="load_value" name="bankReceiveTime" style="padding-top: 5px"></label>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">交易流水</label>
                    <div class="controls">
                        <div class="input-prepend">
                            <label id="m-tradeNo" class="load_value" style="padding-top: 5px" name="tradeNo"></label>
                        </div>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">状态</label>
                    <div class="controls">
                        <div class="input-prepend">
                            <label id="m-status" class="load_value" style="padding-top: 5px" name="status"></label>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">付款方开户行</label>
                    <div class="controls">
                        <label id="m-payerBankName" class="load_value" name="payerBankName" style="padding-top: 5px"></label>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">付款方户名</label>
                    <div class="controls">
                        <label id="m-payerAccountName" class="load_value" name="payerAccountName" style="padding-top: 5px"></label>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">付款方账户</label>
                    <div class="controls">
                        <div class="input-prepend">
                            <label id="m-payerAccountNo" class="load_value" style="padding-top: 5px" name="payerAccountNo"></label>
                        </div>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">客户名称</label>
                    <div class="controls">
                        <input type="text" id="m-customerName" name="customerName" class="form_value load_value">
                        <input type="hidden" id="m-customerId" name="customerId" class="form_value load_value">
                    </div>
                </div>

            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6" id="bankReceiveTime-cntr">
                    <label class="control-label">系统入账日期</label>
                    <div class="controls">
                        <div class="input-append">
                            <input type="text" class="span12 load_value form_value" id="m-receiveTime" name="receiveTime" datatype="date">
                        <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">业务信息</label>
                    <div class="controls">
                        <input type="text" id="m-bizContent" name="bizContent" class="form_value load_value" validate="s[0,100]"
                               error_msg="s[0,254]:请输入0-100位字符">
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">

                <div class="control-group span6">
                    <label class="control-label">款项类型</label>
                    <div class="controls">
                        <select id="m-receiveType" name="receiveType" class="form_value load_value">
                            <option value="0">请选择款项类型</option>
                        </select>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">备注</label>
                    <div class="controls">
                        <input type="text" id="m-memo" name="memo" class="form_value load_value" validate="s[0,100]"
                               error_msg="s[0,254]:请输入0-100位字符">
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary btn-fs-normal btn-fs-xs" id="modify-ro"><span class="glyphicon glyphicon-floppy-disk"></span>确定</button>
    </div>
</div>