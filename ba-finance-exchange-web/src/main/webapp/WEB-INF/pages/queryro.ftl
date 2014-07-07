<div class="panel">
    <div class="header">
        <h4>收款单查询</h4>
    </div>
    <div class="body">
        <div class="form-horizontal" id="ro-search-form">
            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">产品线<span class="required">*</span></label>
                    <div class="controls">
                        <select id="q-businessType" name="businessType" class="form_value" validate="ne[0]"
                                error_msg="ne[0]:请选择产品线">
                            <option value="0">请选择产品线</option>
                        </select>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">客户ID</label>
                    <div class="controls">
                        <input type="text" id="q-customerId" name="customerId" class="form_value"  validate="n">
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">款项类型</label>
                    <div class="controls">
                        <select id="q-receiveType" name="receiveType" class="form_value">
                            <option value="0">请选择款项类型</option>
                        </select>
                    </div>
                </div>
                <div class="control-group span6">
                    <label class="control-label">收款方式</label>
                    <div class="controls">
                        <select id="q-payChannel" name="payChannel" class="form_value">
                            <option value="0">请选择收款方式</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row-fluid label-colon">
                <div class="control-group span6">
                    <label class="control-label">到款日期</label>

                    <div class="controls">
                        <select id="q-select-br-time" class="select-small">
                            <option value="99">自定义</option>
                            <option value="0">截至今天</option>
                            <option value="1">今天</option>
                            <option value="2">昨天</option>
                            <option value="3">前天</option>
                        </select>

                        <div class="input-append">
                            <input type="text" id="q-br-begin-time" datatype="date" name="bankReceiveTimeBegin"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                        至
                        <div class="input-append">
                            <input type="text" id="q-br-end-time" validate="ge[q-br-begin-time]" datatype="date"
                                   name="bankReceiveTimeEnd" error_msg="ge[q-br-begin-time]:起始日期大于结束日期"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">系统入账日期</label>

                    <div class="controls">
                        <select id="q-select-receive-time" class="select-small">
                            <option value="99">自定义</option>
                            <option value="0">截至今天</option>
                            <option value="1">今天</option>
                            <option value="2">昨天</option>
                            <option value="3">前天</option>
                        </select>

                        <div class="input-append">
                            <input type="text" id="q-receive-begin-time" datatype="date" name="receiveTimeBegin"
                                   class="input-small form_value">
                                            <span class="add-on">
                                                <i class="icon-calendar">
                                                </i>
                                            </span>
                        </div>
                        至
                        <div class="input-append">
                            <input type="text" id="q-receive-end-time" validate="ge[q-receive-begin-time]" datatype="date"
                                   name="receiveTimeEnd" error_msg="ge[q-receive-begin-time]:起始日期大于结束日期"
                                   class="input-small form_value">
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
                    <label class="control-label">确认收款情况</label>
                    <div class="controls">
                        <select id="q-status" name="status" class="form_value">
                            <option value="0">请选择收款情况</option>
                        </select>
                    </div>
                </div>

                <div class="control-group span6">
                    <label class="control-label">收款银行账户</label>
                    <div class="controls">
                        <select id="q-bankId" name="bankId" class="form_value">
                            <option value="0">请选择收款银行账户</option>
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

                        <a id="btn-add-br" style="width: 100px" href="#add-ro-dialog" role="button" class="btn btn-primary btn-fs-normal btn-fs-xs ajaxdisabledbutton" data-toggle="modal">
                            <span class="glyphicon glyphicon-pencil"></span>手工录入</a>

                        <a id="btn-import-tel-transfer" href="#import-tel-transfer" role="button" class="btn btn-primary btn-fs-normal btn-fs-xs ajaxdisabledbutton" data-toggle="modal">
                            <span class="glyphicon glyphicon-file"></span>导入</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>